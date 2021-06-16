package nl.firepy.taskgenerator.common.security;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.common.security.annotations.RequiresRole;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
@Log4j2
@RequiresRole("")
@Priority(Priorities.AUTHENTICATION)
public class AuthFilter implements ContainerRequestFilter {

    @Context
    ResourceInfo resourceInfo;

    @Inject
    private JwtTokenService jwtTokenService;

    @Inject
    private AuthData authData;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String requiredRole = "";
        RequiresRole methodRole = resourceInfo.getResourceMethod().getAnnotation(RequiresRole.class);
        RequiresRole classRole = resourceInfo.getResourceClass().getAnnotation(RequiresRole.class);
        if(methodRole == null && classRole != null) {
            requiredRole = classRole.value();
        } else if (methodRole != null) {
            requiredRole = methodRole.value();
        } else {
            log.error(resourceInfo.getResourceMethod().getName() + " lacks the RequiresRole annotation");
            throw new InternalServerErrorException();
        }

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();

        TokenPayload payload = jwtTokenService.verify(token);

        if(payload == null) {
            throw new NotAuthorizedException("Invalid JWT Token");
        } else {
            authData.setTokenPayload(payload);
        }
    }
}
