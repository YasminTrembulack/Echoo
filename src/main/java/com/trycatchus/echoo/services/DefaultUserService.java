package com.trycatchus.echoo.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trycatchus.echoo.dtos.payloads.user.UserPayload;
import com.trycatchus.echoo.dtos.payloads.user.UserUpdatePayload;
import com.trycatchus.echoo.dtos.responses.UserResponse;
import com.trycatchus.echoo.dtos.responses.auth.UserDecodedResponse;
import com.trycatchus.echoo.dtos.system.PasswordVerification;
import com.trycatchus.echoo.enums.UserRole;
import com.trycatchus.echoo.exceptions.EntityNotFoundException;
import com.trycatchus.echoo.exceptions.PasswordValidationException;
import com.trycatchus.echoo.exceptions.UnauthorizedException;
import com.trycatchus.echoo.exceptions.UniqueFieldAlreadyInUseException;
import com.trycatchus.echoo.interfaces.PasswordService;
import com.trycatchus.echoo.interfaces.UserService;
import com.trycatchus.echoo.mappers.UserMapper;
import com.trycatchus.echoo.models.User;
import com.trycatchus.echoo.repository.UserRepository;
import com.trycatchus.echoo.utils.SecurityUtils;
import com.trycatchus.echoo.utils.UpdateUtils;

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

    private void validateUserOwnership(User user) {
        UserDecodedResponse currentUser = SecurityUtils.getCurrentUser();

        UserRole userRole = UserRole.fromString(currentUser.role());

        if (!user.getId().toString().equals(currentUser.userId()) && userRole != UserRole.ADMIN)
            throw new UnauthorizedException("You are not authorized to perform this action");
    }

    @Override
    public UserResponse create(UserPayload request) {
        validateUniqueFields(request.email(), request.cpf(), request.username(), null);

        PasswordVerification passwordValid = passwordService.verifyPrerequisites(request.password());

        if (!passwordValid.isValid())
            throw new PasswordValidationException(400, passwordValid.toString());

        User user = userMapper.toEntity(request);

        user.setPasswordHash(passwordService.applyCriptography(request.password()));

        User savedUser = userRepo.saveAndFlush(user);

        return userMapper.toResponse(savedUser);
    }

    private void validateUniqueFields(String email, String cpf, String username, String excludeId) {
        List<User> conflicts = userRepo.findConflictingUsers(email, cpf, username);

        List<String> errors = new java.util.ArrayList<>();

        for (User u : conflicts) {
            if (excludeId != null && u.getId().equals(UUID.fromString(excludeId))) continue; // Skip the user being updated
            if (u.getEmail().equals(email)) errors.add("email");
            if (u.getCpf().equals(cpf)) errors.add("cpf");
            if (u.getUsername().equals(username)) errors.add("username");
        }

        if (!errors.isEmpty())
            throw new UniqueFieldAlreadyInUseException(User.class, errors);
    }

    @Override
    public UserResponse update(String id, UserUpdatePayload request) {
        User user = userRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(User.class));

        validateUserOwnership(user);
    
        validateUniqueFields(request.email(), request.cpf(), request.username(), id);

        user.setFirstName(UpdateUtils.valueOrKeep(request.firstName(), user.getFirstName()));
        user.setLastName(UpdateUtils.valueOrKeep(request.lastName(), user.getLastName()));
        user.setUsername(UpdateUtils.valueOrKeep(request.username(), user.getUsername()));
        user.setEmail(UpdateUtils.valueOrKeep(request.email(), user.getEmail()));
        user.setCpf(UpdateUtils.valueOrKeep(request.cpf(), user.getCpf()));

        if (request.password() != null && !request.password().isBlank())
            user.setPasswordHash(passwordService.applyCriptography(request.password()));

        User updatedUser = userRepo.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void delete(String id) {
        User user = userRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(User.class));
        
        validateUserOwnership(user);

        userRepo.delete(user);
    }

    @Override
    public UserResponse findById(String id) {
        User user = userRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(User.class));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepo.findAll();

        return users.stream()
            .map(userMapper::toResponse)
            .collect(Collectors.toList());
    }

}
