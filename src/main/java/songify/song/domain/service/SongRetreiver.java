package songify.song.domain.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import songify.song.domain.model.Song;
import songify.song.domain.repository.SongRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class SongRetreiver {

    private final SongRepository songRepository;

    public SongRetreiver(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Map<Integer, Song> findAll() {
        log.info("retrieving all songs: ");
        return songRepository.findAll();
    }

    public Map<Integer, Song> findAllLimitedBy(Integer limit) {
        return songRepository.findAll().entrySet()
                .stream()
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
