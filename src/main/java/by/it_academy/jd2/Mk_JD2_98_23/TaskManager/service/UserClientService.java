package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.UserDetailsImpl;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.service.api.IUserClientService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UserClientService implements IUserClientService {
    @Override
    public UserDTO get(String token, UUID uuid) {
        return null;
    }

    @Override
    public UserDTO get(UserDetailsImpl userDetails, UUID uuid) {
        return null;
    }

    @Override
    public List<UserDTO> get(UserDetailsImpl userDetails, Set<UUID> uuid) {
        return null;
    }

    @Override
    public List<UserDTO> get(String token, Set<UUID> users) {
        return null;
    }
}
