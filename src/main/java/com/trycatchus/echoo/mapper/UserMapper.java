package com.trycatchus.echoo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.model.User;
import com.trycatchus.echoo.dto.payload.UserPayload;
import com.trycatchus.echoo.dto.responses.UserResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {java.time.LocalDate.class, com.trycatchus.echoo.enums.UserRole.class})
public interface UserMapper extends BaseMapper<UserPayload, User, UserResponse> {

    @Override
    @Mapping(target = "birthDate", expression = "java(payload.birthDate() != null ? java.time.LocalDate.parse(payload.birthDate()) : null)")
    @Mapping(target = "userRole", expression = "java(payload.userRole() != null ? UserRole.fromString(payload.userRole()) : null)")
    User toEntity(UserPayload payload);

    @Override
    @Mapping(target = "birthDate", expression = "java(entity.getBirthDate() != null ? entity.getBirthDate().toString() : null)")
    @Mapping(target = "userRole", expression = "java(entity.getUserRole() != null ? entity.getUserRole().toString() : null)")
    UserResponse toResponse(User entity);
}