package com.songify.song.domain.service;

import com.songify.song.domain.entities.Song;
import com.songify.song.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class SongAdder {

    private final SongRepository songRepository;

    public Song addSong(Song song) {
        log.info("adding new song: " + song);
        // zapytanie do serwisu songs.com/validate?songName=song
        Song savedSong = songRepository.save(song);
        return savedSong;
    }
}
