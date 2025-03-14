package com.songify.song.domain.service;

import com.songify.song.domain.entities.Song;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SongUpdater {

    private final SongRetriever songRetriever;

    public void updateById(Long id, Song newSong) {
        Song songById = songRetriever.findSongById(id);
        songById.setName(newSong.getName());
        songById.setArtist(newSong.getArtist());
    }

    public Song updatePartiallyById(Long id, Song songFromRequest) {
        Song songFromDatabase = songRetriever.findSongById(id);
        if (songFromRequest.getName() != null) {
            songFromDatabase.setName(songFromRequest.getName());
            log.info("partially updated song name");
        }
        if (songFromRequest.getArtist() != null) {
            songFromDatabase.setArtist(songFromRequest.getArtist());
            log.info("partially updated artist name");
        }
        log.info("Partially updated song with id: " + id +
                " with oldSongName: " + songFromDatabase.getName() + " to newSongName: " + songFromRequest.getName() +
                " oldArtist: " + songFromDatabase.getArtist() + " to newArtist: " + songFromRequest.getArtist());
        return songFromDatabase;
    }
}
