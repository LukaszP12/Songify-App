package com.songify.domain.crud;

class GenreWasNotDeletedException extends RuntimeException {
    public GenreWasNotDeletedException(Long id) {
        super("genre with id: " + id + " not found");
    }
}
