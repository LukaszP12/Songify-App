package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class GenreDeleter {

    private final GenreRepository genreRepository;

    void deleteById(final Long genreId) {
        int i = genreRepository.deleteById(genreId);
        if (i != 1) {
            throw new GenreWasNotDeletedException("genre id: " + genreId + " was not deleted");
        }
    }
}
