package nl.firepy.taskgenerator.account.login;

import nl.firepy.taskgenerator.common.persistence.daos.AccountsDao;
import nl.firepy.taskgenerator.common.persistence.entities.AccountEntity;
import nl.firepy.taskgenerator.common.security.JwtTokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginResourceTest {

    @Mock
    private AccountsDao accountsDao;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private LoginResource loginResource;

    private static LoginRequest goodRequest;
    private static LoginRequest wrongRequest;
    private static LoginRequest invalidRequest;

    private static AccountEntity account;

    @BeforeAll
    static void init() {
        goodRequest = new LoginRequest();
        goodRequest.setEmail("info@example.com");
        goodRequest.setPassword("123456789");

        wrongRequest = new LoginRequest();
        wrongRequest.setEmail("info@example.com");
        wrongRequest.setPassword("987654321");

        invalidRequest = new LoginRequest();
        invalidRequest.setEmail("info@example.com");
        invalidRequest.setPassword("123456789".repeat(10));

        account = AccountEntity.builder()
                .email(goodRequest.getEmail())
                .passwordHash(goodRequest.getHash())
                .build();
    }

    @Test
    void testCorrectLogin() {
        when(accountsDao.findByMail(goodRequest.getEmail())).thenReturn(Optional.of(account));
        when(jwtTokenService.getToken(any())).thenReturn("TOKEN");

        Response response = loginResource.login(goodRequest);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getHeaders()).containsKey(HttpHeaders.AUTHORIZATION);
        assertThat(response.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0))
                .isEqualTo("Bearer TOKEN");

        verify(accountsDao, times(1)).findByMail(anyString());
        verify(jwtTokenService, times(1)).getToken(any());
    }

    @Test
    void testWrongLogin() {
        when(accountsDao.findByMail(goodRequest.getEmail())).thenReturn(Optional.of(account));

        Response response = loginResource.login(wrongRequest);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getHeaders()).doesNotContainKey(HttpHeaders.AUTHORIZATION);

        verify(accountsDao, times(1)).findByMail(anyString());
        verify(jwtTokenService, times(0)).getToken(any());
    }

    @Test
    void testUnknownLogin() {
        when(accountsDao.findByMail(goodRequest.getEmail())).thenReturn(Optional.empty());

        Response response = loginResource.login(goodRequest);

        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.getHeaders()).doesNotContainKey(HttpHeaders.AUTHORIZATION);

        verify(accountsDao, times(1)).findByMail(anyString());
        verify(jwtTokenService, times(0)).getToken(any());
    }

    @Test
    void testInvalidLogin() {
        Response response = loginResource.login(invalidRequest);

        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getHeaders()).doesNotContainKey(HttpHeaders.AUTHORIZATION);

        verify(accountsDao, times(0)).findByMail(anyString());
        verify(jwtTokenService, times(0)).getToken(any());
    }

}