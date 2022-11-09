package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.ArtistRecommendations;
import com.company.musicstorerecommendations.repository.ArtistRecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistRecommendations")
public class ArtistRecommendationsController {

    @Autowired
    ArtistRecommendationsRepository repo;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendations getArtistRecommendationsById(@PathVariable Integer id) {
        Optional<ArtistRecommendations> returnVal = repo.findById(id);
        if(returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new IllegalArgumentException("Artist Recommendations ID is not present.");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendations createArtistRecommendation(@RequestBody ArtistRecommendations artistRecommendations) {
        return repo.save(artistRecommendations);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendations(@RequestBody ArtistRecommendations artistRecommendations) {
        if(artistRecommendations.getId() != null) {
            repo.save(artistRecommendations);
        } else {
            throw new IllegalArgumentException("Artist Recommendations ID is not present.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendations(@PathVariable Integer id) {
        repo.deleteById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendations> getAllArtistRecommendations() {
        return repo.findAll();
    }
}
