package com.maurilio.autorizacao.controller;

import com.maurilio.autorizacao.Dto.LoginDto;
import com.maurilio.autorizacao.security.UserData;
import com.maurilio.autorizacao.serice.TokenService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/home")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("This is home page!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody(required = false) LoginDto login){

        String password = new BCryptPasswordEncoder().encode(login.getPassword());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword());

        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserData user = (UserData)authenticate.getPrincipal();

        return new ResponseEntity<String>(this.tokenService.gerarToken(user), HttpStatusCode.valueOf(201));
    }

    @GetMapping("/authenticated")
    public ResponseEntity<String> authenticated(){
        return ResponseEntity.ok("You are authenticated!");
    }

    @GetMapping("/authorized")
    //@RolesAllowed("ADMIN")
    @PostAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> authorized() {
        return ResponseEntity.ok("You are authorized!");
    }

}
