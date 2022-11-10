package com.company.musicstorecatalog.repository;

import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.model.Label;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRepositoryTest {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;


    @Before
    public void setUp() throws Exception {
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        artistRepository.deleteAll();
    }

    @Test
    public void addGetDeleteAlbum() {
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

        Optional<Album> album1 = albumRepository.findById(album.getId());

        assertEquals(album1.get(), album);

        albumRepository.deleteById(album.getId());

        album1 = albumRepository.findById(album.getId());

        assertFalse(album1.isPresent());

    }

    @Test
    public void shouldUpdateAlbum() {
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

        album.setTitle("Ascendancy-LIVE");
        album.setReleaseDate(LocalDate.of(2006, 3, 12));
        album.setListPrice(20.00);

        albumRepository.save(album);

        Optional<Album> album1 = albumRepository.findById(album.getId());
        assertEquals(album1.get(), album);
    }

    @Test
    public void shouldGetAllAlbums() {
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

        List<Album> albumList = albumRepository.findAll();
        assertEquals(albumList.size(), 1);
    }

    @After //help from Kevin(Zhong) for the cascading tests problem.
    public void breakDown() throws Exception {
        albumRepository.deleteAll();
        labelRepository.deleteAll();
        artistRepository.deleteAll();
    }
}
