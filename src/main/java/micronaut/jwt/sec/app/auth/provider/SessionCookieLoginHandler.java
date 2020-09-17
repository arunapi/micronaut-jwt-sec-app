package micronaut.jwt.sec.app.auth.provider;

import io.micronaut.context.annotation.Primary;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.http.cookie.SameSite;
import io.micronaut.security.authentication.UserDetails;
import io.micronaut.security.token.jwt.bearer.AccessRefreshTokenLoginHandler;
import io.micronaut.security.token.jwt.generator.AccessRefreshTokenGenerator;

import javax.inject.Singleton;
import java.time.Duration;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.UUID;

@Singleton
@Primary
public class SessionCookieLoginHandler extends AccessRefreshTokenLoginHandler {
    /**
     * @param accessRefreshTokenGenerator AccessRefresh Token generator
     */
    public SessionCookieLoginHandler(AccessRefreshTokenGenerator accessRefreshTokenGenerator) {
        super(accessRefreshTokenGenerator);
    }

    @Override
    public MutableHttpResponse<?> loginSuccess(UserDetails userDetails, HttpRequest<?> request) {
        return applyCookies(super.loginSuccess(userDetails,request), userDetails);

    }

    protected MutableHttpResponse<?> applyCookies(MutableHttpResponse<?> response,UserDetails userDetails) {
        Cookie sessionCookie = Cookie.of("sessionId", (String)userDetails
                .getAttributes("",userDetails.getUsername()).get("session_id"));
        sessionCookie.secure(false);
        sessionCookie.sameSite(SameSite.None);

        response = response.cookie(sessionCookie);

        return response;
    }
    }
