package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
class GenreAdder {

    private final GenreRepository genreRepository;

    GenreDto addGenre(final String name) {
        Genre genre = new Genre(name);
        Genre savedGenre = genreRepository.save(genre);
        return new GenreDto(savedGenre.getId(), savedGenre.getName());
    }
}
