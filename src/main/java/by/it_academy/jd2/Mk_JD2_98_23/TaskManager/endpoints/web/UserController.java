package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.convertors.UserToUserDTOConvertor;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.*;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity.User;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserToUserDTOConvertor convertor;

    public UserController(IUserService userService, UserToUserDTOConvertor convertor) {
        this.userService = userService;
        this.convertor = convertor;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> save(@RequestBody UserCreateDTO userCreateDTO){
        User createUser = this.userService.save(userCreateDTO);
        UserDTO userDTO = this.convertor.convert(createUser);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping(consumes = "application/json", produces = "application/json" )
    public ResponseEntity<PageOfUserDTO> page(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PageOfUserDTO pageOfUserDTO = userService.get(pageRequest);
        return new ResponseEntity<>(pageOfUserDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "{uuid}", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<UserDTO> readCard(@PathVariable UUID uuid) {
        User user = userService.get(uuid);
        UserDTO userDTO = this.convertor.convert(user);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/{uuid}/dt_update/{dt_update}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDTO> update(@PathVariable UUID uuid,
                                          @PathVariable ("dt_update") Long dt_update,
                                          @RequestBody UserUpdateDTO userUpdateDTO){
        userUpdateDTO.setDt_update(dt_update);
        userUpdateDTO.setUuid(uuid);

        User userUpdate = userService.update(userUpdateDTO);
        UserDTO userDTO = this.convertor.convert(userUpdate);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping(value ="/registration", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO userRegistrationDTO){
        userService.registration(userRegistrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value ="/verification", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> verification(@RequestParam String code,
                                          @RequestParam String mail){
        boolean isActivate = userService.activate(mail, code);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
