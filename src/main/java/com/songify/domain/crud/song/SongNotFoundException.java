package com.songify.domain.crud.song;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String id) {
        super("song with " + id + " not found");
    }
}
