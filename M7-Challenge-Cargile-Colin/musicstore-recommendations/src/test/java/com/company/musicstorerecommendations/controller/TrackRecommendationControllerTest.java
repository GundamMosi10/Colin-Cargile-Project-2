package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.repository.TrackRecommendationRepository;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrackRecommendationRepository trackRecommendationsRepo;

    private ObjectMapper mapper = new ObjectMapper();

    TrackRecommendation inputTrackRecommend1;
    TrackRecommendation outputTrackRecommend1;
    TrackRecommendation inputTrackRecommend2;
    TrackRecommendation outputTrackRecommend2;

    List<TrackRecommendation> allTrackRecommendations;

    @Before
    public void setUp() throws Exception {
        inputTrackRecommend1 = new TrackRecommendation(1, 1, true);
        outputTrackRecommend1 = new TrackRecommendation(1, 1, true);
        outputTrackRecommend1.setId(1);
        inputTrackRecommend2 = new TrackRecommendation(2, 2, true);
        outputTrackRecommend2 = new TrackRecommendation(2, 2, true);
        outputTrackRecommend2.setId(2);

        allTrackRecommendations = new ArrayList<>(Arrays.asList(outputTrackRecommend1, outputTrackRecommend2));

        doReturn(outputTrackRecommend1).when(trackRecommendationsRepo).save(inputTrackRecommend1);
        doReturn(Optional.of(outputTrackRecommend1)).when(trackRecommendationsRepo).findById(1);

        doReturn(outputTrackRecommend2).when(trackRecommendationsRepo).save(inputTrackRecommend2);
        doReturn(Optional.of(outputTrackRecommend2)).when(trackRecommendationsRepo).findById(2);

        doReturn(allTrackRecommendations).when(trackRecommendationsRepo).findAll();
    }

    @Test
    public void shouldCreateNewTrackRecommendations() throws Exception {
        String inputJson = mapper.writeValueAsString(inputTrackRecommend1);
        String outputJson = mapper.writeValueAsString(outputTrackRecommend1);

        mockMvc.perform(post("/trackRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetTrackRecommendationsById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputTrackRecommend1);

        mockMvc.perform(get("/trackRecommendation/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllTrackRecommendations() throws Exception {
        String outputJson = mapper.writeValueAsString(allTrackRecommendations);

        mockMvc.perform(get("/trackRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingTrackRecommendations() throws Exception {
        inputTrackRecommend1.setId(1);
        inputTrackRecommend1.setLiked(false);

        String inputJson = mapper.writeValueAsString(inputTrackRecommend1);

        mockMvc.perform(put("/trackRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingTrackRecommendations() throws Exception {
        mockMvc.perform(delete("/trackRecommendation/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}