package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LabelRepository labelRepository;

    private ObjectMapper mapper = new ObjectMapper();

    Label inputLabel1;
    Label outputLabel1;
    Label inputLabel2;
    Label outputLabel2;

    List<Label> allLabels;

    @Before
    public void setUp() throws Exception {
        inputLabel1 = new Label("Roadrunner Records", "https://www.elektramusicgroup.com/roadrunnerrecords");
        outputLabel1 = new Label("Roadrunner Records", "https://www.elektramusicgroup.com/roadrunnerrecords");
        outputLabel1.setId(1);
        inputLabel2 = new Label("Epic Records","https://www.epicrecords.com/");
        outputLabel2 = new Label("Epic Records","https://www.epicrecords.com/");
        outputLabel2.setId(2);

        allLabels = new ArrayList<>(Arrays.asList(outputLabel1, outputLabel2));

        doReturn(outputLabel1).when(labelRepository).save(inputLabel1);
        doReturn(Optional.of(outputLabel1)).when(labelRepository).findById(1);

        doReturn(outputLabel2).when(labelRepository).save(inputLabel2);
        doReturn(Optional.of(outputLabel2)).when(labelRepository).findById(2);

        doReturn(allLabels).when(labelRepository).findAll();
    }

    @Test
    public void shouldCreateNewLabel() throws Exception {
        String inputJson = mapper.writeValueAsString(inputLabel1);
        String outputJson = mapper.writeValueAsString(outputLabel1);

        mockMvc.perform(post("/label")
                .content(inputJson)
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldGetLabelById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputLabel1);

        mockMvc.perform(get("/label/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllLabels() throws Exception {
        String outputJson = mapper.writeValueAsString(allLabels);

        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingLabel() throws Exception {
        inputLabel1.setId(1);
        inputLabel1.setName("Lifeforce Records");
        inputLabel1.setWebsite("https://lifeforcerecords.com/");

        String inputJson = mapper.writeValueAsString(inputLabel1);

        mockMvc.perform(put("/label")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingLabel() throws Exception {
        mockMvc.perform(delete("/label/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }











}