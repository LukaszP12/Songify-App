package com.songify.domain.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
class GenreDeleter {

    private final GenreRepository genreRepository;

    void deleteGenreById(final Long genreId) {
        int i = genreRepository.deleteById(genreId);
        log.info("removing genre with id: " + genreId);
        if (i != 1) {
            throw new GenreWasNotDeletedException(genreId);
        }
    }
}
