package be.vdab.moviestestjpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@Sql({"/genres.sql", "/distributors.sql","/movies.sql", "/comments.sql", "/moviesgenres.sql", "/actors.sql", "/directors.sql", "/moviesdirectors.sql", "/roles.sql"})
@AutoConfigureMockMvc
public class MovieControllerTest {
    private final JdbcClient jdbcClient;
    private final MockMvc mockMvc;

    private final static String MOVIES_TABLE = "movies";

    public MovieControllerTest(JdbcClient jdbcClient, MockMvc mockMvc) {
        this.jdbcClient = jdbcClient;
        this.mockMvc = mockMvc;
    }

    long idVanTestMovie() {
        return jdbcClient.sql("select id from movies where name = 'testMovie'")
                .query(Long.class)
                .single();
    }

    @Test
    void findByIdMetEenBestaandeIdVindtEenMovie() throws Exception {
        long id = idVanTestMovie();
        mockMvc.perform(get("/movies/{id}", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("name").value("testMovie"));
    }
    @Test
    void findByIdMetOnbestaandeIdMislukt() throws Exception {
        mockMvc.perform(get("/movies/{id}", Long.MAX_VALUE))
                .andExpect(
                        status().isNotFound());
    }
    @Test
    void findYearsVindtJuisteAantalRecords() throws Exception {
        var aantalRecords = jdbcClient.sql("select count(year) from (select distinct year from movies) as aantal").query(Long.class).single();

        mockMvc.perform(get("/movies/years"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(aantalRecords));
    }
    @Test
    void findByYearVindtDeJuisteMovies() throws Exception {
        mockMvc.perform(get("/movies").param("year", "2000"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()")
                                .value(JdbcTestUtils.countRowsInTableWhere(jdbcClient, MOVIES_TABLE, "year = 2000")));
    }
    @Test
    void changeRankingWijzigtEenRankingVanMovie() throws Exception {
        long id = idVanTestMovie();

        mockMvc.perform(patch("/movies/{id}/ranking", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("7"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/movies/{id}", id))
                .andExpectAll(status().isOk(),
                        jsonPath("id").value(id),
                        jsonPath("name").value("testMovie"),
                        jsonPath("ranking").value(7));
    }
    @Test
    void changeRankingVanOnbestaandeMovieMislukt() throws Exception {
        mockMvc.perform(patch("/movies/{id}/ranking", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("7"))
                .andExpect(status().isNotFound());
    }
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "-1", "11"})
    void changeRankingMetVerkeerdeWaardeMislukt(String ranking) throws Exception {
        long id = idVanTestMovie();

        mockMvc.perform(patch("/movies/{id}/ranking", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ranking))
                .andExpect(status().isBadRequest());
    }
    @Test
    void findCommentsVindtAlleCommentsVanEenFilm() throws Exception {
        long id = idVanTestMovie();
        mockMvc.perform(get("/movies/{id}/comments", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(2),
                        jsonPath("[0].emailAddress").value("testComment1@example.org"),
                        jsonPath("[1].emailAddress").value("testComment2@example.org"));

    }

    @Test
    void findRolesFromAMovieVindtAlleRolsVanEenMovie() throws Exception {
        long id = idVanTestMovie();

        mockMvc.perform(get("/movies/{id}/roles", id))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("length()").value(2),
                        jsonPath("[0].roleName").value("testRole1"),
                        jsonPath("[0].actorFirstname").value("testActorFirstname1"),
                        jsonPath("[0].actorLastname").value("testActorLastname1"),
                        jsonPath("[0].gender").value("M"),
                        jsonPath("[1].roleName").value("testRole2"),
                        jsonPath("[1].actorFirstname").value("testActorFirstname2"),
                        jsonPath("[1].actorLastname").value("testActorLastname2"),
                        jsonPath("[1].gender").value("F"));
    }

}
