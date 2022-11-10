package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.repository.LabelRecommendationRepository;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LabelRecommendationRepository labelRecommendationsRepo;

    private ObjectMapper mapper = new ObjectMapper();

    LabelRecommendation inputLabelRecommend1;
    LabelRecommendation outputLabelRecommend1;
    LabelRecommendation inputLabelRecommend2;
    LabelRecommendation outputLabelRecommend2;

    List<LabelRecommendation> allLabelRecommendations;

    @Before
    public void setUp() throws Exception {
        inputLabelRecommend1 = new LabelRecommendation(1, 1, true);
        outputLabelRecommend1 = new LabelRecommendation(1, 1, true);
        outputLabelRecommend1.setId(1);
        inputLabelRecommend2 = new LabelRecommendation(2, 2, true);
        outputLabelRecommend2 = new LabelRecommendation(2, 2, true);
        outputLabelRecommend2.setId(2);

        allLabelRecommendations = new ArrayList<>(Arrays.asList(outputLabelRecommend1, outputLabelRecommend2));

        doReturn(outputLabelRecommend1).when(labelRecommendationsRepo).save(inputLabelRecommend1);
        doReturn(Optional.of(outputLabelRecommend1)).when(labelRecommendationsRepo).findById(1);

        doReturn(outputLabelRecommend2).when(labelRecommendationsRepo).save(inputLabelRecommend2);
        doReturn(Optional.of(outputLabelRecommend2)).when(labelRecommendationsRepo).findById(2);

        doReturn(allLabelRecommendations).when(labelRecommendationsRepo).findAll();
    }

    @Test
    public void shouldCreateNewLabelRecommendations() throws Exception {
        String inputJson = mapper.writeValueAsString(inputLabelRecommend1);
        String outputJson = mapper.writeValueAsString(outputLabelRecommend1);

        mockMvc.perform(post("/labelRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetLabelRecommendationsById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputLabelRecommend1);

        mockMvc.perform(get("/labelRecommendation/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllLabelRecommendations() throws Exception {
        String outputJson = mapper.writeValueAsString(allLabelRecommendations);

        mockMvc.perform(get("/labelRecommendation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingLabelRecommendations() throws Exception {
        inputLabelRecommend1.setId(1);
        inputLabelRecommend1.setLiked(false);

        String inputJson = mapper.writeValueAsString(inputLabelRecommend1);

        mockMvc.perform(put("/labelRecommendation")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingLabelRecommendations() throws Exception {
        mockMvc.perform(delete("/labelRecommendation/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}