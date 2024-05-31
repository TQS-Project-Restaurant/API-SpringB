package tqs.project.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import tqs.project.api.dao.SignInRequest;
import tqs.project.api.dao.SignInResponse;
import tqs.project.api.services.AuthenticationService;

@RestController
@Tag(name = "Authentication API")
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }


    @PostMapping
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request){
        return new ResponseEntity<>(authenticationService.signin(request), HttpStatus.OK);
    }
}
