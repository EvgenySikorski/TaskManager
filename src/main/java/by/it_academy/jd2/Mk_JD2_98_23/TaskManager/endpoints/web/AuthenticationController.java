package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    private final IAuthenticationService authenticationServiceService;

    public AuthenticationController(IAuthenticationService authenticationServiceService) {
        this.authenticationServiceService = authenticationServiceService;
    }

    @PostMapping(value ="/registration", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        authenticationServiceService.registration(userRegistrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> verification(@RequestParam String code,
                                          @RequestParam String mail){
        boolean isActivate = authenticationServiceService.activate(mail, code);
        if (!isActivate){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Activation was successful", HttpStatus.ACCEPTED);
    }
}
