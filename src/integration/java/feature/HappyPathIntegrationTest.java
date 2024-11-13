package feature;

import com.songify.SongifyApplication;
import com.songify.domain.crud.song.dto.SongDto;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class HappyPathIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("local_pgdb");

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public ObjectMapper objectMapper;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }


    // 1. when I go to /songs then I can see no songs
    @Test
    public void f() throws Exception {
        // given
        // when
//        ResultActions getSongsResult = mockMvc.perform(get("/songs")
//                        .contentType(MediaType.APPLICATION_JSON));
        // then
//        getSongsResult.andExpect(status().isOk())
//                .andExpect(jsonPath("$.songs", empty()));
        ResultActions perform = mockMvc.perform(get("/songs")
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult getSongsActionResult = perform.andExpect(status().isOk())
                .andReturn();

        String contentAsString = getSongsActionResult.getResponse().getContentAsString();
        GetAllSongsResponseDto allSongsResponseDto = objectMapper.readValue(contentAsString, GetAllSongsResponseDto.class);
        assertThat(allSongsResponseDto.songs()).hasSize(4);
    }
    // 2.when I post to /song with Song "Till I collapse" then Song "Till I collapse" is returned with id 1


}
