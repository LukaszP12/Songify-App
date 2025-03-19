package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
class ArtistRetriver {

    private final ArtistRepository artistRepository;

    public Set<ArtistDto> findAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                .collect(Collectors.toSet());
    }
}
