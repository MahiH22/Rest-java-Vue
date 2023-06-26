package com.ceuflix.app.domain.cuenta;

public record LoginEntrada(
        String username,
        String hash_contrasenia
) {
}
