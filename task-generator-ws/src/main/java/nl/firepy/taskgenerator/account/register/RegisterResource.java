package nl.firepy.taskgenerator.account.register;

import lombok.extern.log4j.Log4j2;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Log4j2
@RequestScoped
@Path("/accounts/register")
public class RegisterResource {
    @Inject
    AccountDao accountDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest request) {

        if(!request.isValid()) {
            return ApiError.buildResponse(400, "Ongeldig request");
        }

        boolean emailExists = accountDao.containsEmail(request.getEmail());
        if(emailExists) {
            log.info("Email already in use: " + request.getEmail());
            return ApiError.buildResponse(409, "E-mail adres al in gebruik");
        }

        Account account = Account.builder()
                .email(request.getEmail().toLowerCase())
                .passwordHash(request.getHash())
                .build();
        accountDao.save(account);
        log.info("Registered new user: " + request.getEmail());

        return Response.status(201).entity(new AccountResponse(request.getEmail(), 0)).build();
    }
}
