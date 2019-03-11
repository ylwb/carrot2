package org.carrot2.dcs.servlets;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.carrot2.clustering.ClusteringAlgorithmProvider;
import org.carrot2.dcs.client.ClusterRequest;
import org.carrot2.language.LanguageComponents;
import org.carrot2.util.ResourceLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class DcsContext {
  public static final String PARAM_RESOURCES = "resources";
  public static final String PARAM_TEMPLATES = "templates";

  private static String KEY = "_dcs_";
  private static Logger log = LoggerFactory.getLogger(DcsContext.class);

  final ObjectMapper om;
  final Map<String, ClusterRequest> templates;
  final Map<String, ClusteringAlgorithmProvider> algorithmSuppliers;
  final Map<String, LanguageComponents> languages;

  private DcsContext(ServletContext servletContext) throws ServletException {
    this.om = new ObjectMapper();
    om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

    this.templates = processTemplates(om, servletContext);
    this.algorithmSuppliers =
        StreamSupport.stream(ServiceLoader.load(ClusteringAlgorithmProvider.class).spliterator(), false)
            .collect(Collectors.toMap(e -> e.name(), e -> e));

    // Load lexical resources.
    String resourcePath = servletContext.getInitParameter(PARAM_RESOURCES);
    if (resourcePath != null && !resourcePath.trim().isEmpty()) {
      ResourceLookup contextLookup = new ServletContextLookup(servletContext, resourcePath);
      log.info("Loading language resources from: " + resourcePath);
      this.languages = new LinkedHashMap<>();
      for (String lang : LanguageComponents.languages()) {
        try {
          languages.put(lang, LanguageComponents.load(lang, contextLookup));
        } catch (IOException e) {
          throw new ServletException(
              String.format(Locale.ROOT, "Could not load the required resource for language '%s'.", lang), e);
        }
      }
    } else {
      log.info("Loading language resources from default classpath locations.");
      // Load default.
      this.languages = LanguageComponents.languages().stream()
          .collect(Collectors.toMap(
              e -> e,
              e -> LanguageComponents.load(e)));
    }

    log.info("DCS context initialized [algorithms: {}, templates: {}]",
        algorithmSuppliers.keySet(),
        templates.keySet());
  }

  public synchronized static DcsContext load(ServletContext servletContext) throws ServletException {
    DcsContext context = (DcsContext) servletContext.getAttribute(KEY);
    if (context == null) {
      context = new DcsContext(servletContext);
      servletContext.setAttribute(KEY, context);
    }
    return context;
  }

  private Map<String, ClusterRequest> processTemplates(ObjectMapper om, ServletContext servletContext) throws ServletException {
    String templatePath = servletContext.getInitParameter(PARAM_TEMPLATES);
    if (templatePath == null || templatePath.isEmpty()) {
      log.warn("Template path init parameter is empty.");
      return Collections.emptyMap();
    }

    Pattern NAME_PATTERN = Pattern.compile("(/)?(?<ordering>[0-9]+)?+(?<separator>\\s*\\-?\\s*)?+(?<id>[^/]+)(.json)$",
        Pattern.CASE_INSENSITIVE);

    Map<String, ClusterRequest> templates = new LinkedHashMap<>();
    Set<String> resourcePaths = servletContext.getResourcePaths(templatePath);
    if (resourcePaths != null) {
      for (TemplateInfo ti : resourcePaths.stream()
          .filter(path -> path.toLowerCase(Locale.ROOT).endsWith(".json"))
          .map(path -> new TemplateInfo(path))
          .filter(v -> v != null)
          .sorted(Comparator.comparing(t -> t.name))
          .collect(Collectors.toList())) {
        try {
          ClusterRequest requestTemplate =
              om.readValue(servletContext.getResourceAsStream(ti.path), ClusterRequest.class);
          if (requestTemplate.documents != null && !requestTemplate.documents.isEmpty()) {
            log.warn("Templates must not contain the 'documents' property, clearing it in template: {}", ti.path);
            requestTemplate.documents = null;
          }
          templates.put(ti.id, requestTemplate);
        } catch (IOException e) {
          throw new ServletException("Could not process request template: " + ti.path, e);
        }
      }
    }
    return templates;
  }

  private static class TemplateInfo {
    private static Pattern NAME_PATTERN = Pattern.compile("(/)?(?<name>[^/]+)(.json)$", Pattern.CASE_INSENSITIVE);
    private static Pattern ID_PATTERN = Pattern.compile("^(?:[0-9]+)?+(?:\\s*\\-?\\s*)?+", Pattern.CASE_INSENSITIVE);

    private final String path;
    private final String name;
    private final String id;

    public TemplateInfo(String path) {
      this.path = path;
      Matcher matcher = NAME_PATTERN.matcher(path);
      if (!matcher.find()) {
        throw new RuntimeException("Name part not found?: " + path);
      }
      this.name = matcher.group("name");
      this.id = ID_PATTERN.matcher(name).replaceAll("");
    }
  }
}
