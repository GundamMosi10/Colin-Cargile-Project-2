package com.company.musicstorecatalog.controller;

import static org.junit.Assert.*;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrackRepository trackRepository;

    private ObjectMapper mapper = new ObjectMapper();

    Track inputTrack1;
    Track outputTrack1;
    Track inputTrack2;
    Track outputTrack2;

    List<Track> allTracks;

    @Before
    public void setUp() throws Exception {
        inputTrack1 = new Track(1, "Like Light to the Flies", 540);
        outputTrack1 = new Track(1, "Like Light to the Flies", 540);
        outputTrack1.setId(7);
        inputTrack2 = new Track(2, "Know Your Enemy", 455);
        outputTrack2 = new Track(2, "Know Your Enemy", 455);
        outputTrack2.setId(6);

        allTracks = new ArrayList<>(Arrays.asList(outputTrack1, outputTrack2));

        doReturn(outputTrack1).when(trackRepository).save(inputTrack1);
        doReturn(Optional.of(outputTrack1)).when(trackRepository).findById(7);

        doReturn(outputTrack2).when(trackRepository).save(inputTrack2);
        doReturn(Optional.of(outputTrack2)).when(trackRepository).findById(6);

        doReturn(allTracks).when(trackRepository).findAll();
    }

    @Test
    public void shouldCreateNewTrack() throws Exception {
        String inputJson = mapper.writeValueAsString(inputTrack1);
        String outputJson = mapper.writeValueAsString(outputTrack1);

        mockMvc.perform(post("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldGetTrackById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputTrack1);

        mockMvc.perform(get("/track/7"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllTracks() throws Exception {
        String outputJson = mapper.writeValueAsString(allTracks);

        mockMvc.perform(get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingTrack() throws Exception {
        inputTrack1.setId(7);
        inputTrack1.setRunTime(620);
        inputTrack1.setTitle("Like Light to the Flies-LIVE");

        String inputJson = mapper.writeValueAsString(inputTrack1);

        mockMvc.perform(put("/track")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingTrack() throws Exception {
        mockMvc.perform(delete("/track/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}