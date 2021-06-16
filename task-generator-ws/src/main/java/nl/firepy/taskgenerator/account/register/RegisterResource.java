package nl.firepy.taskgenerator.account.register;

import lombok.extern.log4j.Log4j2;
import nl.firepy.taskgenerator.account.AccountResponse;
import nl.firepy.taskgenerator.common.errors.web.ApiError;
import nl.firepy.taskgenerator.common.persistence.daos.AccountsDao;
import nl.firepy.taskgenerator.common.persistence.daos.ProjectsDao;
import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;
import nl.firepy.taskgenerator.common.persistence.entities.ProjectEntity;

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
    AccountsDao accountsDao;

    @Inject
    ProjectsDao projectsDao;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest request) {

        if(!request.isValid()) {
            return ApiError.buildResponse(400, "Ongeldig request");
        }

        boolean emailExists = accountsDao.containsEmail(request.getEmail());
        if(emailExists) {
            log.info("Email already in use: " + request.getEmail());
            return ApiError.buildResponse(409, "E-mail adres al in gebruik");
        }

        AccountEntity account = AccountEntity.builder()
                .email(request.getEmail().toLowerCase())
                .passwordHash(request.getHash())
                .build();
        accountsDao.save(account);
        log.info("Registered new user: " + request.getEmail());

        projectsDao.save(ProjectEntity.builder()
                .projectName("defaultProject")
                .owner(account)
                .build());

        return Response.status(201).entity(new AccountResponse(request.getEmail(), 0)).build();
    }
}
