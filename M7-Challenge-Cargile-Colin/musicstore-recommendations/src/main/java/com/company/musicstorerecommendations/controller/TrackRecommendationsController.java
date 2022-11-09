package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.TrackRecommendations;
import com.company.musicstorerecommendations.repository.TrackRecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackRecommendations")
public class TrackRecommendationsController {

    @Autowired
    TrackRecommendationsRepository repo;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendations getTrackRecommendationsById(@PathVariable Integer id) {
        Optional<TrackRecommendations> returnVal = repo.findById(id);
        if(returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new IllegalArgumentException("Track Recommendations ID is not present.");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendations createTrackRecommendation(@RequestBody TrackRecommendations trackRecommendations) {
        return repo.save(trackRecommendations);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendations(@RequestBody TrackRecommendations trackRecommendations) {
        if (trackRecommendations.getId() != null) {
            repo.save(trackRecommendations);
        } else {
            throw new IllegalArgumentException("Track Recommendations ID is not present.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendations(@PathVariable Integer id) {
        repo.deleteById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendations> getAllTrackRecommendations() {
        return repo.findAll();
    }
}
