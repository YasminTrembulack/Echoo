package com.trycatchus.echoo.dto;

import org.springframework.http.HttpStatusCode;

public record HttpEntity<DT> (
    HttpStatusCode statusCode,
    DT data
) { }