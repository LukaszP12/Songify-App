package com.songify.domain.crud.song;

import com.songify.domain.crud.song.dto.SongDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
class SongUpdater {

    private final SongRetriever songRetriever;
    private final SongRepository songRepository;
    private final SongAdder songAdder;

    public void updateById(Long id, Song newSong) {
        songRepository.updateById(id,newSong);
    }

    public void someComplicatedLogic() {
        songRepository.updateById(1L, new Song("siema", "siema"));
        songRepository.updateById(2L, new Song("siema", "siema"));
        songRetriever.existsById(1000L);
//        if (true) {
//            throw new RuntimeException();
//        }
        songRepository.updateById(3L, new Song("siema", "siema"));
        Song piesSong = songAdder.addSong(new Song("pies", "pies"));
        songRepository.updateById(piesSong.getId(), new Song("pies2", "pies2"));
    }

    public void transfer(){
        // start point
        songRepository.updateById(1L,new Song("2000","bartek"));
        songRepository.updateById(1L,new Song("1500","bartek"));

        // from bartek to ania
        songRepository.updateById(2L,new Song("2000","ania"));
        // throw new Exception
        // to
        songRepository.updateById(2L,new Song("2500","ania"));
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
