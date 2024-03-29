package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.controller;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.LoginDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserRegistrationDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IAuthenticationService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
    private final ConversionService conversionService;

    public AuthenticationController(IAuthenticationService authenticationService, ConversionService conversionService) {
        this.authenticationService = authenticationService;
        this.conversionService = conversionService;
    }

    @PostMapping(value ="/registration", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        authenticationService.registration(userRegistrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> verification(@RequestParam UUID code,
                                          @RequestParam String mail){
        boolean isActivate = authenticationService.activate(mail, code);
        if (!isActivate){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Activation was successful", HttpStatus.ACCEPTED);
    }

    @PostMapping(value ="/login", consumes = "application/json", produces = "application/json")
    public String login (@RequestBody LoginDTO loginDTO){
        return authenticationService.login(loginDTO);
    }

    @GetMapping(value ="/me", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> getMe (){
        User me = this.authenticationService.getMe();
        UserDTO userDTO = this.conversionService.convert(me, UserDTO.class);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


}
