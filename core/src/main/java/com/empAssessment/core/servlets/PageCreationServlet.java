package com.empAssessment.core.servlets;


import java.io.IOException;


import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.resource.ResourceResolverFactory;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.model.WorkflowModel;
import com.empAssessment.core.service.impl.CreatePageInterfacebyServlet;

@Component(service = Servlet.class, property = {
    "sling.servlet.methods=GET",
    "sling.servlet.methods=POST",
    "sling.servlet.paths=/bin/assessment/createpage"
})
public class PageCreationServlet extends SlingAllMethodsServlet {

    @Reference
    private CreatePageInterfacebyServlet pageCreationService;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private WorkflowService workflowService;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            response.getWriter().write("GET method is called. Thank you.");
        } catch (Exception e) {
            response.getWriter().write("Error in GET: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        // Map<String, Object> authInfo = new HashMap<>();
        // authInfo.put(ResourceResolverFactory.SUBSERVICE, "vishal");
        ResourceResolver resourceResolver = null;
        
            resourceResolver = request.getResourceResolver();
        
        
        if (resourceResolver != null) {
            try {
                // Create pages using PageCreationService
                pageCreationService.createPage(resourceResolver, "page-1", "Page 1");
                pageCreationService.createPage(resourceResolver, "page-2", "Page 2");
                response.getWriter().write("Pages created successfully.");



                // Trigger Workflow for created pages
                String playLoad1="/content/empAssessment1/us/en-us/page 1";
                String playLoad2="/content/empAssessment1/us/en-us/page 2";

                try{
                   WorkflowSession workflowSession=resourceResolver.adaptTo(WorkflowSession.class);
                   WorkflowModel workflowModel=workflowSession.getModel("/var/workflow/models/CopyPublishWorkFlowModel");
                   WorkflowData workflowData1=workflowSession.newWorkflowData("JCR_PATH", playLoad1);
                   WorkflowData workflowData2=workflowSession.newWorkflowData("JCR_PATH", playLoad2);
                   workflowSession.startWorkflow(workflowModel, workflowData1);
                   workflowSession.startWorkflow(workflowModel, workflowData2);
                }
                catch(Exception e){
                    response.getWriter().write("unable to do that please");

                }
               

            } catch (Exception e) {
                response.getWriter().write("Error creating pages: " + e.getMessage());
            }
        } else {
            response.getWriter().write("Failed to obtain ResourceResolver.");
        }
    }
}
