package com.trycatchus.echoo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trycatchus.echoo.dtos.payloads.theme.ThemePayload;
import com.trycatchus.echoo.dtos.payloads.theme.ThemeUpdatePayload;
import com.trycatchus.echoo.dtos.responses.DataResponse;
import com.trycatchus.echoo.dtos.responses.ThemeResponse;
import com.trycatchus.echoo.interfaces.ThemeService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController()
@RequestMapping("/themes")
public class ThemeController {

    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping
    public DataResponse<ThemeResponse> createTheme(@RequestBody @Valid ThemePayload payload) {
        ThemeResponse response = themeService.create(payload);
        return new DataResponse<ThemeResponse>("Theme created successfully", response);
    }

    @PatchMapping("/{id}")
    public DataResponse<ThemeResponse> updateTheme(
        @PathVariable String id,
        @RequestBody @Valid ThemeUpdatePayload payload
    ) {
        ThemeResponse response = themeService.update(id, payload);
        return new DataResponse<ThemeResponse>("Theme updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable String id) {
        themeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public DataResponse<ThemeResponse> getTheme(@PathVariable String id) {
        ThemeResponse response = themeService.findById(id);
        return new DataResponse<ThemeResponse>("Theme retrieved successfully", response);
    }
    
    @GetMapping
    public DataResponse<List<ThemeResponse>> getAllThemes() {
        List<ThemeResponse> response = themeService.findAll();
        return new DataResponse<List<ThemeResponse>>("Themes retrieved successfully", response);
    }

}