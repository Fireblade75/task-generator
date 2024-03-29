package nl.firepy.taskgenerator.account.register;

import nl.firepy.taskgenerator.common.persistence.daos.AccountsDao;
import org.junit.jupiter.api.Disabled;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
class RegisterResourceTest {

    @Mock
    private AccountsDao accountsDao;

    @InjectMocks
    private RegisterResource registerResource;

    private static RegisterRequest goodRequest;
    private static RegisterRequest badRequest;

    @BeforeAll
    static void init() {
        goodRequest = new RegisterRequest();
        goodRequest.setEmail("info@example.com");
        goodRequest.setPassword("123456789");
        goodRequest.setTos(true);

        badRequest = new RegisterRequest();
        badRequest.setEmail("info@example.com");
        badRequest.setPassword("123456789");
        badRequest.setTos(false);
    }

    @Test
    void register() {
        when(accountsDao.containsEmail(eq("info@example.com"))).thenReturn(false);

        Response response = registerResource.register(goodRequest);

        assertThat(response.getStatus()).isEqualTo(201);
        verify(accountsDao).containsEmail(anyString());
        verify(accountsDao).save(any());
    }

    @Test
    void registerExistingMail() {
        when(accountsDao.containsEmail(eq("info@example.com"))).thenReturn(true);

        Response response = registerResource.register(goodRequest);

        assertThat(response.getStatus()).isEqualTo(409);
        verify(accountsDao).containsEmail(anyString());
    }

    @Test
    void registerWithoutAcceptingTos() {
        Response response = registerResource.register(badRequest);
        assertThat(response.getStatus()).isEqualTo(400);
    }
}