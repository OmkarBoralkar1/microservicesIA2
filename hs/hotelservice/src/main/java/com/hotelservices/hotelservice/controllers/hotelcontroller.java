package com.hotelservices.hotelservice.controllers;

import com.hotelservices.hotelservice.entities.Hotel;
import com.hotelservices.hotelservice.services.Hotelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.core.io.UrlResource;
// import org.springframework.core.io.Resource;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import java.net.MalformedURLException;
// import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class hotelcontroller {

    @Autowired
    private Hotelservice hotelservice;

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelservice.create(hotel));
    }

    @GetMapping("/{hotelid}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelid) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelservice.get(hotelid));
    }

    @GetMapping
    public ResponseEntity<List<Hotel>> getAll() {
        return ResponseEntity.ok(hotelservice.getAll());
    }

    @PutMapping("/{hotelid}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String hotelid, @RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.OK).body(hotelservice.update(hotelid, hotel));
    }

    @DeleteMapping("/{hotelid}")
    public ResponseEntity<Void> deleteHotel(@PathVariable String hotelid) {
        hotelservice.delete(hotelid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // @GetMapping("/{hotelid}/image")
    // public ResponseEntity<Resource> getHotelImage(@PathVariable String hotelid) {
    //     Hotel hotel = hotelservice.get(hotelid);
    //     Resource file = loadAsResource(hotel.getImagePath());
    //     return ResponseEntity.ok().body(file);
    // }

    // private Resource loadAsResource(String imagePath) {
    //     try {
    //         Path filePath = Paths.get(imagePath).normalize();
    //         Resource resource = new UrlResource(filePath.toUri());
    //         if (resource.exists()) {
    //             return resource;
    //         } else {
    //             throw new MyFileNotFoundException("File not found " + imagePath);
    //         }
    //     } catch (MalformedURLException ex) {
    //         throw new MyFileNotFoundException("File not found " + imagePath, ex);
    //     }
    // }

    // Other methods...
}