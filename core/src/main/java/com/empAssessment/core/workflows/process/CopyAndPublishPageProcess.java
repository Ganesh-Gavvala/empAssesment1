package com.empAssessment.core.workflows.process;

import org.osgi.service.component.annotations.Component;

import com.adobe.granite.workflow.exec.WorkflowProcess;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;

import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;

import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = WorkflowProcess.class,
        property = {"process.label=Copy and Publish Page Process"})
public class CopyAndPublishPageProcess implements WorkflowProcess{
    private static final Logger log = LoggerFactory.getLogger(CopyAndPublishPageProcess.class);
    @Reference private Replicator replicator;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
            throws WorkflowException {
        // Adapt WorkFlowSession to ResourceResolver
        ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);
        if (resolver == null) {
            throw new WorkflowException("Unable to adapt WorkflowSession to ResourceResolver.");
        }

        // Get JCR Session
        Session session = resolver.adaptTo(Session.class);
        if (session == null) {
            throw new WorkflowException("Unable to adapt ResourceResolver to JCR Session.");
        }

        // Get playLoad Path (Source Page Path)
        String sourcePath = workItem.getWorkflowData().getPayload().toString();
        String destinationPath = "/content/empAssessment1/language-master/fr-fr "; // Target path

        try {
            // Check if source exists
            Resource sourceResource = resolver.getResource(sourcePath);
            if (sourceResource == null) {
                throw new WorkflowException("Source page does not exist at path: " + sourcePath);
            }

            // Perform JCR Copy
            Workspace workspace = session.getWorkspace();
            String targetPagePath =
                    destinationPath
                            + "/"
                            + ResourceUtil.getName(sourcePath); // Append page name to target path

            // Copy page
            workspace.copy(sourcePath, targetPagePath);
            session.save(); // Commit the copy operation
            System.out.println(
                    "Page copied successfully from " + sourcePath + " to " + targetPagePath);

            // Publish the copied page
            replicator.replicate(session, ReplicationActionType.ACTIVATE, targetPagePath);
            System.out.println("Page published successfully at: " + targetPagePath);

        } catch (RepositoryException e) {
            throw new WorkflowException("Error during page copy operation", e);
        } catch (ReplicationException e) {
            throw new WorkflowException("Error during page publishing operation", e);
        }
    }

}
