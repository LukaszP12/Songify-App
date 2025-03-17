package com.songify.domain.crud.song;

import com.songify.infrastructure.songplayer.controller.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.songplayer.controller.dto.response.SongDto;

public class SongDomainMapper {

    public static SongDto mapFromSongToSongDto(Song song) {
        return new SongDto(song.getId(), song.getName(), song.getArtist());
    }

    public static Song mapFromCreateSongRequestDtoToSong(CreateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }

    public static Song mapFromUpdateSongRequestDtoToSongDto(UpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }

    public static Song mapFromPartiallyUpdateSongRequestDtoToSong(PartiallyUpdateSongRequestDto dto) {
        return new Song(dto.songName(), dto.artistName());
    }
}
