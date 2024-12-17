package com.empAssessment.core.workflows.process;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(
    service = WorkflowProcess.class,
    property = {
        "process.label=Custom Message failur"
    }
)
public class CustomMessagereject implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(CustomMessagereject.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metadata) throws WorkflowException{
        try {
            // Get the payload path
            //String payloadPath = workItem.getWorkflowData().getPayload().toString();
            
            // Log payload path
            LOG.info("Executing CustomMessage failur.");
            //LOG.info("Payload Path: {}", payloadPath);
            
        } catch(Exception e) {
            LOG.error("Exception occurred in CustomMessage failur", e);
        }
    }
}
