package micronaut.jwt.sec.app;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.Claims;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.generator.claims.JwtClaimsSetAdapter;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class AppAuthProviderSpec {
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testLogin() throws ParseException {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials();
        creds.setPassword("client_secret");
        creds.setUsername("client_id");
        HttpResponse<?> response = client.toBlocking()
                .exchange(HttpRequest.POST("login",creds).header("user-agent","Mozilla"));
        assertEquals(HttpStatus.OK, response.status());
        assertEquals("sessionId=uuid; SameSite=None",response.getHeaders().get("set-cookie"));
    }

    @Test
    void testJWT() throws ParseException {
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials();
        creds.setPassword("client_secret");
        creds.setUsername("client_id");
        BearerAccessRefreshToken token = client.toBlocking()
                .retrieve(HttpRequest.POST("login",creds).header("user-agent","Mozilla"),
                        BearerAccessRefreshToken.class);
        JWT jwt = JWTParser.parse(token.getAccessToken());
        JwtClaims claims = new JwtClaimsSetAdapter(jwt.getJWTClaimsSet());
        assertEquals("uuid",claims.get("session_id"));
        assertEquals("Mozilla",claims.get("user_agent"));

        String accountResponse = client.toBlocking()
                .retrieve(HttpRequest.GET("resource").header("user-agent","Mozilla")
                        .cookie(Cookie.of("sessionId","uuid"))
                        .header("Authorization","bearer "+token.getAccessToken()));
        assertEquals("{\"account-name\":\"my-app-user\"}",accountResponse);
    }
}