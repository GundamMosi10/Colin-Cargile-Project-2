package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Label;
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
public class LabelRepositoryTest {

    @Autowired
    LabelRepository labelRepository;

    private Label label;

    @Before
    public void setUp() throws Exception {
        labelRepository.deleteAll();
        label = new Label();
        label.setName("Roadrunner Records");
        label.setWebsite("https://www.elektramusicgroup.com/roadrunnerrecords");
        labelRepository.save(label);
//        Label label2 = new Label();
//        label2.setName("Epic Records");
//        label2.setWebsite("https://www.epicrecords.com/")
    }

    @Test
    public void addGetDeleteLabel() {
        Optional<Label> label1 = labelRepository.findById(label.getId());
        assertEquals(label1.get(), label);

        labelRepository.deleteById(label.getId());
        label1 = labelRepository.findById(label.getId());
        assertFalse(label1.isPresent());
    }

    @Test
    public void shouldUpdateLabel() {
        labelRepository.save(label);
        label.setWebsite("https://www.elektramusicgroup.com/meepmeeprecords");
        labelRepository.save(label);

        Optional<Label> label1 = labelRepository.findById(label.getId());
        assertEquals(label1.get(), label);
    }

    @Test
    public void getAllLabels() {
        label = new Label();
        label.setName("Epic Records");
        label.setWebsite("https://www.epicrecords.com/");
        labelRepository.save(label);

        List<Label> labelList = labelRepository.findAll();
        assertEquals(labelList.size(), 2);
    }
}