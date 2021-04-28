package nl.firepy.taskgenerator.account.login;

import nl.firepy.taskgenerator.account.AccountResponse;
import nl.firepy.taskgenerator.common.errors.web.ApiError;
import nl.firepy.taskgenerator.common.persistence.daos.AccountDao;
import nl.firepy.taskgenerator.common.persistence.entities.Account;
import nl.firepy.taskgenerator.security.JwtTokenService;
import nl.firepy.taskgenerator.security.TokenPayload;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.Optional;

@RequestScoped
@Path("/accounts/login")
public class LoginResource {
    @Context
    private UriInfo context;

    @Inject
    AccountDao accountDao;

    @Inject
    JwtTokenService jwtTokenService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {

        if(!loginRequest.isValid()) {
            return Response.status(400).entity(new ApiError("Invalid request")).build();
        }

        Optional<Account> account = accountDao.findByMail(loginRequest.getEmail());
        if(account.isPresent() && account.get().verifyPassword(loginRequest.getPassword())) {
            String email = account.get().getEmail();
            String token = jwtTokenService.getToken(
                    TokenPayload.builder().email(email).build());

            return Response
                    .ok(new AccountResponse(email))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .build();

        } else {
            return Response.status(401).entity(new ApiError("Login failed")).build();
        }

    }
}
