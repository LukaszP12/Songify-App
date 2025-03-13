package com.songify.song.domain.service;

import com.songify.song.domain.entities.Song;
import com.songify.song.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class SongUpdater {

    private final SongRepository songRepository;

    public void updateById(Long id, Song newSong) {
        songRepository.updateById(id, newSong);
    }
}
