# Sample micronaut application to demo JWT auth

The app has an endpoint `GET /resource`

In order to access the endpoint you need to get a JWT token and sessionId cookie.

You will get them by sending a request to `POST /login` with client credentials.

Checkout the spec file for request and response.
[`src/test/java/micronaut/jwt/sec/app/AppAuthProviderSpec.java`](/src/test/java/micronaut/jwt/sec/app/AppAuthProviderSpec.java)

## Feature http-client documentation

- [Micronaut Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature security-jwt documentation

- [Micronaut Micronaut Security JWT documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

