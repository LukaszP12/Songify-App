package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;

    ArtistDto addArtist(final String name) {
        Artist artist = new Artist(name);
        ArtistDto save = artistRepository.save(artist);
        return new ArtistDto(save.id(), save.name());
    }
}
