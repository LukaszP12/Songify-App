package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class GenreRetriever {

    private final GenreRepository genreRepository;

    Genre findGenreById(final Long genreId) {
        return genreRepository
                .findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));
    }

    Set<GenreDto> findAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                .collect(Collectors.toSet());
    }
}
