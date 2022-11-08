package com.company.musicstorecatalog.controller;

import static org.junit.Assert.*;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.repository.AlbumRepository;
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
import org.springframework.web.util.NestedServletException;


import java.time.LocalDate;
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
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlbumRepository albumRepository;

    private ObjectMapper mapper = new ObjectMapper();

    Album inputAlbum1;
    Album outputAlbum1;
    Album inputAlbum2;
    Album outputAlbum2;

    List<Album> allAlbums;

    @Before
    public void setUp() throws Exception {
        inputAlbum1 = new Album("Ascendancy", 1, LocalDate.of(2005,03,06),1, 10.99);
        outputAlbum1 = new Album("Ascendancy", 1, LocalDate.of(2005,03,06), 1, 10.99);
        outputAlbum1.setId(1);
        inputAlbum2 = new Album("Rage Against The Machine", 2, LocalDate.of(1992,11,03), 2, 16.99);
        outputAlbum2 = new Album("Rage Against The Machine", 2, LocalDate.of(1992,11,03), 2, 16.99);
        outputAlbum2.setId(2);

        allAlbums = new ArrayList<>(Arrays.asList(outputAlbum1, outputAlbum2));

        doReturn(outputAlbum1).when(albumRepository).save(inputAlbum1);
        doReturn(Optional.of(outputAlbum1)).when(albumRepository).findById(1);

        doReturn(outputAlbum2).when(albumRepository).save(inputAlbum2);
        doReturn(Optional.of(outputAlbum2)).when(albumRepository).findById(2);

        doReturn(allAlbums).when(albumRepository).findAll();
    }

    @Test //(expected = NestedServletException.class)
    public void shouldCreateNewAlbum() throws Exception {
        String inputJson = mapper.writeValueAsString(inputAlbum1);
        String outputJson = mapper.writeValueAsString(outputAlbum1);

        mockMvc.perform(post("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldGetAlbumById() throws Exception {
        String outputJson = mapper.writeValueAsString(outputAlbum1);

        mockMvc.perform(get("/album/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldGetAllAlbums() throws Exception {
        String outputJson = mapper.writeValueAsString(allAlbums);

        mockMvc.perform(get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldRespondWith204WhenUpdatingAlbum() throws Exception {
        inputAlbum1.setId(1);
        inputAlbum1.setListPrice(19.99);

        String inputJson = mapper.writeValueAsString(inputAlbum1);

        mockMvc.perform(put("/album")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWith204WhenDeletingAlbum() throws Exception {
        mockMvc.perform(delete("/album/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}