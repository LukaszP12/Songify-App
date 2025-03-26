package com.songify.domain.songplayer;

import com.songify.domain.crud.SongifyCrudFacade;
import com.songify.domain.crud.dto.SongDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SongPlayerFacade {

    private final SongifyCrudFacade songifyCrudFacade;
    private final YoutubeHttpClient youtubeHttpClient;

    public String playSongWithId(Long id) {
        SongDto songDtoById = songifyCrudFacade.findSongDtoById(id);
        String name = songDtoById.name();
        // swoj kod który wykonuje zeby uruchomic np. zapytanie do Youtube po nazwie piosenki
        String result = youtubeHttpClient.playSongByName(name);
        if (result.equals("success")) {
            return result;
        }
        throw new RuntimeException("some error - result failed");
    }

}
