package com.songify.domain.crud.song;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
class SongDeleter {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public void deleteSongById(Long id) {
        songRetriever.findSongById(id);
        log.info("removing old song with id: " + id);
        songRepository.deleteById(id);
    }
}
