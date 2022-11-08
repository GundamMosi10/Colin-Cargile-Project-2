package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {
    @Autowired
    ArtistRepository artistRepository;

    private Artist artist;

    @Before
    public void setUp() throws Exception {
        artistRepository.deleteAll();
        artist = new Artist();
        artist.setName("Trivium");
        artist.setInstagram("@triviumband");
        artist.setTwitter("@triviumbandontwitter");
        artistRepository.save(artist);
    }

    @Test
    public void addGetDeleteArtist() {
        Optional<Artist> artist1 = artistRepository.findById(artist.getId());
        assertEquals(artist1.get(), artist);

        artistRepository.deleteById(artist.getId());
        artist1 = artistRepository.findById(artist.getId());
        assertFalse(artist1.isPresent());
    }

    @Test
    public void shouldUpdateArtist() {
        artistRepository.save(artist);
        artist.setTwitter("@triviumTHEBESTBANDonELONSTWITTER");
        artistRepository.save(artist);

        Optional<Artist> artist1 = artistRepository.findById(artist.getId());
        assertEquals(artist1.get(), artist);
    }

    @Test
    public void getAllArtists() {
        artist = new Artist();
        artist.setName("Rage Against The Machine");
        artist.setInstagram("@rageagainstthemachine");
        artist.setTwitter("@RATMofficial");
        artistRepository.save(artist);

        List<Artist> artistList = artistRepository.findAll();
        assertEquals(artistList.size(),2);
    }
}