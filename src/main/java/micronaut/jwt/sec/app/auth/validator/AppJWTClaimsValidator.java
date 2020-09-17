package micronaut.jwt.sec.app.auth.validator;

import com.nimbusds.jwt.JWTClaimsSet;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JWTClaimsSetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class AppJWTClaimsValidator implements GenericJwtClaimsValidator {

    private static final Logger LOG = LoggerFactory.getLogger(AppJWTClaimsValidator.class);

    /**
     * @param claimsSet The JWT Claims
     * @return true if the session_id and user_agent claims matches values in request.
     */
    protected boolean validate(@NonNull JWTClaimsSet claimsSet) {
        String sessionId = (String) claimsSet.getClaim("session_id");
        String userAgent = (String) claimsSet.getClaim("user_agent");
        Optional<HttpRequest<Object>> httpRequestOptional = ServerRequestContext.currentRequest();
        if (httpRequestOptional.isPresent()) {
            String sessionIdInRequest = httpRequestOptional.get().getCookies().get("sessionId").getValue();
            String userAgentInRequest = httpRequestOptional.get().getHeaders().get("user-agent");
            if (sessionId.equals(sessionIdInRequest) &&
                    userAgent.equalsIgnoreCase(userAgentInRequest)) {
                return true;
            }
            LOG.error("JWT token is invalid. SessionId in token :" + sessionId +
                    " user_agent in token:" + userAgent + " SessionId in request: " + sessionIdInRequest +
                    " user_agent in request:" + userAgentInRequest);
        }

        return false;
    }

    @Override
    public boolean validate(JwtClaims claims) {
        return validate(JWTClaimsSetUtils.jwtClaimsSetFromClaims(claims));
    }

}