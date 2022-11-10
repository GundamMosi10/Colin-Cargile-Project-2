package com.company.musicstorerecommendations.repository;

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
public class LabelRecommendationRepositoryTest {

    @Autowired
    LabelRecommendationRepository labelRecommendationRepo;

    private LabelRecommendation labelRecommendation;

    @Before
    public void setUp() throws Exception {
        labelRecommendationRepo.deleteAll();
        labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelId(1);
        labelRecommendation.setUserId(1);
        labelRecommendation.setLiked(true);
        labelRecommendationRepo.save(labelRecommendation);
    }

    @Test
    public void addGetDeleteLabelRecommendation() {
        Optional<LabelRecommendation> labelRecommendations1 = labelRecommendationRepo.findById(labelRecommendation.getId());
        assertEquals(labelRecommendations1.get(), labelRecommendation);

        labelRecommendationRepo.deleteById(labelRecommendation.getId());
        labelRecommendations1 = labelRecommendationRepo.findById(labelRecommendation.getId());
        assertFalse(labelRecommendations1.isPresent());
    }

    @Test
    public void shouldUpdateLabelRecommendation() {
        labelRecommendationRepo.save(labelRecommendation);
        labelRecommendation.setLiked(false);
        labelRecommendationRepo.save(labelRecommendation);

        Optional<LabelRecommendation> labelRecommendation1 = labelRecommendationRepo.findById(labelRecommendation.getId());
        assertEquals(labelRecommendation1.get(), labelRecommendation);
    }

    @Test
    public void getAllLabelRecommendations() {
        labelRecommendation = new LabelRecommendation();
        labelRecommendation.setLabelId(1);
        labelRecommendation.setUserId(1);
        labelRecommendation.setLiked(true);
        labelRecommendationRepo.save(labelRecommendation);

        List<LabelRecommendation> labelRecommendationList = labelRecommendationRepo.findAll();
        assertEquals(labelRecommendationList.size(), 2);
    }
}