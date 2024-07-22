package songify.song.domain.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import songify.song.domain.model.Song;
import songify.song.domain.repository.SongRepository;

@Service
@Log4j2
public class SongAdder {

    private final SongRepository songRepository;

    public SongAdder(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void addSong(Song song) {
        // 2. Warstwa logiki biznesowej/serwisów domenowych: wyświetlamy informacje
        log.info("adding new song: " + song);
        // 3. Warstwa bazodanowa: zapisujemy do bazy danych
        saveToDatabase(song);
    }

    private void saveToDatabase(Song song) {
        songRepository.saveToDatabase(song);
    }
}
