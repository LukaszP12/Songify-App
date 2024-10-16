package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
class ArtistRetriever {

    private final ArtistRepository artistRepository;

    public Set<ArtistDto> findAllArtists() {
        return artistRepository.findAll()
                .stream()
                .map(artist -> new ArtistDto(
                        artist.getId(),
                        artist.getName()
                ))
                .collect(Collectors.toSet());
    }

    public Set<Artist> findAllArtists(Pageable pageable) {
        return artistRepository.findAll(pageable);
    }
}
