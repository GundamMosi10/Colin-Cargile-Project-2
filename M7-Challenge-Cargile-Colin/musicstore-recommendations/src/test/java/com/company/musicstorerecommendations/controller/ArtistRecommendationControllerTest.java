package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.repository.ArtistRecommendationRepository;
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

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtistRecommendationRepository artistRecommendationsRepo;

    private ObjectMapper mapper = new ObjectMapper();

    ArtistRecommendation inputArtistRecommend1;
    ArtistRecommendation outputArtistRecommend1;
    ArtistRecommendation inputArtistRecommend2;
    ArtistRecommendation outputArtistRecommend2;

    List<ArtistRecommendation> allArtistRecommendations;

    @Before
    public void setUp() throws Exception {
        inputArtistRecommend1 = new ArtistRecommendation(1, 1, true);
        outputArtistRecommend1 = new ArtistRecommendation(1, 1, true);
        outputArtistRecommend1.setId(1);
        inputArtistRecommend2 = new ArtistRecommendation(2, 2, true);
        outputArtistRecommend2 = new ArtistRecommendation(2, 2, true);
        outputArtistRecommend2.setId(2);

        allArtistRecommendations = new ArrayList<>(Arrays.asList(outputArtistRecommend1, outputArtistRecommend2));

        doReturn(outputArtistRecommend1).when(artistRecommendationsRepo).save(inputArtistRecommend1);
        doReturn(Optional.of(outputArtistRecommend1)).when(artistRecommendationsRepo).findById(1);

        doReturn(outputArtistRecommend2).when(artistRecommendationsRepo).save(inputArtistRecommend2);
        doReturn(Optional.of(outputArtistRecommend2)).when(artistRecommendationsRepo).findById(2);

        doReturn(allArtistRecommendations).when(artistRecommendationsRepo).findAll();
    }

    @Test
    public void shouldCreateNewArtistRecommendations() throws Exception {
        String inputJson = mapper.writeValueAsString(inputArtistRecommend1);
        String outputJson = mapper.writeValueAsString(outputArtistRecommend1);

        mockMvc.perform(post("/artistRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetArtistRecommendationsById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputArtistRecommend1);

        mockMvc.perform(get("/artistRecommendation/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllArtistRecommendations() throws Exception {
        String outputJson = mapper.writeValueAsString(allArtistRecommendations);

        mockMvc.perform(get("/artistRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingArtistRecommendations() throws Exception {
        inputArtistRecommend1.setId(1);
        inputArtistRecommend1.setLiked(false);

        String inputJson = mapper.writeValueAsString(inputArtistRecommend1);

        mockMvc.perform(put("/artistRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingArtistRecommendations() throws Exception {
        mockMvc.perform(delete("/artistRecommendation/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}