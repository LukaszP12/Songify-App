//package com.songify.song.domain.repository;
//
//import com.songify.song.domain.model.Song;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//@Primary
//public class SongRepositoryInMemory implements SongRepository {
//
//    Map<Integer, Song> database = new HashMap<>(Map.of(
//            1, new Song("shawnmendes song1", "Shawn Mendes"),
//            2, new Song("ariana grande song2", "Ariana Grande"),
//            3, new Song("ariana grande song21111232323", "Ariana Grande"),
//            4, new Song("ariana grande song21111232323cbvbcbcv", "Ariana Grande")
//    ));
//
//    @Override
//    public Song save(Song song) {
//        database.put(database.size() + 1, song);
//        return song;
//    }
//
//    @Override
//    public com.songify.song.domain.entities.Song save(com.songify.song.domain.entities.Song song) {
//        return null;
//    }
//
//    @Override
//    public List<Song> findAll() {
//        return database.values()
//                .stream()
//                .toList();
//    }
//}
