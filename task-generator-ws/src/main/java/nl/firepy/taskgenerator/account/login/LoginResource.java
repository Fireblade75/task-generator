package nl.firepy.taskgenerator.account.login;

import nl.firepy.taskgenerator.account.AccountResponse;
import nl.firepy.taskgenerator.common.errors.web.ApiError;
import nl.firepy.taskgenerator.common.persistence.daos.AccountDao;
import nl.firepy.taskgenerator.common.persistence.entities.Account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@RequestScoped
@Path("/accounts/login")
public class LoginResource {
    @Context
    private UriInfo context;

    @Inject
    AccountDao accountDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {

        if(!loginRequest.isValid()) {
            return Response.status(400).entity(new ApiError("Invalid request")).build();
        }

        Optional<Account> optionalAccount = accountDao.match(loginRequest);
        if(optionalAccount.isPresent()) {
            return Response.ok(new AccountResponse()).build();
        } else {
            return Response.status(401).entity(new ApiError("Login failed")).build();
        }

    }
}
