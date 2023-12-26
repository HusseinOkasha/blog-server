# Description
Blog server is a personal project for practice purposes. It is a simple blog project you can create account, write, edit    
, list or delete your own posts.

## Endpoints
* **/login**
  * It accepts http post requests with user credential (basic auth)
  * In case of successfull login, it returns a jwt token which you should use with your future requests, otherwise returns 
   status code 401 unauthorized.
  
* **/api/signup**
  * It accepts a post request with user information (firstName, lastName, email, password)
  * In case of successful signup it returns status code 200, otherwise returns 400 bad request(in case of missing field
   or duplicate email address).
****

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
