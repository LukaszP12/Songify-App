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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(post("/songs")
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

        // then

    }

}
