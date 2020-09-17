package micronaut.jwt.sec.app.auth.provider;

import io.micronaut.security.authentication.UserDetails;

import java.util.Collection;
import java.util.Map;

public class AppUserDetails extends UserDetails {

    String user_agent;

    String session_id;

    public AppUserDetails(String username, Collection<String> roles) {
        super(username, roles);
    }

    public AppUserDetails(String username, Collection<String> roles, Map<String, Object> attributes) {
        super(username, roles, attributes);
    }


    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
