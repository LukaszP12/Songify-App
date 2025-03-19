package com.songify.domain.crud;

class ArtistNotFoundException extends RuntimeException {
    public ArtistNotFoundException(Long id) {
        super("artist with id: " + id + " not found");
    }
}
