package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Transactional
public class SongifyCrudFacade {

    private final SongRetriever songRetriever;
    private final SongUpdater songUpdater;
    private final SongDeleter songDeleter;
    private final SongAssigner songAssigner;
    private final SongAdder songAdder;
    private final ArtistAdder artistAdder;
    private final GenreRetriever genreRetriever;
    private final GenreAdder genreAdder;
    private final AlbumAdder albumAdder;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final ArtistDeleter artistDeleter;
    private final ArtistAssigner artistAssigner;
    private final ArtistUpdater artistUpdater;
    private final GenreAssigner genreAssigner;

    public ArtistDto addArtist(ArtistRequestDto dto) {
        return artistAdder.addArtist(dto.name());
    }


    public AlbumDto addAlbumWithSongs(AlbumRequestDto albumRequestDto) {
        return albumAdder.addAlbum(albumRequestDto.songsIds(),
                albumRequestDto.title(),
                albumRequestDto.releaseDate());
    }

    public void addArtistToAlbum(Long artistId, Long albumId) {
        artistAssigner.addArtistToAlbum(artistId, albumId);
    }

    public ArtistDto updateArtistNameById(Long artistId, String name) {
        return artistUpdater.updateArtistNameById(artistId, name);
    }

    public ArtistDto addArtistWithDefaultAlbumAndSong(ArtistRequestDto dto) {
        return artistAdder.addArtistWithDefaultAlbumAndSong(dto);
    }

    public SongDto addSong(final SongRequestDto dto) {
        return songAdder.addSong(dto);
    }

    public Set<ArtistDto> findAllArtists(Pageable pageable) {
        return artistRetriever.findAllArtists(pageable);
    }

    public AlbumInfo findAlbumByIdWithArtistsAndSongs(Long id) {
        return albumRetriever.findAlbumByIdWithArtistsAndSongs(id);
    }

    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
        artistDeleter.deleteArtistByIdWithAlbumsAndSongs(artistId);
    }

    Set<Album> findAlbumsByArtistId(Long artistId) {
        return albumRetriever.findAlbumsByArtistId(artistId);
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(Long artistId) {
        return albumRetriever.findAlbumsDtoByArtistId(artistId);
    }

    public List<SongDto> findAllSongs(Pageable pageable) {
        return songRetriever.findAll(pageable);
    }

    public void deleteSongById(Long id) {
        songRetriever.existsById(id);
        songDeleter.deleteSongById(id);
    }

//    public void deleteSongAndGenreById(final Long songId) {
//        Song songById = songRetriever.findSongById(songId);
//        Long genreId = songById.getGenre().getId();
//
//        deleteSongById(songId);
//        genreDeleter.deleteGenreById(genreId);
//    }

    public void updateSongById(Long id, SongDto newSongDto) {
        songRetriever.existsById(id);
        Song songValidatedAndReadyToUpdate = new Song(newSongDto.name());
        songUpdater.updateById(id, songValidatedAndReadyToUpdate);
    }

    public SongDto updateSongPartiallyById(Long id, Song songFromRequest) {
        songRetriever.existsById(id);
        Song songFromDatabase = songRetriever.findSongById(id);
        Song toSave = new Song();
        if (songFromRequest.getName() != null) {
            toSave.setName(songFromRequest.getName());
        } else {
            toSave.setName(songFromDatabase.getName());
        }

        songUpdater.updateById(id, toSave);
        return SongDto.builder()
                .id(toSave.getId())
                .name(toSave.getName())
                .build();
    }

    public SongDto findSongDtoById(Long id) {
        return songRetriever.findSongDtoById(id);
    }

    long countArtistsByAlbumId(final Long albumId) {
        return albumRetriever.countArtistsByAlbumId(albumId);
    }

    AlbumDto findAlbumById(final Long albumId) {
        return albumRetriever.findDtoById(albumId);
    }

    public Set<AlbumDto> findAllAlbums() {
        return albumRetriever.findAll();
    }

    public GenreDto addGenre(GenreRequestDto dto) {
        return genreAdder.addGenre(dto.name());
    }

    public GenreDto findGenre(Long genreId) {
        Genre genreById = genreRetriever.findGenreById(genreId);
        return new GenreDto(genreById.getId(), genreById.getName());
    }

    public Set<GenreDto> retrieveGenres() {
        return genreRetriever.findAllGenres();
    }

    public void assignGenreToSong(Long genreId, Long songId) {
        genreAssigner.assignGenreToSong(genreId, songId);
    }

    public AlbumDto addSongToAlbum(Long albumId, Long songId) {
        return songAssigner.assignSongToAlbum(albumId, songId);
    }
}
