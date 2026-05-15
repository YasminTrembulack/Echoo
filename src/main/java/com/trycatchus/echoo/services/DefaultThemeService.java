package com.trycatchus.echoo.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trycatchus.echoo.dtos.payloads.theme.ThemePayload;
import com.trycatchus.echoo.dtos.payloads.theme.ThemeUpdatePayload;
import com.trycatchus.echoo.dtos.responses.ThemeResponse;
import com.trycatchus.echoo.exceptions.EntityNotFoundException;
import com.trycatchus.echoo.exceptions.UniqueFieldAlreadyInUseException;
import com.trycatchus.echoo.interfaces.ThemeService;
import com.trycatchus.echoo.mappers.ThemeMapper;
import com.trycatchus.echoo.models.Theme;
import com.trycatchus.echoo.repository.ThemeRepository;
import com.trycatchus.echoo.utils.UpdateUtils;

@Service
public class DefaultThemeService implements ThemeService{

    private final ThemeRepository themeRepo;
    private final ThemeMapper themeMapper;

    public DefaultThemeService(ThemeRepository themeRepo, ThemeMapper themeMapper) {
        this.themeRepo = themeRepo;
        this.themeMapper = themeMapper;
    }

    private void validateUniqueFields(String name, UUID themeIdToExclude) {
        Boolean exists = themeRepo.existsConflictingTheme(name, themeIdToExclude);

        if (exists)
            throw new UniqueFieldAlreadyInUseException(Theme.class, List.of("name"));
    }   

    @Override
    public ThemeResponse create(ThemePayload payload) {
        validateUniqueFields(payload.name(), null);

        Theme theme = themeMapper.toEntity(payload);

        Theme savedTheme = themeRepo.save(theme);

        return themeMapper.toResponse(savedTheme);
    }

    @Override
    public ThemeResponse update(String id, ThemeUpdatePayload payload) {
        Theme theme = themeRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Theme.class));
        
        theme.setName(UpdateUtils.valueOrKeep(payload.name(), theme.getName()));

        validateUniqueFields(theme.getName(), UUID.fromString(id));

        theme.setDescription(UpdateUtils.valueOrKeep(payload.descriprion(), theme.getDescription()));

        Theme updatedTheme = themeRepo.save(theme);

        return themeMapper.toResponse(updatedTheme);
    }

    @Override
    public void delete(String id) {
        Theme theme = themeRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Theme.class));
        
        themeRepo.delete(theme);
    }

    @Override
    public ThemeResponse findById(String id) {
        Theme theme = themeRepo.findById(UUID.fromString(id))
            .orElseThrow(() -> new EntityNotFoundException(Theme.class));
        
        return themeMapper.toResponse(theme);
    }

    @Override
    public List<ThemeResponse> findAll() {
        List<Theme> themes = themeRepo.findAll();

        return themes.stream()
            .map(themeMapper::toResponse)
            .collect(Collectors.toList());
    }
    
}
