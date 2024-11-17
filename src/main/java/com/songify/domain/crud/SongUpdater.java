package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongUpdater {

    private final SongRepository songRepository;

    void updateById(Long id, Song newSong) {
        songRepository.updateById(id, newSong);
    }

    void someComplicatedLogic() {
////        try {
//        songRepository.updateById(1L, new Song("siema", "siema"));
//        songRepository.updateById(2L, new Song("siema", "siema"));
////        if (true) {
////            throw new RuntimeException();
////        }
//        songRepository.updateById(3L, new Song("siema", "siema"));
//        songRepository.updateById(piesSong.getId(), new Song("pies2", "pies2"));
////        } catch (RuntimeException e) {
////            System.out.println("pies");
////        }
    }

//    void transfer() {
//        // start point
//        songRepository.updateById(1L, new Song("2000", "bartek"));
//        songRepository.updateById(2L, new Song("2000", "ania"));
//
//        // from Bartek to Asia
//        songRepository.updateById(1L, new Song("2000", "bartek"));
//        songRepository.updateById(1L, new Song("1500", "bartek"));
//        // to
//        songRepository.updateById(2L, new Song("2000", "ania"));
//        songRepository.updateById(2L, new Song("2500", "ania"));
//    }

//    Song updatePartiallyById(Long id, Song songFromRequest) {
//        Song songFromDatabase = songRetriever.findSongById(id);
//        Song.SongBuilder builder = Song.builder();
//        if (songFromRequest.getName() != null) {
//            builder.name(songFromRequest.getName());
//        } else {
//            builder.name(songFromDatabase.getName());
//        }
//        if (songFromRequest.getArtist() != null) {
//            builder.artist(songFromRequest.getArtist());
//        } else {
//            builder.artist(songFromDatabase.getArtist());
//        }
//        Song toSave = builder.build();
//        updateById(id, toSave);
//        return toSave;
//    }

// Dirty checking version
//     void updateById(Long id, Song newSong) {
//        Song songById = songRetriever.findSongById(id);
//        songById.setName(newSong.getName());
//        songById.setArtist(newSong.getArtist());
//    }
//
//     Song updatePartiallyById(Long id, Song songFromRequest) {
//        Song songFromDatabase = songRetriever.findSongById(id);
//        if (songFromRequest.getName() != null) {
//            songFromDatabase.setName(songFromRequest.getName());
//        }
//        if (songFromRequest.getArtist() != null) {
//            songFromDatabase.setArtist(songFromRequest.getArtist());
//        }
//        return songFromDatabase;
//    }
}
