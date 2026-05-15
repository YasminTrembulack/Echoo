package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dtos.payloads.user.UserPayload;
import com.trycatchus.echoo.dtos.payloads.user.UserUpdatePayload;
import com.trycatchus.echoo.dtos.responses.UserResponse;

public interface UserService extends 
    BaseService<UserPayload, UserUpdatePayload, UserResponse, String> 
{ }