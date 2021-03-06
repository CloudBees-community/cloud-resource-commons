package org.cloudbees.cloud_resource.jersey;

import com.cloudbees.cloud_resource.auth.Secure;
import com.cloudbees.cloud_resource.types.CloudResource;
import com.cloudbees.cloud_resource.types.CloudResourceTypes;
import com.cloudbees.cloud_resource.types.ReferencedResource;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * A partial default implementation of Jersey {@link CloudResource}.
 *
 * Subtypes should use Jackson annotations on the fields of this class which makes the state (in the REST sense)
 * of the object.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class CloudResourceSupport implements CloudResource {
    /**
     * Sends a state of this object.
     */
    @GET
    @Produces(CloudResource.CONTENT_TYPE)
    @Secure(capabilities={"https://types.cloudbees.com/resource/read"})
    public Response doIndex() {
        return asResponse(this);
    }

    public static Response asResponse(CloudResource res) {
        ResponseBuilder rsp = Response.ok(res);
        for (String t : CloudResourceTypes.of(res)) {
            rsp.header("X-Cloud-Resource-Type",t);
        }
        return rsp.build();
    }

    public static Response asResponse(ReferencedResource referencedResource) {
        ResponseBuilder rsp = Response.ok(referencedResource);
        for (String t : referencedResource.getTypes()) {
            rsp.header("X-Cloud-Resource-Type",t);
        }
        return rsp.build();
    }
}
