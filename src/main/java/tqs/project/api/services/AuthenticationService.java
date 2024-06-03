package tqs.project.api.services;

import tqs.project.api.dao.SignInRequest;
import tqs.project.api.dao.SignInResponse;

public interface AuthenticationService {
    SignInResponse signin(SignInRequest request);
}
