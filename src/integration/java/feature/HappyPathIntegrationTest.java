package feature;

import com.songify.SongifyApplication;
import com.songify.infrastructure.crud.song.controller.dto.response.GetAllSongsResponseDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
    @Test
    public void f1() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/songs")
                .content("""
                        {
                          "name": "Till i collapse",
                          "releaseDate": "2024-03-15T13:55:21.850Z",
                          "duration": 0,
                          "language": "ENGLISH"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // when
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        // then
        perform
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.name", is("Till i collapse")))
                .andExpect(jsonPath("$.song.genre.id", is(1)))
                .andExpect(jsonPath("$.song.genre.name", is("default")));
    }

    // 3. when I post to /song with Song "Lose Yourself" then Song "Lose Yourself" is returned with id 2
    @Test
    public void f2() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/songs")
                        .content(
                                """
                                        {
                                          "name": "Lose Yourself",
                                          "releaseDate": "2024-03-15T13:55:21.850Z",
                                          "duration": 0,
                                          "language": "ENGLISH"
                                        }
                                        """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(2)))
                .andExpect(jsonPath("$.song.name", is("Lose Yourself")))
                .andExpect(jsonPath("$.song.genre.id", is(1)))
                .andExpect(jsonPath("$.song.genre.name", is("default")));
        // when

        // then
    }

    // 4. when I go to /genre then I can see only default genre with 1
    @Test
    public void f3() throws Exception {
        mockMvc.perform(post("/genres")
                        .content(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genres[0].id", is(1)))
                .andExpect(jsonPath("$.genres[0].name", is("default")));
    }

    // 5. when I post to /genres with Genre "Rap" then Genre "Rap" is returned with id 2
    @Test
    public void f4() throws Exception {
        mockMvc.perform(post("/genres")
                        .content("""
                                {
                                    "name": "Rap"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Rap")));
    }

    // 6. when I go to /songs/1 then I can see default genre with id and name default
    @Test
    public void f5() throws Exception {
        mockMvc.perform(get("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.genre.id", is(1)))
                .andExpect(jsonPath("$.song.name", is("Till i collapse")))
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.genre.name", is("default")));
    }

    // 7. when I put to /songs/1/genre/2 then Genre with id 2 ("Rap") is added to Song with id 2
    @Test
    public void f6() throws Exception {
        mockMvc.perform(put("/songs/1/genres/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("updated")));
    }

    // 8. when I go to /songs/1 then I can see "Rap" genre
    @Test
    public void f7() throws Exception {
        mockMvc.perform(get("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.genre.id", is(2)))
                .andExpect(jsonPath("$.song.genre.name", is("Rap")));
    }
}
