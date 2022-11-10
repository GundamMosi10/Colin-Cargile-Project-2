package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRecommendation;
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
public class TrackRecommendationRepositoryTest {

    @Autowired
    TrackRecommendationRepository trackRecommendationRepo;

    private TrackRecommendation trackRecommendation;

    @Before
    public void setUp() throws Exception {
        trackRecommendationRepo.deleteAll();
        trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackId(1);
        trackRecommendation.setUserId(1);
        trackRecommendation.setLiked(true);
        trackRecommendationRepo.save(trackRecommendation);
    }

    @Test
    public void addGetDeleteTrackRecommendation() {
        Optional<TrackRecommendation> trackRecommendation1 = trackRecommendationRepo.findById(trackRecommendation.getId());
        assertEquals(trackRecommendation1.get(), trackRecommendation);

        trackRecommendationRepo.deleteById(trackRecommendation.getId());
        trackRecommendation1 = trackRecommendationRepo.findById(trackRecommendation.getId());
        assertFalse(trackRecommendation1.isPresent());
    }

    @Test
    public void shouldUpdateTrackRecommendation() {
        trackRecommendationRepo.save(trackRecommendation);
        trackRecommendation.setLiked(false);
        trackRecommendationRepo.save(trackRecommendation);

        Optional<TrackRecommendation> trackRecommendation1 = trackRecommendationRepo.findById(trackRecommendation.getId());
        assertEquals(trackRecommendation1.get(), trackRecommendation);
    }

    @Test
    public void getAllTrackRecommendations() {
        trackRecommendation = new TrackRecommendation();
        trackRecommendation.setTrackId(1);
        trackRecommendation.setUserId(1);
        trackRecommendation.setLiked(true);
        trackRecommendationRepo.save(trackRecommendation);

        List<TrackRecommendation> trackRecommendationList = trackRecommendationRepo.findAll();
        assertEquals(trackRecommendationList.size(), 2);
    }
}