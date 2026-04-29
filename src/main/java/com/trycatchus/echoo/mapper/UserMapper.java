package com.trycatchus.echoo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.model.User;
import com.trycatchus.echoo.dto.payload.user.UserPayload;
import com.trycatchus.echoo.dto.responses.UserResponse;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = 
        ReportingPolicy.IGNORE, 
        imports = {java.time.LocalDate.class, com.trycatchus.echoo.enums.UserRole.class}
)
public interface UserMapper extends BaseMapper<UserPayload, User, UserResponse> { }