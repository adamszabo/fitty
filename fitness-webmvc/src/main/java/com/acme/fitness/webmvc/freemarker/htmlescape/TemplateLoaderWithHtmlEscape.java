package com.acme.fitness.webmvc.freemarker.htmlescape;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.io.IOUtils;

import freemarker.cache.TemplateLoader;

public class TemplateLoaderWithHtmlEscape implements TemplateLoader {
	private static final String ESCAPE_PREFIX = "<#ftl strip_whitespace=true><#escape x as x?html>";  
    private static final String ESCAPE_SUFFIX = "</#escape>";
    
    private TemplateLoader delegate;
    
    public TemplateLoaderWithHtmlEscape(TemplateLoader delegate){
    	this.delegate=delegate;
    }

	@Override
	public Object findTemplateSource(String name) throws IOException {
		return delegate.findTemplateSource(name); 
	}

	@Override
	public long getLastModified(Object templateSource) {
		return delegate.getLastModified(templateSource);  
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		delegate.closeTemplateSource(templateSource);
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		Reader reader = delegate.getReader(templateSource, encoding);  
        try {
            String templateText = IOUtils.toString(reader);
            if(templateText.contains("<#ftl")){
            	return new StringReader(templateText);
            }
            return new StringReader(ESCAPE_PREFIX + templateText + ESCAPE_SUFFIX);  
        } finally {  
            IOUtils.closeQuietly(reader);  
        }  
	}

}
