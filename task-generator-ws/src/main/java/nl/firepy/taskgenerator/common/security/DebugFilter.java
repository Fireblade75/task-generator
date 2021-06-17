package nl.firepy.taskgenerator.common.security;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class DebugFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext response) {
        MultivaluedMap<String, Object> headers = response.getHeaders();

        headers.add("Access-Control-Allow-Origin", "http://localhost:4200");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "authorization, content-type, accepts");
        headers.add("Access-Control-Expose-Headers", "authorization");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH");
    }
}
