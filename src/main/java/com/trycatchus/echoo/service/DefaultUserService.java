package com.trycatchus.echoo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trycatchus.echoo.dto.payload.UserPayload;
import com.trycatchus.echoo.dto.responses.UserResponse;
import com.trycatchus.echoo.dto.system.PasswordVerification;
import com.trycatchus.echoo.exception.PasswordValidationException;
import com.trycatchus.echoo.exception.UniqueFieldAlreadyInUseException;
import com.trycatchus.echoo.interfaces.PasswordService;
import com.trycatchus.echoo.interfaces.UserService;
import com.trycatchus.echoo.mapper.UserMapper;
import com.trycatchus.echoo.model.User;
import com.trycatchus.echoo.repository.UserRepository;

@Service
public class DefaultUserService implements UserService {
    
    private final UserRepository userRepo;
    private final PasswordService passwordService;
    private final UserMapper userMapper;

    public DefaultUserService(UserRepository userRepo, PasswordService passwordService, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.passwordService = passwordService;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse create(UserPayload request) {
        validateUniqueFields(request);

        PasswordVerification passwordValid = passwordService.verifyPrerequisites(request.password());

        if (!passwordValid.isValid())
            throw new PasswordValidationException(400, passwordValid.toString());

        User user = userMapper.toEntity(request);

        user.setPasswordHash(passwordService.applyCriptography(request.password()));

        User newUser = userRepo.saveAndFlush(user);

        return userMapper.toResponse(newUser);
    }

    private void validateUniqueFields(UserPayload request) {
        List<User> conflicts = userRepo.findConflictingUsers(
            request.email(),
            request.cpf(),
            request.username()
        );

        List<String> errors = new java.util.ArrayList<>();

        for (User u : conflicts) {
            if (u.getEmail().equals(request.email())) errors.add("email");
            if (u.getCpf().equals(request.cpf())) errors.add("cpf");
            if (u.getUsername().equals(request.username())) errors.add("username");
        }

        if (!errors.isEmpty()) {
            throw new UniqueFieldAlreadyInUseException("User", errors);
        }
    }

    @Override
    public UserResponse update(String id, UserPayload request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public UserResponse findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<UserResponse> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
