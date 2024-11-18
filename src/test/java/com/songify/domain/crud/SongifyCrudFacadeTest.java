package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SongifyCrudFacadeTest {

    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );

    @Test
    @DisplayName("should add artist 'amigo' with id:0 when amigo was sent")
    public void should_add_artist_amigo_with_id_zero_when_amigo_was_sent() {
        // given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        Assert.assertTrue(allArtists.isEmpty());

        // when
        ArtistDto result = songifyCrudFacade.addArtist(artist);
        // then
        assert result.id().equals(0L);
        assert result.name().equals("amigo");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        Assert.assertEquals(1, size);
    }

    @Test
    @DisplayName("should add artist 'shawn mendes' with id:0 when amigo was sent")
    public void should_add_artist_shawn_mendes_with_id_zero_when_shawn_mendes_was_sent() {
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);

        assert response.id().equals(0L);
        assert response.name().equals("shawn mendes");
    }

    @Test
    public void should_add_return_correct_dto() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        // when
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);
        // then
        assert response.name().equals("amigo");
        assertNotNull(response.id());
    }

    @Test
    public void second() {
        // given

        // when

        // then

    }

    @Test
    public void third() {
        // given

        // when

        // then

    }

    @Test
    @Ignore
    public void testFindAlbumById() {
        // Setup
        final AlbumDto expectedResult = new AlbumDto(0L, "name", Set.of(0L));

        // Run the test
        songifyCrudFacade.addAlbumWithSong(new AlbumRequestDto("namme", Instant.now(), Set.of(0L)));
        final AlbumDto result = songifyCrudFacade.findAlbumById(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Should throw exception ArtistNotFound when id:0")
    public void should_throw_exception_artist_not_found_when_id_was_zero() {
        // given
        assertTrue(songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));
        // then
        assertThat(throwable).isInstanceOf(ArtistNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("artist with id: 0 not found");
    }

    @Test
    @DisplayName("should not throw exception")
    public void should_not_throw_exception() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(shawnMendes);
        assertThat(!songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistDto.id());
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
    }

    @Test
    @DisplayName("should delete artist by id when he have no albums")
    public void should_delete_artist_by_id_when_he_have_no_albums() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).isEmpty());
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
    }

    @Test
    @DisplayName("Should delete artist by id when he has one album")
    public void should_delete_artist_by_id_when_he_has_one_album() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId))
                .title("album title 1")
                .build());
        songifyCrudFacade.addArtistToAlbum(artistId, albumDto.id());
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).size()).isEqualTo(1);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
    }

    @Test
    @DisplayName("should delete artist with album and songs by id when artist had one album and he was the only artist in album")
    public void should_delete_artist_with_album_and_songs_by_id_when_artist_had_one_album_and_he_was_the_only_artist_in_album() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId))
                .title("album title 1")
                .build());
        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songId));
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id 0 not found");

        Throwable throwable2 = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumId));
        assertThat(throwable2).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable2.getMessage()).isEqualTo("Album with id: 0 not found");
    }

    @Test
    @DisplayName("should delete only artist from album by id When there were more than 1 artist in album")
    public void should_delete_only_artist_from_album_by_id_When_there_were_more_than_1_artist() {
        assertThat(songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(0L)
                .getArtists()
                .size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void should_add_album_with_song() {
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto.id()))
                .title("album title 1")
                .build();
        assertThat(songifyCrudFacade.findAllAlbums().isEmpty());
        // when
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        // then
        assertThat(!songifyCrudFacade.findAllAlbums().isEmpty());
        AlbumInfo albumByIdWithArtistsAndSongs = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumDto.id());
        Set<AlbumInfo.SongInfo> songs = albumByIdWithArtistsAndSongs.getSongs();
        assertTrue(songs.stream().anyMatch(song1 -> song1.getId().equals(songDto.id())));
    }

//    @Test
//    @DisplayName("should add song")
//    public void should_add_song() {
//        // given
//        SongRequestDto song = SongRequestDto.builder()
//                .name("song1")
//                .language(SongLanguageDto.ENGLISH)
//                .build();
//        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).isEmpty());
//        // when
//        songifyCrudFacade.addSong(song);
//        // then
//        List<SongDto> allSongs = songifyCrudFacade.findAllSongs(Pageable.unpaged());
//        assertThat(allSongs)
//                .extracting(SongDto::id)
//                .containsExactly(0L);
//    }

    @Test
    @DisplayName("should add artist to album")
    public void should_add_artist_to_album() {
        ArtistDto lionelRitchie = songifyCrudFacade.addArtist(new ArtistRequestDto("Lionel Ritchie"));
        SongDto sayYouSayMe = songifyCrudFacade.addSong(new SongRequestDto(
                "Say you say me",
                Instant.now(),
                100L,
                SongLanguageDto.ENGLISH
        ));

        AlbumDto biggestHits = songifyCrudFacade.addAlbumWithSong(new AlbumRequestDto(
                "biggest hits",
                Instant.now(),
                Set.of(sayYouSayMe.id())
        ));

        songifyCrudFacade.addArtistToAlbum(
                lionelRitchie.id(),
                biggestHits.id()
        );

        assertThat(songifyCrudFacade.findAlbumById(biggestHits.id()).songsIds().equals(sayYouSayMe.id()));
    }

//    @Test
//    @DisplayName("should add artist to album 2")
//    public void should_add_artist_to_album_2() {
//        // given
//        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
//                .name("shawn mendes")
//                .build();
//        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
//        SongRequestDto song = SongRequestDto.builder()
//                .name("song1")
//                .language(SongLanguageDto.ENGLISH)
//                .build();
//        SongDto songDto = songifyCrudFacade.addSong(song);
//        Long songId = songDto.id();
//        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
//                .builder()
//                .songIds(Set.of(songId))
//                .title("album title 1")
//                .build());
//        Long albumId = albumDto.id();
//        assertThat(songifyCrudFacade.findAlbumsByArtistId(artistId).isEmpty());
//        // when
//        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
//        // then
//        Set<AlbumDto> albumsByArtistId = songifyCrudFacade.findAlbumsByArtistId(artistId);
//        assertThat(!albumsByArtistId.isEmpty());
//        assertThat(albumsByArtistId)
//                .extracting(AlbumDto::id)
//                .containsExactly(albumId);
//    }

    @Test
    @DisplayName("should return album By Id")
    public void should_return_album_by_id() {
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songDto.id()))
                .title("album title 1")
                .build());
        Long albumId = albumDto.id();
        // when
        AlbumDto albumById = songifyCrudFacade.findAlbumById(albumId);
        // then
        assertThat(albumById)
                .isEqualTo(new AlbumDto(albumId, "album title 1", Set.of(songDto.id())));
    }

//    @Test
//    @DisplayName("should return album by id 2")
//    public void should_return_album_by_id_2() {
//        // given
//        SongRequestDto song = SongRequestDto.builder()
//                .name("song1")
//                .language(SongLanguageDto.ENGLISH)
//                .build();
//        SongDto songDto = songifyCrudFacade.addSong(song);
//        Long songId = songDto.id();
//        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
//                .builder()
//                .songIds(Set.of(songId))
//                .title("album title 1")
//                .build());
//        Long albumId = albumDto.id();
//        // when
//        AlbumDto albumById = songifyCrudFacade.findAlbumById(albumId);
//        // then
//        assertThat(albumById)
//                .isEqualTo(new AlbumDto(albumId, "album title 1"));
//    }

    @Test
    @DisplayName("should throw exception when album not found by id")
    public void should_throw_exception_when_album_not_found_by_id() {
        // given
        assertThat(songifyCrudFacade.findAllAlbums().isEmpty());
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumById(55L));
        // then
        assertThat(throwable).isInstanceOf(AlbumNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("album with id: 55 not found");
    }

    @Test
    @DisplayName("should throw exception when song not found by id")
    public void should_throw_exception_when_song_not_found_by_id() {
        // given
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).isEmpty());
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(55l));
        // then
        assertThat(throwable).isInstanceOf(SongNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Song with id: 55 not found");
    }

    @Test
    @DisplayName("should retrieve song with genre")
    public void should_retrieve_song() {
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        // when
        SongDto songDtoById = songifyCrudFacade.findSongDtoById(songDto.id());
        // then
        assertThat(songDtoById.genre().name()).isEqualTo("default");
        assertThat(songDtoById.genre().id()).isEqualTo(1);
    }

//    @Test
//    @DisplayName("should delete only artist from album by when there were more than 1 artist in album")
//    public void should_delete_only_artist_from_album_by_when_there_were_more_than_one_artist_in_album() {
//        // given
//        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
//                .name("shawn mendes")
//                .build();
//        ArtistRequestDto camilaCabello = ArtistRequestDto.builder()
//                .name("camila cabello")
//                .build();
//
//        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();
//        Long artistId2 = songifyCrudFacade.addArtist(camilaCabello).id();
//
//        SongRequestDto songSeniorita = SongRequestDto.builder()
//                .name("Seniorita")
//                .language(SongLanguageDto.ENGLISH)
//                .build();
//        SongDto songSenioritaDto = songifyCrudFacade.addSong(songSeniorita);
//        Long songSenioritaDtoId = songSenioritaDto.id();
//
//        AlbumDto albumWithSeniorita = songifyCrudFacade.addAlbumWithSong(
//                AlbumRequestDto.builder()
//                        .songIds(Set.of(songSenioritaDtoId))
//                        .title("Album with Seniorita")
//                        .build()
//        );
//        Long albumId = albumWithSeniorita.id();
//        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
//        songifyCrudFacade.addArtistToAlbum(artistId2, albumId);
//        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(2);
//        //when
//        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
//        //then
//        AlbumInfo album = songifyCrudFacade.findAlbumByIdWithArtistsAndSongs(albumId);
//        Set<AlbumInfo.ArtistInfo> artists = album.getArtists();
//        assertThat(artists)
//                .extracting("id")
//                .containsOnly(artistId2);
//    }

    @Test
    @DisplayName("should delete artist with albums and songs by id when artist was the only artist in albums")
    public void should_delete_artist_with_albums_and_songs_by_id_when_artist_was_the_only_artist_in_albums() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("shawn mendes")
                .build();
        Long artistId = songifyCrudFacade.addArtist(shawnMendes).id();

        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto song2 = SongRequestDto.builder()
                .name("song2")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto song3 = SongRequestDto.builder()
                .name("song3")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongRequestDto song4 = SongRequestDto.builder()
                .name("Seniorita")
                .language(SongLanguageDto.ENGLISH)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        SongDto songDto2 = songifyCrudFacade.addSong(song2);
        SongDto songDto3 = songifyCrudFacade.addSong(song3);
        SongDto songDto4 = songifyCrudFacade.addSong(song4);
        Long songId = songDto.id();
        Long songId2 = songDto2.id();
        Long songId3 = songDto3.id();
        Long songId4 = songDto4.id();

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId, songId2))
                .title("album1")
                .build());
        AlbumDto albumDto2 = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songIds(Set.of(songId3, songId4))
                .title("album2")
                .build());

        Long albumId = albumDto.id();
        Long albumId2 = albumDto2.id();

        songifyCrudFacade.addArtistToAlbum(artistId, albumId);
        songifyCrudFacade.addArtistToAlbum(artistId, albumId2);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId)).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistsByAlbumId(albumId2)).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllAlbums().size()).isEqualTo(2);
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).size()).isEqualTo(4);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).isEmpty());
        assertThat(songifyCrudFacade.findAllAlbums().isEmpty());
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()).isEmpty());
    }
}
