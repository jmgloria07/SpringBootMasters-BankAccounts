package com.example.bankaccounts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankaccounts.authentication.ApplicationUserDaoService;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    ApplicationUserDaoService userService;

    /*@GetMapping
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<Users>> getAllUser(Pageable pageable) {
        Page<Users> list = userService.getAllUsers(pageable);
        return new ResponseEntity<Page<Users>>(list, new HttpHeaders(), HttpStatus.OK);
    }*/
}
