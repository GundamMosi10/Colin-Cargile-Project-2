package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.AlbumRecommendation;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRecommendationRepositoryTest {

    @Autowired
    AlbumRecommendationRepository albumRecommendationRepo;

    private AlbumRecommendation albumRecommendation;

    @Before
    public void setUp() throws Exception {
        albumRecommendationRepo.deleteAll();
        albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumId(1);
        albumRecommendation.setUserId(1);
        albumRecommendation.setLiked(true);
        albumRecommendationRepo.save(albumRecommendation);
    }

    @Test
    public void addGetDeleteAlbumRecommendation() {
        Optional<AlbumRecommendation> albumRecommendations1 = albumRecommendationRepo.findById(albumRecommendation.getId());
        assertEquals(albumRecommendations1.get(), albumRecommendation);

        albumRecommendationRepo.deleteById(albumRecommendation.getId());
        albumRecommendations1 = albumRecommendationRepo.findById(albumRecommendation.getId());
        assertFalse(albumRecommendations1.isPresent());
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        albumRecommendationRepo.save(albumRecommendation);
        albumRecommendation.setLiked(false);
        albumRecommendationRepo.save(albumRecommendation);

        Optional<AlbumRecommendation> albumRecommendation1 = albumRecommendationRepo.findById(albumRecommendation.getId());
        assertEquals(albumRecommendation1.get(), albumRecommendation);
    }

    @Test
    public void getAllAlbumRecommendations() {
        albumRecommendation = new AlbumRecommendation();
        albumRecommendation.setAlbumId(1);
        albumRecommendation.setUserId(1);
        albumRecommendation.setLiked(true);
        albumRecommendationRepo.save(albumRecommendation);

        List<AlbumRecommendation> albumRecommendationList = albumRecommendationRepo.findAll();
        assertEquals(albumRecommendationList.size(), 2);
    }
}