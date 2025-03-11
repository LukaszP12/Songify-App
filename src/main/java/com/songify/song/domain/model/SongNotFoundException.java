package com.songify.song.domain.model;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String id) {
        super("song with " + id + " not found");
    }
}
