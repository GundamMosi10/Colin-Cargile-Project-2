package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.LabelRecommendations;
import com.company.musicstorerecommendations.repository.LabelRecommendationsRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelRecommendations")
public class LabelRecommendationsController {

    @Autowired
    LabelRecommendationsRepository repo;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendations getLabelRecommendationsById(@PathVariable Integer id) {
        Optional<LabelRecommendations> returnVal = repo.findById(id);
        if(returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new IllegalArgumentException("Label Recommendations ID is not present.");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendations createLabelRecommendation(@RequestBody LabelRecommendations labelRecommendations) {
        return repo.save(labelRecommendations);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendations(@RequestBody LabelRecommendations labelRecommendations) {
        if(labelRecommendations.getId() != null) {
            repo.save(labelRecommendations);
        } else {
            throw new IllegalArgumentException("Label Recommendations ID is not present.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendations(@PathVariable Integer id) {
        repo.deleteById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendations> getAllLabelRecommendations() {
        return repo.findAll();
    }
}
