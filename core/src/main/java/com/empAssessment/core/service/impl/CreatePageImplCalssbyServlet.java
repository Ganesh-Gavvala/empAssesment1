package com.empAssessment.core.service.impl;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;

@Component(service={CreatePageInterfacebyServlet.class}, immediate = true)
public class CreatePageImplCalssbyServlet implements CreatePageInterfacebyServlet{
    @Override
    public Page createPage(ResourceResolver resourceResolver,String pageName,String title) {
       String parentPath="/content/empAssessment1/language-master/en-us";
       
       String templatePath="/conf/empAssessment1/settings/wcm/templates/articletemplate";
      
       Page page=null;
       PageManager pageManager=resourceResolver.adaptTo(PageManager.class);
       if(pageManager != null){
        try{
        page = pageManager.create(parentPath,pageName,templatePath,title);
        }
        catch(WCMException wcmException){
            wcmException.printStackTrace();
        }

       }
       return page;
        
    }

}
