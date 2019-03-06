
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2019, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.text.preprocessing;

import org.carrot2.clustering.Document;
import org.carrot2.clustering.FieldMapDocument;
import org.carrot2.language.LanguageComponents;

import java.util.ArrayList;

/**
 * Preprocessing context builder for tests.
 */
class PreprocessingContextBuilder {
  private final LanguageComponents languageComponents;
  private ArrayList<Document> documents = new ArrayList<>();

  public PreprocessingContextBuilder(LanguageComponents languageComponents) {
    this.languageComponents = languageComponents;
  }

  PreprocessingContextBuilder() {
    this(LanguageComponents.load("English"));
  }

  public PreprocessingContext buildContext(BasicPreprocessingPipeline pipeline) {
    return pipeline.preprocess(documents.stream(), null, languageComponents);
  }

  public PreprocessingContextAssert buildContextAssert(BasicPreprocessingPipeline pipeline) {
    return PreprocessingContextAssert.assertThat(buildContext(pipeline));
  }

  public final static class FieldValue {
    String field;
    String value;

    public FieldValue(String field, String value) {
      this.field = field;
      this.value = value;
    }

    public static FieldValue fv(String fieldName, String value) {
      return new FieldValue(fieldName, value);
    }
  }

  public PreprocessingContextBuilder newDoc(String title) {
    return newDoc(title, null);
  }

  public PreprocessingContextBuilder newDoc(String title, String summary) {
    FieldMapDocument doc = new FieldMapDocument();
    doc.addField("title", title);
    doc.addField("summary", summary);
    documents.add(doc);
    return this;
  }

  public PreprocessingContextBuilder newDoc(FieldValue... fields) {
    FieldMapDocument doc = new FieldMapDocument();
    for (FieldValue fv : fields) {
      doc.addField(fv.field, fv.value);
    }
    documents.add(doc);
    return this;
  }
}
