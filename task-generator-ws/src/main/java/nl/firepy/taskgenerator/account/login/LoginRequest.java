package nl.firepy.taskgenerator.account.login;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Size(max = 64)
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 64)
    private String password;

    public String getHash() {
        return BCrypt.withDefaults().hashToString(AccountEntity.hashRounds, password.toCharArray());
    }
}
