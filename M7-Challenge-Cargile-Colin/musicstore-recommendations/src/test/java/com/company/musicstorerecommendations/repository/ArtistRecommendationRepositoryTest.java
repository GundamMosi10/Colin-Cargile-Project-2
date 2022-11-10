package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.model.LabelRecommendation;
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
public class ArtistRecommendationRepositoryTest {

    @Autowired
    ArtistRecommendationRepository artistRecommendationRepo;

    private ArtistRecommendation artistRecommendation;

    @Before
    public void setUp() throws Exception {
        artistRecommendationRepo.deleteAll();
        artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistId(1);
        artistRecommendation.setUserId(1);
        artistRecommendation.setLiked(true);
        artistRecommendationRepo.save(artistRecommendation);
    }

    @Test
    public void addGetDeleteArtistRecommendation() {
        Optional<ArtistRecommendation> artistRecommendation1 = artistRecommendationRepo.findById(artistRecommendation.getId());
        assertEquals(artistRecommendation1.get(), artistRecommendation);

        artistRecommendationRepo.deleteById(artistRecommendation.getId());
        artistRecommendation1 = artistRecommendationRepo.findById(artistRecommendation.getId());
        assertFalse(artistRecommendation1.isPresent());
    }

    @Test
    public void shouldUpdateArtistRecommendation() {
        artistRecommendationRepo.save(artistRecommendation);
        artistRecommendation.setLiked(false);
        artistRecommendationRepo.save(artistRecommendation);

        Optional<ArtistRecommendation> artistRecommendation1 = artistRecommendationRepo.findById(artistRecommendation.getId());
        assertEquals(artistRecommendation1.get(), artistRecommendation);
    }

    @Test
    public void getAllArtistRecommendations() {
        artistRecommendation = new ArtistRecommendation();
        artistRecommendation.setArtistId(1);
        artistRecommendation.setUserId(1);
        artistRecommendation.setLiked(true);
        artistRecommendationRepo.save(artistRecommendation);

        List<ArtistRecommendation> artistRecommendationList = artistRecommendationRepo.findAll();
        assertEquals(artistRecommendationList.size(), 2);
    }
}