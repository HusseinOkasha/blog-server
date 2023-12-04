package com.blog.blog.rest;

import com.blog.blog.entity.User;
import com.blog.blog.security.EncryptionService;
import com.blog.blog.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class SignupController {
    private UserService userService;

    private EncryptionService encryptionService;
    public SignupController(UserService userService, EncryptionService encryptionService){
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        try{
            String password  = user.getPassword();
            String bCryptPassword = encryptionService.encryptString(password);
            user.setPassword(bCryptPassword);
            userService.save(user);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("you may have entered empty field or an already existing email address");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("User created successfully");
    }


}
