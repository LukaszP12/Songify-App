package com.songify.domain.crud;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
class GenreAssigner {

    private final SongRetriever songRetriever;
    private final GenreRetriever genreRetriever;

    void assignDefaultGenreToSong(Long songId) {
        Song song = songRetriever.findSongById(songId);
        Genre defaultGenre = genreRetriever.findGenreById(1L);
        song.setGenre(defaultGenre);
    }

    public void assignGenreToSong(Long genreId, Long songId) {
        Song songById = songRetriever.findSongById(songId);
        Genre genreById = genreRetriever.findGenreById(genreId);
        songById.setGenre(genreById);
    }
}
