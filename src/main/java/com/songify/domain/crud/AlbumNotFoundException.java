package com.songify.domain.crud;

public class AlbumNotFoundException extends RuntimeException{

    AlbumNotFoundException(final Long id) {
        super("album with id: " + id + " not found");
    }
}
