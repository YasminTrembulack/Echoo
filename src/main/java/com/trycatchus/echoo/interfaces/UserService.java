package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.payload.user.UserPayload;
import com.trycatchus.echoo.dto.payload.user.UserUpdatePayload;
import com.trycatchus.echoo.dto.responses.UserResponse;

public interface UserService extends 
    BaseService<UserPayload, UserUpdatePayload, UserResponse, String> 
{ }