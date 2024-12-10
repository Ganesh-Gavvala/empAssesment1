package com.empAssessment.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;


import com.empAssessment.core.service.impl.CreatePageInterfacebyServlet;

@Component(service = Servlet.class , property={
    "sling.servlet.methods=GET",
    "sling.servlet.methods=POST",
    "sling.servlet.paths=/bin/assessment/createpage"
})
@ServiceDescription("Creating Page by PathBased")
public class CreatePageByServletPathBased extends SlingAllMethodsServlet {
    @Reference
    private CreatePageInterfacebyServlet createPageInterfacebyServlet;


    ResourceResolver resourceResolver;


    @Override
    protected void doGet(final SlingHttpServletRequest req , final SlingHttpServletResponse resp)
    throws ServletException,IOException{
       
        doPost(req, resp);
    }
    @Override
    protected void doPost(final SlingHttpServletRequest req , final SlingHttpServletResponse resp)
      throws ServletException,IOException{
        try{
            // 1st way of getting resourceResolver
            resourceResolver=req.getResourceResolver();
            // 2nd way of getting resourceResolver
                //Resource resource = null;
                //resource.getResourceResolver();
            // 3rd way of getting resoureResolver
            //Service/system user - ResolverResolverfactory get resolver resolver 
            
            createPageInterfacebyServlet.createPage(resourceResolver, "page-1", "Page 1");
            createPageInterfacebyServlet.createPage(resourceResolver, "page-2", "Page 2");
            resp.getWriter().write("Pages are created");
        }catch(Exception e){
            resp.getWriter().write("An error Occured :");
        }
      
      }

}
