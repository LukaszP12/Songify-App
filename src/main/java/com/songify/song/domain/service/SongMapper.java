package com.songify.song.domain.service;

import com.songify.song.domain.entities.Song;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.CreateSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.DeleteSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.GetAllSongsResponseDto;
import com.songify.song.infrastructure.controller.dto.response.GetSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.song.infrastructure.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public class SongMapper {

    public static Song mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }

    public static Song mapFromCreateSongRequestDtoToSongDto(CreateSongRequestDto dto) {
        return new Song(dto.songName(), dto.songName());
    }

    public static Song mapFromUpdateSongRequestDtoToSongDto(UpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }

    public static CreateSongResponseDto mapFromSongToCreateSongResponseDto(Song song) {
        return new CreateSongResponseDto(song);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        return new UpdateSongResponseDto(newSong.getName(), newSong.getArtist());
    }

    public static PartiallyUpdateSongResponseDto mapFromSongDtoToPartiallyUpdateSongResponseDto(Song songDto) {
        return new PartiallyUpdateSongResponseDto(songDto.getName(), songDto.getArtist());
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(Song songDto) {
        return new GetSongResponseDto(songDto);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<Song> songs) {
        return new GetAllSongsResponseDto(songs);
    }
}
