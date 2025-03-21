package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


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
                artistUpdater
        );
    }

    @Test
    public void should_add_artist_amigo_when_amigo_was_sent() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        // when
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);

        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("amigo");
    }

    @Test
    public void should_add_return_correct_dto() {
        // given
        ArtistRequestDto shawnMendes = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        assertThat(allArtists).isEmpty();
        // when
        ArtistDto response = songifyCrudFacade.addArtist(shawnMendes);

        // then
        assertThat(response.name()).isEqualTo("amigo");
        assertThat(response.id()).isNotNull();
    }

    @Test
    public void should_add_shawn_mendes_when_amigo_was_sent() {
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
    public void third() {
        // given

        // when

        // then

    }
}