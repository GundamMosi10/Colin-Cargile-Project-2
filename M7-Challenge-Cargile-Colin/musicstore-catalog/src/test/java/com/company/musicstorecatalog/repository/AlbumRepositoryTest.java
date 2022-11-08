//package com.company.musicstorecatalog.repository;
//
//import com.company.musicstorecatalog.model.Album;
//import com.company.musicstorecatalog.model.Artist;
//import com.company.musicstorecatalog.model.Label;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class AlbumRepositoryTest {
//
//    @Autowired
//    AlbumRepository albumRepository;
//    @Autowired
//    ArtistRepository artistRepository;
//    @Autowired
//    LabelRepository labelRepository;
//
////    private Album album;
//
//    @Before
//    public void setUp() throws Exception {
//        artistRepository.deleteAll();
//        labelRepository.deleteAll();
//        albumRepository.deleteAll();
//    }
//
//    @Test
//    public void addGetDeleteAlbum() {
//        Artist artist = new Artist();
//        artist.setName("Trivium");
//        artist.setInstagram("@triviumband");
//        artist.setTwitter("@triviumbandontwitter");
//        artistRepository.save(artist);
//
//        Label label = new Label();
//        label.setName("Roadrunner Records");
//        label.setWebsite("https://www.elektramusicgroup.com/roadrunnerrecords");
//        labelRepository.save(label);
//
//        Album album = new Album();
//        album.setTitle("Ascendancy");
//        album.setArtistId(artist.getId());
//        album.setReleaseDate(LocalDate.of(2005,03,06));
//        album.setLabelId(label.getId());
//        album.setListPrice(10.99);
//
//        Optional<Album> album1 = albumRepository.findById(album.getId()); //how is this ID invaid/null?
//        assertEquals(album1.get(), album);
//
//        albumRepository.deleteById(album.getId());
//        album1 = albumRepository.findById(album.getId());
//        assertFalse(album1.isPresent());
//    }
//}