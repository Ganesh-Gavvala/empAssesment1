package com.empAssessment.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.osgi.framework.Constants;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Page Properties Servlet",
        "sling.servlet.paths=empAssessment1/components/resource",
        "sling.servlet.methods=GET",
        "sling.servlet.selectors=fetchdata", // Add selector
        "sling.servlet.extensions=json" // Add extension
})
public class PagePropertiesServlet extends SlingAllMethodsServlet {
    private static final String API_URL = "https://empassessment.free.beeceptor.com/resource";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(API_URL))) {
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                httpResponse.getEntity().writeTo(response.getOutputStream());
            } else {
                response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"Failed to fetch API data\"}");
            }
        } catch (Exception e) {
            response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}
