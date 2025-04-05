package com.songify.domain.crud;

public class GenreNotFoundException extends RuntimeException {

    GenreNotFoundException(final Long id) {
        super("album with id: " + id + " not found");
    }
}
