package com.songify.infrastructure;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.UUID;

@RequestScope
@Component
@Log4j2
public class ArtistSaver {

    List<String> artists;
    String saverName = UUID.randomUUID().toString();

    public ArtistSaver(List<String> artists) {
        this.artists = artists;
    }

    void addArtist(String artist) {
        artists.add(artist);
    }

    void printArtistsSize() {
        log.info("actual size is: " + artists.size());
    }

    void printSaverName() {
        log.info("actual saver is: " + saverName);
    }

    public void printArtists() {
        artists.forEach(log::info);
    }

}
