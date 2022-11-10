package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.model.Track;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    public void shouldGetDeleteTrack() {
        Label label = new Label();
        label.setName("Roadrunner Records");
        label.setWebsite("https://www.elektramusicgroup.com/roadrunnerrecords");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Trivium");
        artist.setInstagram("@triviumband");
        artist.setTwitter("@triviumbandontwitter");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Ascendancy");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2005, 03, 06));
        album.setListPrice(10.99);
        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("Like Light to the Flies");
        track.setAlbumId(album.getId());
        track.setRunTime(540);
        track = trackRepository.save(track);

        Optional<Track> track1 = trackRepository.findById(track.getId());
        assertEquals(track1.get(), track);

        trackRepository.deleteById(track.getId());
        track1 = trackRepository.findById(track.getId());
        assertFalse(track1.isPresent());
    }

    @Test
    public void shouldUpdateTrack() {
        Label label = new Label();
        label.setName("Roadrunner Records");
        label.setWebsite("https://www.elektramusicgroup.com/roadrunnerrecords");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Trivium");
        artist.setInstagram("@triviumband");
        artist.setTwitter("@triviumbandontwitter");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Ascendancy");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2005, 03, 06));
        album.setListPrice(10.99);
        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("Like Light to the Flies");
        track.setAlbumId(album.getId());
        track.setRunTime(540);
        track = trackRepository.save(track);

        track.setTitle("Rain");
        track.setRunTime(411);
        track.setAlbumId(album.getId());
        trackRepository.save(track);

        Optional<Track> track1 = trackRepository.findById(track.getId());
        assertEquals(track1.get(), track);
    }

    @Test
    public void shouldGetAllTracks() {
        Label label = new Label();
        label.setName("Roadrunner Records");
        label.setWebsite("https://www.elektramusicgroup.com/roadrunnerrecords");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Trivium");
        artist.setInstagram("@triviumband");
        artist.setTwitter("@triviumbandontwitter");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Ascendancy");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2005, 03, 06));
        album.setListPrice(10.99);
        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("Like Light to the Flies");
        track.setAlbumId(album.getId());
        track.setRunTime(540);
        track = trackRepository.save(track);

        List<Track> trackList = trackRepository.findAll();
        assertEquals(trackList.size(), 1);
    }

    @After //help from Kevin(Zhong) for the cascading tests problem.
    public void breakDown() throws Exception {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        artistRepository.deleteAll();
    }
}