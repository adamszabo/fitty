package com.acme.fitness.webmvc.freemarker.htmlescape;

import java.util.List;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.TemplateLoader;

public class FreeMarkerConfigurerWithHtmlEscape extends FreeMarkerConfigurer{
	
	@Override
	protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {
		logger.info("Using HtmlTemplateLoader to enforce HTML-safe content");
		return new TemplateLoaderWithHtmlEscape(super.getAggregateTemplateLoader(templateLoaders));
	}
}
