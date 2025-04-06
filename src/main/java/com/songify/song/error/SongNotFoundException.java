package com.songify.song.error;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String id) {
        super("song with " + id + " not found");
    }
}
