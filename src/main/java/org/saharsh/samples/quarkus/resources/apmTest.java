package org.saharsh.samples.quarkus.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import co.elastic.apm.attach.ElasticApmAttacher;

@Path("/hi")
public class apmTest {

    // @Inject
    // Logger logger;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        ElasticApmAttacher.attach();
        // Your application code here
        Transaction transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("MyController#myActionNewApm");
            transaction.setType(Transaction.TYPE_REQUEST);
            // do your thing...
            // logger.info("Apm-working-fine");
        } catch (Exception e) {
            transaction.captureException(e);
            throw e;
        } finally {
            transaction.end();
        }
        return "Hello from Apm-server app";

    }
    @GET
    @Path("/hi")
    @Produces(MediaType.TEXT_PLAIN)
    public void helloo() {
        ElasticApmAttacher.attach();
        // Your application code here
        Transaction transaction = ElasticApm.startTransaction();
        try {
            transaction.setName("MyControllerHellooo");
            transaction.setType("Get");
            // do your thing...
            // logger.error("transaction about to fail");
            throw new RuntimeException();
        } catch (Exception e) {
            // logger.error("message", e);
            transaction.captureException(e);
            throw e;
        } finally {
            transaction.end();
        }

    }

}
