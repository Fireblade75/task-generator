package nl.firepy.taskgenerator.common.persistence.entities;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@NamedQueries({
        @NamedQuery(name="Account.findByMail", query="SELECT a FROM Account a WHERE a.email = :email")
})
public class Account {

    public static final int hashRounds = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String passwordHash;

    public boolean verifyPassword(String password) {
        var res = BCrypt.verifyer().verify(password.toCharArray(), passwordHash);
        return res.verified;
    }
}
