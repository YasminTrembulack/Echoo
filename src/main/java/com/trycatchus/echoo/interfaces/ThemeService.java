package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dto.payload.theme.ThemePayload;
import com.trycatchus.echoo.dto.payload.theme.ThemeUpdatePayload;
import com.trycatchus.echoo.dto.responses.ThemeResponse;

public interface ThemeService extends 
    BaseService<ThemePayload, ThemeUpdatePayload, ThemeResponse, String> 
{ }