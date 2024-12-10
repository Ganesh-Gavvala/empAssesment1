package com.empAssessment.core.service.impl;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.Page;

public interface CreatePageInterfacebyServlet {
    Page createPage(ResourceResolver resourceResolver,String pageName,String title);
}
