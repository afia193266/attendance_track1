package com.dats.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.dats.filter.PageAttributeFilter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class FilterConfig
{

    @Bean
    public Filter pageAttributeFilter() {
        return new PageAttributeFilter();
    }

    @Bean
    public FilterRegistrationBean<Filter> customFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(pageAttributeFilter());
        registration.addUrlPatterns("/","*.html");
        return registration;
    }
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/**")
          .addResourceLocations("classpath:/static/")
          //.setCachePeriod(3600)
          .resourceChain(true)
          .addResolver(new PathResourceResolver());
    }
}
