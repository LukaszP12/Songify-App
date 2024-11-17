package com.songify.domain.crud;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
class ArtistDeleter {

    private final ArtistRepository artistRepository;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final AlbumDeleter albumDeleter;
    private final SongDeleter songDeleter;


    public ArtistDeleter(ArtistRepository artistRepository,
                         ArtistRetriever artistRetriever,
                         AlbumRetriever albumRetriever,
                         AlbumDeleter albumDeleter,
                         SongDeleter songDeleter) {
        this.artistRepository = artistRepository;
        this.artistRetriever = artistRetriever;
        this.albumRetriever = albumRetriever;
        this.albumDeleter = albumDeleter;
        this.songDeleter = songDeleter;
    }

    void deleteArtistByIdWithAlbumsAndSongs(final Long artistId) {
        Artist artist = artistRetriever.findById(artistId);
        Set<Album> artistAlbums = albumRetriever.findAlbumsByArtistId(artist.getId());
        if (artistAlbums.isEmpty()) {
            log.info("Artist with id: " + artistId + " have 0 albums ");
            artistRepository.deleteById(artistId);
            return;
        }

        Set<Album> albumsWithOnlyOneArtist = artistAlbums.stream()
                .filter(album -> album.getArtists().size() == 1)
                .collect(Collectors.toSet());

        Set<Long> allSongsIdsFromAllAlbumsWhereWasOnlyThisArtist = albumsWithOnlyOneArtist
                .stream()
                .flatMap(album -> album.getSongs().stream())
                .map(Song::getId)
                .collect(Collectors.toSet());

        songDeleter.deleteAllSongsByIdIn(allSongsIdsFromAllAlbumsWhereWasOnlyThisArtist);

        Set<Long> albumsIdsToDelete = albumsWithOnlyOneArtist.stream()
                .map(Album::getId)
                .collect(Collectors.toSet());

        albumDeleter.deleteAllAlbumsByIds(albumsIdsToDelete);

        artistAlbums.stream()
                .filter(album -> album.getArtists().size() >= 2)
                .forEach(album -> album.removeArtist(artist));

        artistRepository.deleteById(artistId);
    }

}
