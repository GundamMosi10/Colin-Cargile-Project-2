package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.model.AlbumRecommendations;
import com.company.musicstorerecommendations.repository.AlbumRecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumRecommendations")
public class AlbumRecommendationsController {

    @Autowired
    AlbumRecommendationsRepository repo;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendations getAlbumRecommendationsById(@PathVariable Integer id) {
        Optional<AlbumRecommendations> returnVal = repo.findById(id);
        if(returnVal.isPresent()) {
            return returnVal.get();
        } else {
            throw new IllegalArgumentException("AlbumRecommendations ID is not present.");
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendations createAlbumRecommendation(@RequestBody AlbumRecommendations albumRecommendations) {
        return repo.save(albumRecommendations);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendations(@RequestBody AlbumRecommendations albumRecommendations) {
        if(albumRecommendations.getId() != null) {
            repo.save(albumRecommendations);
        } else {
            throw new IllegalArgumentException("Album Recommendations ID is not present.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendations(@PathVariable Integer id) {
        repo.deleteById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendations> getAllAlbumRecommendations() {
        return repo.findAll();
    }
}
