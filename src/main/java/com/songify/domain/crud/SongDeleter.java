package com.songify.domain.crud;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
class SongDeleter {

    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    private final SongUpdater songUpdater;
    private final GenreDeleter genreDeleter;

    public void deleteSongById(Long id) {
        log.info("removing old song with id: " + id);
        songRepository.deleteById(id);
    }

    void deleteAllSongsById(final Set<Long> songsIds) {
        songRepository.deleteByIdIn(songsIds);
    }

    public void deleteSongAndGenreById(final Long songId) {
        Song songById = songRetriever.findSongById(songId);
        Long genreId = songById.getGenre().getId();

        deleteSongById(songById.getId());

        genreDeleter.deleteGenreById(genreId);
    }
}
