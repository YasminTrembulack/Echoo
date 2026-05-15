package com.trycatchus.echoo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.dtos.payloads.user.UserPayload;
import com.trycatchus.echoo.dtos.responses.UserResponse;
import com.trycatchus.echoo.models.User;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper extends BaseMapper<UserPayload, User, UserResponse> { }