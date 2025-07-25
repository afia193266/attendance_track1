package com.dats.service.impl;

import com.dats.dto.PageAttribute;
import com.dats.service.WebAppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebAppServiceImpl implements WebAppService
{
    private static final Logger logger              = LoggerFactory.getLogger(WebAppServiceImpl.class);

    private final Map<String, PageAttribute> pageAttributesCache = new ConcurrentHashMap<>();

    private final PageAttribute              emptyPageAttribute   = new PageAttribute(true);

    private final ObjectMapper               objectMapper         = new ObjectMapper();

    @Value(value = "classpath:json/PageAttribute.json")
    private Resource                        pageAttributeJson;

    @PostConstruct
    public void init()
    {
        populatePageAttributeFromJSONFile();
    }

    private void populatePageAttributeFromJSONFile() {
        try {
        	
            InputStream fileInputStream = pageAttributeJson.getInputStream();
            if(fileInputStream == null)
            {
                throw new RuntimeException("json/PageAttribute.json has been deleted from codebase. Please add it back.");
            }
            List<PageAttribute> pageAttributes = objectMapper.readValue(fileInputStream, TypeFactory.defaultInstance()
                    .constructCollectionType(List.class, PageAttribute.class));
            for (PageAttribute attribute : pageAttributes)
            {	
            	
                pageAttributesCache.put("/" + attribute.getRequestUrl(), attribute);

            }
        } catch (IOException e) {
            logger.error("Could not parse json/PageAttribute.json. Please check it.",e);
            throw new RuntimeException("Could not parse json/PageAttribute.json. Please check it.",e);
        }

    }

//    @Override
//    public PageAttribute getPageAttribute(String url)
//    {	
//        return pageAttributesCache.getOrDefault(url, emptyPageAttribute);
//    }
    
    @Override
    public PageAttribute getPageAttribute(String url)
    {	
        return pageAttributesCache.getOrDefault(url, emptyPageAttribute);
    }
    

}
