package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendations;
import com.company.musicstorerecommendations.repository.AlbumRecommendationsRepository;
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
@WebMvcTest(AlbumRecommendationsController.class)
public class AlbumRecommendationsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlbumRecommendationsRepository albumRecommendationsRepo;

    private ObjectMapper mapper = new ObjectMapper();

    AlbumRecommendations inputAlbumRecommend1;
    AlbumRecommendations outputAlbumRecommend1;
    AlbumRecommendations inputAlbumRecommend2;
    AlbumRecommendations outputAlbumRecommend2;

    List<AlbumRecommendations> allAlbumRecommendations;

    @Before
    public void setUp() throws Exception {
        inputAlbumRecommend1 = new AlbumRecommendations(1, 1, true);
        outputAlbumRecommend1 = new AlbumRecommendations(1, 1, true);
        outputAlbumRecommend1.setId(1);
        inputAlbumRecommend2 = new AlbumRecommendations(2, 2, true);
        outputAlbumRecommend2 = new AlbumRecommendations(2, 2, true);
        outputAlbumRecommend2.setId(2);

        allAlbumRecommendations = new ArrayList<>(Arrays.asList(outputAlbumRecommend1, outputAlbumRecommend2));

        doReturn(outputAlbumRecommend1).when(albumRecommendationsRepo).save(inputAlbumRecommend1);
        doReturn(Optional.of(outputAlbumRecommend1)).when(albumRecommendationsRepo).findById(1);

        doReturn(outputAlbumRecommend2).when(albumRecommendationsRepo).save(inputAlbumRecommend2);
        doReturn(Optional.of(outputAlbumRecommend2)).when(albumRecommendationsRepo).findById(2);

        doReturn(allAlbumRecommendations).when(albumRecommendationsRepo).findAll();
    }

    @Test
    public void shouldCreateNewAlbumRecommendations() throws Exception {
        String inputJson = mapper.writeValueAsString(inputAlbumRecommend1);
        String outputJson = mapper.writeValueAsString(outputAlbumRecommend1);

        mockMvc.perform(post("/albumRecommendations")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAlbumRecommendationsById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputAlbumRecommend1);

        mockMvc.perform(get("/albumRecommendations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllAlbumRecommendations() throws Exception {
        String outputJson = mapper.writeValueAsString(allAlbumRecommendations);

        mockMvc.perform(get("/albumRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingAlbumRecommendations() throws Exception {
        inputAlbumRecommend1.setId(1);
        inputAlbumRecommend1.setLiked(false);

        String inputJson = mapper.writeValueAsString(inputAlbumRecommend1);

        mockMvc.perform(put("/albumRecommendations")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingAlbumRecommendations() throws Exception {
        mockMvc.perform(delete("/albumRecommendations/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}