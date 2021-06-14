package nl.firepy.taskgenerator.common.errors.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError implements Serializable {
    public String error;
    public String message;

    public ApiError(String error) {
        this(error, "");
    }

    public static Response buildResponse(int status, String error) {
        return buildResponse(status, error, "");
    }

    public static Response buildResponse(int status, String error, String message) {
        return Response
                .status(status)
                .entity(new ApiError(error, message))
                .build();
    }
}
