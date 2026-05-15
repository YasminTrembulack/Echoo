package com.trycatchus.echoo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.model.Theme;
import com.trycatchus.echoo.dto.payload.theme.ThemePayload;
import com.trycatchus.echoo.dto.responses.ThemeResponse;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ThemeMapper extends BaseMapper<ThemePayload, Theme, ThemeResponse> { }