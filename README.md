# Description
Blog server is a personal project for practice purposes. It is a simple blog project you can create account, write, edit,
list or delete your own posts.

****

# How to run
The project is dockerized and is divided into two services the first is the mysql which will be built from
"**Dockerfile-mysql**" and the second is the spring app which will be built from "**Dockerfile**"

## Steps
* Clone the repo `git clone https://github.com/HusseinOkasha/blog-server.git`
* Navigate to ``blog-server/blog``
* Open the terminal or cmd on this path
* Run `./mvnw clean package` 
   * **clean**  will clean the directory from existing build files.
   * **package** will package the application generating a jar file containing compiled code and it's dependencies
* Run `docker build -t blog .`
  * will build a docker image based on Dockerfile in the current directory and give that image a tag 'blog'
* Run  `docker compose up` will start:
  * mysql based on Dockerfile-mysql
  * the spring app based on the image we have created above. 

In order to run successfully make sure you have no process using ports 8080 or 3306.

# User Guide
I have a user created under email: "f1@gmail.com", password: "123" owning one post.

### Signup

* `curl -X POST -H "Content-Type: application/json"  -d '
{
"firstName":"f2",
"lastName":"l2",
"email":"f2@gmail.com",
"password":123
}' http://localhost:8080/api/signup
`
* It accepts a post request with user information (firstName, lastName, email, password)
* In case of successful signup it returns status code 200.
* In case of missing field returns status code 400 bad request.
* In case of using already existing email address will return 400 bad request.

### Login
* `curl -X POST -H "Content-Type: application/json"  -u f2@gmail.com:123 http://localhost:8080/login`
* It accepts http post requests with user credentials (basic auth)
* In case of successfull login, it returns a jwt token which you should use with your future requests.
* In case of failed login, returns status code 401 unauthorized.

### Create Post
* `curl -X POST \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer token" \
  -d '{"body": "f2 first post"}' \
  http://localhost:8080/api/posts
  `
* Replace "token" in the command with the token you got after a sucessfull login.
* Along with authorization header containing a valid bearer token, and the post body it will return status code
  created 201.
* In case of empty post body `{"body": ""}` or missing post body `{}`, returns: 
  * Status code 400 bad request
  * Along with empty postDTO like `{
    "id": 0,"body": "","createdAt": null,"updatedAt": null, "userEmail": null }`.
* In case of missing request body, returns
  * Status code 400 bad request
* In case the user embedded in the bearer token doesn't exist it will return status code 401 unauthorized

### List your posts
* `curl -H "Content-Type: application/json" \
  -H "Authorization: Bearer token" \
  http://localhost:8080/api/posts
  `
* Replace "token" in the command with the token you got after a sucessfull login.
* Along with authorization header containing a valid bearer token, it will return all posts of the user 
owning the token.
* In case the user embedded in the token doesn't exist it returns status code unauthorized 401.

### Edit post
Currently, it requires that you know the database id of the post so, I will solve this issue first.

### Delete post
Currently, it requires that you know the database id of the post so, I will solve this issue first.

****
## Endpoints
* **/login**
  * It accepts http post requests with user credential (basic auth)
  * In case of successfull login, it returns a jwt token which you should use with your future requests, otherwise returns 
   status code 401 unauthorized.
  
* **/api/signup**
  * It accepts a post request with user information (firstName, lastName, email, password)
  * In case of successful signup it returns status code 200.
  * In case of  missing field returns status code 400 bad request .
  * It allows duplicate email addresses due to a forgotten unique constraint in the sql schema (will be handled in
  future ISA )


### /api/posts

* GET **/api/posts**
  * Along with authorization header containing a valid bearer token, it will return all posts of the user
   owning the token.
  * If the user embedded in the token doesn't exist it returns status code unauthorized 401.
* POST **api/posts**
  * Along with authorization header containing a valid bearer token, and the post body it will return status code 
  created 201.
  * In case of empty post body `{"body":""" }` it will work as the above scenario. (will be handled in future ISA)
  * In case of missing post body `{}`, returns internal server error 500. (will be handled in future ISA)
  * If the user embedded in the bearer token doesn't exist it will return status code 401 unauthorized  
* PUT **api/post**
  * Along with authorization header containing a valid bearer token, and a request body contains the post id and the
  updated post body, it returns status code 200 ok 
  * If the user embedded in the bearer token doesn't exist it will return status code 401 unauthorized
  * In case of empty post body `{"id":1 ,"body": """}` it returns status code 200 ok (will be handled in future ISA).
  * In case of missing post id `{"body": """}` it returns status code 400 bad request saying the post you are trying to 
  update doesn't exist.
  * In case of missing post body `{"id": 1 "}` it returns status code 500 internal server error (will be handled in
  future ISA).
* DELETE **api/posts**
  * It expects an authorization header containing a valid bearer token and a request body containing the id of the
  post to be deleted `{"id": 1}`.
  * If the post id doesn't exist `{}` or belongs to a user other than the one specified in the bearer token, it returns
  status code unauthorized 401
****

