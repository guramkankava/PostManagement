The Application is Spring framework based REST API.

It exposes API at http://localhost:8080/postmanagement/ to manage the Post, Comment, Like, Subscription. See controller package.

API is secured by Bearer token, token can be acquired at POST:/auth/token with Basic Authentication. See controller.AuthController.

User Registration: POST:api/v1/users/register {username, password}. See controller.UserController.

Authentication: POST:/auth/token -Header Authorization Basic username password -> Bearer Token

API utilization: any:/api -H Authorization Bearer token

Swagger/OpenAPI is available at http://localhost:8080/postmanagement/swagger-ui/index.html#/ 

Running in container<br />
1 build image : ./gradlew bootBuildImage --imageName=githubguramkankava/postmanagement <br />
2 docker-compose up