# Sample micronaut application to demo JWT auth

The app has an endpoint `GET /resource`

In order to access the endpoint you need to get a JWT token and sessionId cookie.

You will get them by sending a request to `POST /login` with client credentials.

Checkout the spec file for request and response.
[`src/test/java/micronaut/jwt/sec/app/AppAuthProviderSpec.java`](/src/test/java/micronaut/jwt/sec/app/AppAuthProviderSpec.java)

Or start the app and use the below curl commands

To get token:
```
curl -X POST http://localhost:8080/login -H 'Content-Type: application/x-www-form-urlencoded' -d 'username=client_id&password=client_secret' 
```

To access resource:
```
curl http://localhost:8080/resource -H 'Authorization: bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZXctYXBwLXVzZXIiLCJuYmYiOjE2MDA0MjYxMTcsInJvbGVzIjpbXSwiaXNzIjoibWljcm9uYXV0LWp3dC1zZWMtYXBwIiwic2Vzc2lvbl9pZCI6InV1aWQiLCJleHAiOjE2MDA0Mjk3MTcsImlhdCI6MTYwMDQyNjExNywidXNlcl9hZ2VudCI6ImN1cmxcLzcuNjguMCJ9.zBPcB0kEYNN6PGmLwvobGp-2MAvvah2BnNZA5X0tGNE' -H 'Cookie: sessionId=uuid'
```


## Feature http-client documentation

- [Micronaut Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature security-jwt documentation

- [Micronaut Micronaut Security JWT documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

