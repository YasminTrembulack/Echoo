package com.trycatchus.echoo.utils;

import java.text.Normalizer;
import java.util.UUID;

public class SlugUtils {

    public static String generateSlug(String text) {
        if (text == null || text.isBlank()) {
            return "";
        }

        // remove acentos
        String slug = Normalizer.normalize(text, Normalizer.Form.NFD);
        slug = slug.replaceAll("\\p{M}", "");

        // lowercase
        slug = slug.toLowerCase();

        // remove caracteres especiais
        slug = slug.replaceAll("[^a-z0-9\\s-]", "");

        // substitui espaços por hífen
        slug = slug.replaceAll("\\s+", "-");

        // remove hífens duplicados
        slug = slug.replaceAll("-+", "-");

        // remove hífens do começo/fim
        slug = slug.replaceAll("^-|-$", "");

        return slug;
    }

    public static String generateUniqueSlug(String text) {
        String baseSlug = generateSlug(text);

        String shortId = UUID.randomUUID()
            .toString()
            .substring(0, 6);

        return baseSlug + "-" + shortId;
    }
}