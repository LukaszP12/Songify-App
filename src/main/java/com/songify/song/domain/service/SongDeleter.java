package com.songify.song.domain.service;

import com.songify.song.domain.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class SongDeleter {

    private final SongRepository songRepository;

    public void deleteSongById(Long id) {
        log.info("removing old song with id: " + id);
        songRepository.deleteById(id);
    }
}
