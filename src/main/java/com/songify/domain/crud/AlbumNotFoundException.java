package com.songify.domain.crud;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(String id) {
        super("album with " + id + " not found");
    }
}
