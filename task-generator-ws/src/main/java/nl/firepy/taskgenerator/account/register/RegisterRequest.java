package nl.firepy.taskgenerator.account.register;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.firepy.taskgenerator.account.login.LoginRequest;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RegisterRequest extends LoginRequest implements Serializable {
    @NotNull
    @AssertTrue
    private boolean tos;
}
