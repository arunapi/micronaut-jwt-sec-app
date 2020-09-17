package micronaut.jwt.sec.app.auth.provider;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.Maybe;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AppAuthenticationProvider  implements AuthenticationProvider {

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Maybe.<AuthenticationResponse>create(emitter -> {
            if (authenticationRequest.getIdentity().equals("client_id") && authenticationRequest.getSecret().equals("client_secret")) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("user_agent",httpRequest.getHeaders().get("user-agent"));
                attributes.put("session_id","uuid");
                emitter.onSuccess(new UserDetails("new-app-user", new ArrayList<>(),attributes));
            } else {
                emitter.onError(new AuthenticationException(new AuthenticationFailed()));
            }
        }).toFlowable();
    }
}
