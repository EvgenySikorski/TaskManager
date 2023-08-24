package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDetailsImpl;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IUserClientService {

    UserDTO get(String token, UUID uuid);

    UserDTO get(UserDetailsImpl userDetails, UUID uuid);

    List<UserDTO> get(UserDetailsImpl userDetails, Set<UUID> uuid);

    List<UserDTO> get(String token, Set<UUID> users);
}

