package nl.firepy.taskgenerator.account.login;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    @NotNull
    private String email;

    @NotNull
    private String password;

    public String getHash() {
        return BCrypt.withDefaults().hashToString(AccountEntity.hashRounds, password.toCharArray());
    }

    public boolean isValid() {
        return email != null && password != null && email.length() > 0
                && email.length() <= 64 && password.length() > 0 && password.length() <= 32;
    }
}
