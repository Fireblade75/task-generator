package nl.firepy.taskgenerator.account.login;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.firepy.taskgenerator.common.persistence.entities.Account;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    private String email;
    private String password;

    public String getHash() {
        return BCrypt.withDefaults().hashToString(Account.hashRounds, password.toCharArray());
    }

    public boolean isValid() {
        return email.length() > 0 && email.length() < 64
                && password.length() > 0 && password.length() < 32;
    }
}
