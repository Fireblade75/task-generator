package nl.firepy.taskgenerator.common.errors.mappers;

import nl.firepy.taskgenerator.common.errors.web.ApiError;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("application/json")
                .build();
    }

    private ApiError prepareMessage(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder();
        for (var cv : exception.getConstraintViolations()) {
            msg.append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\n");
        }
        return new ApiError("ConstraintViolation", msg.toString());
    }
}
