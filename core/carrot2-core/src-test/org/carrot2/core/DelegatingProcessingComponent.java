/**
 *
 */
package org.carrot2.core;

import org.carrot2.core.attribute.Init;
import org.carrot2.core.attribute.Processing;
import org.carrot2.util.attribute.*;

@Bindable
public abstract class DelegatingProcessingComponent implements ProcessingComponent
{
    @Init
    @Input
    @Attribute(key = "instanceAttribute")
    private String instanceAttribute = "";

    @Processing
    @Input
    @Attribute(key = "runtimeAttribute")
    private String runtimeAttribute = "";

    @SuppressWarnings("unused")
    @Processing
    @Input
    @Output
    @Required
    @Attribute(key = "data")
    private String data = null;

    @Processing
    @Input
    @Attribute(key = "delay")
    private int delay = 0;
    
    public void init()
    {
        getDelegate().init();
    }

    public void beforeProcessing() throws ProcessingException
    {
        getDelegate().beforeProcessing();
    }

    public void process() throws ProcessingException
    {
        getDelegate().process();

        // Do some simple processing
        data = data + instanceAttribute;
        data = data + runtimeAttribute;
        
        if (delay != 0)
        {
            try
            {
                Thread.sleep(delay);
            }
            catch (InterruptedException e)
            {
                // nothing to do
            }
        }
    }

    public void afterProcessing()
    {
        getDelegate().afterProcessing();
    }

    public void dispose()
    {
        getDelegate().dispose();
    }

    abstract ProcessingComponent getDelegate();
}
