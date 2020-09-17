package micronaut.jwt.sec.app.resource;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import micronaut.jwt.sec.app.Application;

import java.util.Collections;
import java.util.Map;

@Controller("/resource")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class AppController {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,String> getAccount(HttpRequest httpRequest){
        return Collections.singletonMap("account-name","my-app-user");
    }
}
