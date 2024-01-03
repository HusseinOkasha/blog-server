# Description
Blog server is a personal project for practice purposes. It is a simple blog project you can create account, write, edit,
list or delete your own posts.

****

# How to run
The project is dockerized and is divided into two services the first is the mysql which will be built from
"**Dockerfile-mysql**" and the second is the spring app which I have created an image of it and pushed to dockerhub.

## Steps
* Clone the repo `git clone https://github.com/HusseinOkasha/blog-server.git`
* Navigate to ``blog-server/blog``
* Open the terminal or cmd on this path
* Run `docker compose up` will:
  * start mysql based on Dockerfile-mysql
  * pull image husseinokasha/blog from dockerhub if it doesn't exist on your computer.
  * It will take 6 to 10 minutes to start + the download time. 

In order to run successfully make sure you have no process using ports 8080 or 3306.

# User Guide
I have a user created under email: "f1@gmail.com", password: "123" owning one post.

### Signup
* #### Command
  * `curl -X POST -H "Content-Type: application/json"  -d '{"firstName":"f2", "lastName":"l2", "email":"f2@gmail.com", "password":123 }' http://localhost:8080/api/signup`
* #### Explanation
  * It accepts a post request with user information (firstName, lastName, email, password)
  * In case of successful signup it returns status code 200 OK.
  * In case of missing field returns status code 400 BAD_REQUEST.
  * In case of using already existing email address will return 400 BAD_REQUEST.

### Login
* #### Command
  * `curl -X POST -H "Content-Type: application/json" -u f2@gmail.com:123 http://localhost:8080/login`
* #### Explanation
  * It accepts http post requests with user credentials (basic auth)
  * In case of successful login, it returns a jwt token which you should use with your future requests.
  * In case of failed login, returns status code 401 UNAUTHORIZED.

### Create Post
* #### Command
  * `curl -X POST -H "Content-Type: application/json" -H "Authorization: Bearer token" -d '{"body": "f2 first post"}' http://localhost:8080/api/posts`
* #### Explanation
  * Replace "token" in the command with the token you got after a successful login.
  * With authorization header containing a valid bearer token, and the post body, it returns status code CREATED 201 and
  postDto like `{
    "id": 1,
    "body": "f2 first post",
    "createdAt": "2024-01-02T13:23:04.009175413",
    "updatedAt": "2024-01-02T13:23:04.009210933",
    "userEmail": "f2@gmail.com"
    }`
  * In case of empty post body `{"body": ""}` or missing post body `{}`, returns:
    * Status code 400 bad request
    * Along with empty postDTO like `{
      "id": 0,"body": "","createdAt": null,"updatedAt": null, "userEmail": null }`.
  * In case of missing request body, returns
    * Status code 400 BAD_REQUEST
  * In case the user embedded in the bearer token doesn't exist it will return status code 401 UNAUTHORIZED

### List your posts
* #### Command
  * `curl -H "Content-Type: application/json" -H "Authorization: Bearer token" http://localhost:8080/api/posts`
* #### Explanation
  * Replace "token" in the command with the token you got after a successful login.
  * It returns all posts of the user owning the token.
  * In case the user embedded in the token doesn't exist it returns status code UNAUTHORIZED 401.

### Edit post
* #### Command
  * `curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer token" -d '{"id": "1", "body": "f2 first post update!"}' http://localhost:8080/api/posts`
* #### Explanation
  * When you create a post you will get a postDto containing the post database id, which you should use to identify the 
post you want to edit.
  * Replace "token" in the command with the token you got after a successful login.
  * With authorization header containing a valid bearer token, and the post body, it returns status code CREATED 201 and
    postDto like `{
    "id": 1,
    "body": "f2 first post",
    "createdAt": "2024-01-02T13:23:04.009175413",
    "updatedAt": "2024-01-02T13:23:04.009210933",
    "userEmail": "f2@gmail.com"
    }`
  * In case of empty post body `{"body": ""}` or missing post body `{}`, returns:
    * Status code 400 BAD_REQUEST
    * Along with empty postDTO like `{
      "id": 0,"body": "","createdAt": null,"updatedAt": null, "userEmail": null }`.
  * In case of missing request body, returns
    * Status code 400 BAD_REQUEST
  * In case the user embedded in the bearer token doesn't exist it will return status code 401 UNAUTHORIZED.
  * In case the user embedded in the bearer token doesn't own the post identified by the provided id it returns.
    * Status code 401 UNAUTHORIZED.
  * In case of missing id, returns
    * Status code 400 BAD_REQUEST
    * Message saying that the post you are trying to update doesn't exist

### Delete post
* #### Command
  * `curl -X DELETE -H "Content-Type: application/json" -H "Authorization: Bearer token" -d '{"id": "1", "body": "f2 first post update!"}' http://localhost:8080/api/posts`
* #### Explanation
  * When you create a post you will get a postDto containing the post database id, which you should use to identify the
    post you want to delete.
  * Replace "token" in the command with the token you got after a successful login.
  * In case of missing post id in the request body, it returns
    * status code 400 BAD_REQUEST.
    * Message saying post you are trying to delete doesn't exist.
  * In case of absence of a post with the provided id, it returns
    * status code 404 NOT_FOUND.
    * Message saying post you are trying to update doesn't exist.
  * In case the user embedded in the bearer token doesn't exist it will return status code 401 UNAUTHORIZED.
  * In case the user embedded in the bearer token doesn't own the post identified by the provided id it returns.
    * Status code 401 UNAUTHORIZED.
  * In case of successful deletion, it returns 
    * Status code 200 OK
    * Message saying post deleted successfully.

****
