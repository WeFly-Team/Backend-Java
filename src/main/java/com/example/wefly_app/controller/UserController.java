package com.example.wefly_app.controller;

import com.example.wefly_app.entity.User;
import com.example.wefly_app.request.user.UpdateUserModel;
import com.example.wefly_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    public UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> update(@Valid @RequestBody UpdateUserModel request) {
        return new ResponseEntity<>(userService.update(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping(value = {"/delete", "/delete/"})
    public ResponseEntity<Map> delete(@RequestBody User request) {
        return new ResponseEntity<>(userService.delete(request), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = {"/profile", "/profile/"})
    public ResponseEntity<Map> getById() {
        return new ResponseEntity<>(userService.getByIdUser(), HttpStatus.OK);
    }


    @GetMapping(value = {"/test", "/test/"})
    public ResponseEntity<Map> test() {
        Map test = new HashMap<>();
        test.put("test", "test");
        System.out.println("h");
        return new ResponseEntity<Map>(test, HttpStatus.OK);
    }
}


