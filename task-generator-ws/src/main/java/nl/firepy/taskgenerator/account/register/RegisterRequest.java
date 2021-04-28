package nl.firepy.taskgenerator.account.register;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nl.firepy.taskgenerator.account.login.LoginRequest;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RegisterRequest extends LoginRequest implements Serializable {
    private boolean tos;

    public boolean isValid() {
        return super.isValid() && tos;
    }
}
