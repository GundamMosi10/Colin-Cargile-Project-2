package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.repository.ArtistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtistRepository artistRepository;

    private ObjectMapper mapper = new ObjectMapper();

    Artist inputArtist1;
    Artist outputArtist1;
    Artist inputArtist2;
    Artist outputArtist2;

    List<Artist> allArtists;

    @Before
    public void setUp() throws Exception {
        inputArtist1 = new Artist("Trivium", "@triviumband", "@triviumbandontwitter");
        outputArtist1 = new Artist("Trivium", "@triviumband", "@triviumbandontwitter");
        outputArtist1.setId(1);
        inputArtist2 = new Artist("Rage Against The Machine", "@rageagainstthemachine", "@RATMofficial");
        outputArtist2 = new Artist("Rage Against The Machine", "@rageagainstthemachine", "@RATMofficial");
        outputArtist2.setId(2);

        allArtists = new ArrayList<>(Arrays.asList(outputArtist1, outputArtist2));

        doReturn(outputArtist1).when(artistRepository).save(inputArtist1);
        doReturn(Optional.of(outputArtist1)).when(artistRepository).findById(1);

        doReturn(outputArtist2).when(artistRepository).save(inputArtist2);
        doReturn(Optional.of(outputArtist2)).when(artistRepository).findById(2);

        doReturn(allArtists).when(artistRepository).findAll();
    }

    @Test
    public void shouldCreateNewArtist() throws Exception {
        String inputJson = mapper.writeValueAsString(inputArtist1);
        String outputJson = mapper.writeValueAsString(outputArtist1);

        mockMvc.perform(post("/artist")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetArtistById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputArtist1);

        mockMvc.perform(get("/artist/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllArtists() throws Exception {
        String outputJson = mapper.writeValueAsString(allArtists);

        mockMvc.perform(get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingArtist() throws Exception {
        inputArtist1.setId(1);
        inputArtist1.setTwitter("@triviumbandontwitter");

        String inputJson = mapper.writeValueAsString(inputArtist1);

        mockMvc.perform(put("/artist")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingArtist() throws Exception {
        mockMvc.perform(delete("/artist/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}