package com.trycatchus.echoo.dtos.system;

public record ViaCepResponse(
    String cep,
    String logradouro,
    String complemento,
    String bairro,
    String localidade,
    String uf,
    Boolean erro

) {}