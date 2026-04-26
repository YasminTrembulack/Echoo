package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.system.PasswordVerification;

public interface PasswordService {
    String applyCriptography(String rawPassword);
    Boolean matchPasswords(String rawPassword, String hashedPassword);
    PasswordVerification verifyPrerequisites(String rawPassword);
}