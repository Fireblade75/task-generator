package nl.firepy.taskgenerator.account.login;

import nl.firepy.taskgenerator.account.AccountResponse;
import nl.firepy.taskgenerator.common.errors.web.ApiError;
import nl.firepy.taskgenerator.common.persistence.daos.AccountsDao;
import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;
import nl.firepy.taskgenerator.common.security.JwtTokenService;
import nl.firepy.taskgenerator.common.security.TokenPayload;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;

@RequestScoped
@Path("/accounts/login")
public class LoginResource {
    @Context
    private UriInfo context;

    @Inject
    AccountsDao accountsDao;

    @Inject
    JwtTokenService jwtTokenService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequest loginRequest) {
        Optional<AccountEntity> account = accountsDao.findByMail(loginRequest.getEmail());
        if(account.isPresent() && account.get().verifyPassword(loginRequest.getPassword())) {
            String email = account.get().getEmail();
            List<String> roles = List.of("user");
            String token = jwtTokenService.getToken(
                    TokenPayload.builder().email(email).roles(roles).build());

            return Response
                    .ok(new AccountResponse(email, jwtTokenService.getExpireTime()))
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .build();

        } else {
            return Response.status(401).entity(new ApiError("Login failed")).build();
        }

    }
}
