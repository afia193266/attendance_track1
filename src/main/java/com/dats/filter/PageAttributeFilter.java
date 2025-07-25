package com.dats.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dats.dto.PageAttribute;
import com.dats.service.WebAppService;

import org.apache.commons.lang3.StringUtils;

public class PageAttributeFilter implements Filter {
	
    private static final Logger logger = LoggerFactory.getLogger(PageAttributeFilter.class);

    @Autowired
    private WebAppService service;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException
    {
    	
        HttpServletRequest request = (HttpServletRequest) req;
        //logger.info("request query\t" + request.getQueryString());
        //HttpServletResponse response = (HttpServletResponse) res;
        
        PageAttribute pageAttribute = service.getPageAttribute(request.getRequestURI());
        
//        String path = request.getRequestURI().substring(request.getContextPath().length());
//        logger.info("Path " + path);

        if (logger.isDebugEnabled())
        {
            logger.debug("pageAttribute :" + pageAttribute);
        }
        
        request.setAttribute("queryString", pageAttribute.getQueryString());// to get requested query, added by saifur
        request.setAttribute("title", pageAttribute.getTitle());
        request.setAttribute("contentTitle", pageAttribute.getContentTitle());
        request.setAttribute("contentSubTitle", pageAttribute.getContentSubTitle());
        request.setAttribute("contentPath", pageAttribute.getContentPath());
        request.setAttribute("contentSubPath", pageAttribute.getContentSubPath());
        request.setAttribute("selectedMenu", pageAttribute.getMenuName());
        request.setAttribute("selectedGroupMenu", pageAttribute.getMenuGroupName());
        request.setAttribute("selectedSubGroupMenu", pageAttribute.getMenuSubGroupName());
        request.setAttribute("contentPathImage", pageAttribute.getContentPathImage());
        
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        name = StringUtils.capitalize(name);
	
        request.setAttribute("username", name);
        request.setAttribute("panel", name+" Panel");
        
        chain.doFilter(req, res);

       
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub

    }

}
