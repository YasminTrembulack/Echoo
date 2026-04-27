package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.payload.UserPayload;
import com.trycatchus.echoo.dto.responses.UserResponse;

public interface UserService  extends BaseService<UserPayload, UserResponse, String> 
{ }