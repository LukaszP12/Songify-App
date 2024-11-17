package com.songify.domain.crud;

import com.songify.domain.crud.song.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
class ArtistUpdater {

    private final ArtistRetriever artistRetriever;
    private final ArtistRepository artistRepository;

    ArtistDto updateArtistNameById(final Long artistId, final String name) {
        Artist artist = artistRetriever.findById(artistId);
        artist.setName(name);
        return new ArtistDto(artist.getId(), artist.getName());
    }
}
