package com.trycatchus.echoo.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.trycatchus.echoo.dtos.payloads.theme.ThemePayload;
import com.trycatchus.echoo.dtos.responses.ThemeResponse;
import com.trycatchus.echoo.models.Theme;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ThemeMapper extends BaseMapper<ThemePayload, Theme, ThemeResponse> { }