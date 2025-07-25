package com.dats.dto;

public class PageAttribute
{

    private Long   id;

    private String requestUrl;

    private String queryString; // created by saifur

    private String menuName;

    private String menuGroupName;

    private String menuSubGroupName;

    private String title;

    private String contentTitle;

    private String contentSubTitle;

    private String contentPath;

    private String contentPathImage;

    private String contentSubPath;

    public PageAttribute()
    {
    }

    public PageAttribute(boolean isDefault) {
        this.menuName = "Dashboard";
        this.menuGroupName = "Dashboard";
        this.title = "POS";
        this.contentTitle = "POS";
        this.contentSubTitle = "Dashboard";
        this.contentPath = "POS";
        this.contentSubPath = "Dashboard";
        this.contentPathImage = "fa fa-dashboard";
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRequestUrl()
    {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl)
    {
        this.requestUrl = requestUrl;
    }

    public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getMenuName()
    {
        return menuName;
    }

    public void setMenuName(String menuName)
    {
        this.menuName = menuName;
    }

    public String getMenuGroupName()
    {
        return menuGroupName;
    }

    public void setMenuGroupName(String menuGroupName)
    {
        this.menuGroupName = menuGroupName;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContentTitle()
    {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle)
    {
        this.contentTitle = contentTitle;
    }

    public String getContentSubTitle()
    {
        return contentSubTitle;
    }

    public void setContentSubTitle(String contentSubTitle)
    {
        this.contentSubTitle = contentSubTitle;
    }

    public String getContentPath()
    {
        return contentPath;
    }

    public void setContentPath(String contentPath)
    {
        this.contentPath = contentPath;
    }

    public String getContentSubPath()
    {
        return contentSubPath;
    }

    public void setContentSubPath(String contentSubPath)
    {
        this.contentSubPath = contentSubPath;
    }

    public String getContentPathImage()
    {
        return contentPathImage;
    }

    public void setContentPathImage(String contentPathImage)
    {
        this.contentPathImage = contentPathImage;
    }

    public String getMenuSubGroupName() {
        return menuSubGroupName;
    }

    public void setMenuSubGroupName(String menuSubGroupName) {
        this.menuSubGroupName = menuSubGroupName;
    }

    @Override
    public String toString() {
        return "PageAttribute{" +
                "id=" + id +
                ", requestUrl='" + requestUrl + '\'' +
                ", queryString='" + queryString + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuGroupName='" + menuGroupName + '\'' +
                ", menuSubGroupName='" + menuSubGroupName + '\'' +
                ", title='" + title + '\'' +
                ", contentTitle='" + contentTitle + '\'' +
                ", contentSubTitle='" + contentSubTitle + '\'' +
                ", contentPath='" + contentPath + '\'' +
                ", contentPathImage='" + contentPathImage + '\'' +
                ", contentSubPath='" + contentSubPath + '\'' +
                '}';
    }
    
}
