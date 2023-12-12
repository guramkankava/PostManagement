The Application is Spring framework based REST API.

It exposes API to manage the Post, Comment, Like, Subscription. See controller package.

API is secured by Bearer token, token can be acquired at POST:/auth/token with Basic Authentication. See controller.AuthController.

User Registration: POST:api/v1/users/register {username, password}. See controller.UserController.

Authentication: POST:/auth/token -Header Authorization Basic username password -> Bearer Token

API utilization: any:/api -H Authorization Bearer token


