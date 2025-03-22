package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Set;

import static com.songify.domain.crud.SongLanguage.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository(),
            new AlbumRetriever(new InMemoryAlbumRepository())
    );

    public static SongifyCrudFacade createSongifyCrud(
            final SongRepository songRepository,
            final GenreRepository genreRepository,
            final ArtistRepository artistRepository,
            final AlbumRepository albumRepository,
            final AlbumRetriever albumRetriever) {
        SongRetriever songRetriever = new SongRetriever(songRepository);
        SongUpdater songUpdater = new SongUpdater(songRepository);
        AlbumAdder albumAdder = new AlbumAdder(songRetriever, albumRepository);
        ArtistRetriever artistRetriever = new ArtistRetriever(artistRepository);
        GenreDeleter genreDeleter = new GenreDeleter(genreRepository);
        SongDeleter songDeleter = new SongDeleter(songRepository, songRetriever, genreDeleter);
        SongAdder songAdder = new SongAdder(songRepository);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository, albumAdder);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository);
        ArtistDeleter artistDeleter = new ArtistDeleter(artistRepository, artistRetriever, albumRetriever, albumDeleter, songDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistRetriever, albumRetriever);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistRetriever, artistRepository);

        return new SongifyCrudFacade(
                songRetriever,
                songUpdater,
                songDeleter,
                songAdder,
                artistAdder,
                artistRetriever,
                genreAdder,
                albumAdder,
                albumRetriever,
                artistDeleter,
                artistAssigner,
                artistUpdater,
                albumRepository
        );
    }

    @Test
    @DisplayName("should add artist 'amigo' with id:0 when amigo was sent")
    public void should_add_artist_amigo_with_id_zero_when_amigo_was_sent() {
        // given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        // when
        ArtistDto response = songifyCrudFacade.addArtist(artist);

        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("amigo");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("should add artist shawn mendes with id zero when shawn mendes was sent")
    public void should_add_artist_shawn_mendes_with_id_zero_when_shawn_mendes_was_sent() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        // when
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("shawn mendes");
    }

    @Test
    @DisplayName("should throw exception ArtistNotFound when id: 0")
    public void should_throw_exception_artist_not_found_when_id_was_zero() {
        // given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));
        // then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("artist with id: 0 not found");
    }

    @Test
    @DisplayName("Should not throw exception")
    public void should_not_throw_exception() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(shawnMendes);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistDto.id());
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("should delete artist by id when he has no albums")
    public void should_delete_artist_by_id_when_he_has_no_albums() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(shawnMendes);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        Long artistId = artistDto.id();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId)).isEmpty();
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("should delete artist by id when he has one album and he was the only artist in album")
    public void should_delete_artist_with_album_and_songs_by_id_when_artist_had_one_album_and_he_was_the_only_artist_in_album() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songId(songId)
                .title("album title 1")
                .build());
        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id 0 not found");
        Throwable throwable2 = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumId));
        assertThat(throwable2).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable2.getMessage()).isEqualTo("Album with id: 0 not found");
    }

    @Test
    @DisplayName("should delete only artist from album by id When there were more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_id_When_there_were_more_than_one_artist_in_album() {

    }

    @Test
    @DisplayName("should delete only artist from album by id When there were more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_id_When_there_were_more_than_one_artist_in_albums() {

    }

    @Test
    public void should_add_album_with_song() {
        // toDo

    }

    @Test
    public void should_add_artist_to_album() {
        // toDo

    }

    @Test
    public void should_add_song() {
        // toDo
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(ENGLISH)
                .build();
        // when
        SongDto songDto = songifyCrudFacade.addSong(song);
        // then
        Long songId = songDto.id();
        assertThat(songifyCrudFacade.findSongDtoById(songId));
    }

    @Test
    public void should_return_album_by_id(){

    }

}
