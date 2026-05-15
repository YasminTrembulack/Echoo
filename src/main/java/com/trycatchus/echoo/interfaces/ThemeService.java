package com.trycatchus.echoo.interfaces;

import com.trycatchus.echoo.dtos.payloads.theme.ThemePayload;
import com.trycatchus.echoo.dtos.payloads.theme.ThemeUpdatePayload;
import com.trycatchus.echoo.dtos.responses.ThemeResponse;

public interface ThemeService extends 
    BaseService<ThemePayload, ThemeUpdatePayload, ThemeResponse, String> 
{ }