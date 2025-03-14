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
import com.songify.song.infrastructure.controller.dto.response.SongDto;
import com.songify.song.infrastructure.controller.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

public class SongMapper {

    public static SongDto mapFromSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
    }

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
        SongDto savedSongDto = SongMapper.mapFromSongToSongDto(song);
        return new CreateSongResponseDto(savedSongDto);
    }

    public static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK);
    }

    public static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(Song newSong) {
        return new UpdateSongResponseDto(new SongDto(newSong.getId(), newSong.getName(), newSong.getArtist()));
    }

    public static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(Song savedSong) {
        return new PartiallyUpdateSongResponseDto(new SongDto(savedSong.getId(),savedSong.getName(),savedSong.getArtist()));
    }

    public static PartiallyUpdateSongResponseDto mapFromSongDtoToPartiallyUpdateSongResponseDto(Song song) {
        SongDto songDto = SongMapper.mapFromSongToSongDto(song);
        return new PartiallyUpdateSongResponseDto(songDto);
    }

    public static GetSongResponseDto mapFromSongToGetSongResponseDto(Song song) {
        SongDto songDto = mapFromSongToSongDto(song);
        return new GetSongResponseDto(songDto);
    }

    public static GetAllSongsResponseDto mapFromSongToGetAllSongsResponseDto(List<Song> songs) {
        List<SongDto> songDtos = songs.stream()
                .map(SongMapper::mapFromSongToSongDto)
                .collect(Collectors.toList());
        return new GetAllSongsResponseDto(songDtos);
    }

}
