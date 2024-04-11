package com.ratinghotel.ratingservice.controller;

import com.ratinghotel.ratingservice.entities.Rating;
import com.ratinghotel.ratingservice.services.Ratingservice; // Corrected here
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class Ratingcontroller { // Corrected here

    @Autowired
    private Ratingservice ratingService; // Corrected here

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating) {
        Rating createdRating = ratingService.create(rating);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getRatings();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userid) {
        List<Rating> ratings = ratingService.getRatingBYuserid(userid);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelid}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelid) {
        List<Rating> ratings = ratingService.getRatingBYhotelid(hotelid);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @PutMapping("/{ratingid}")
    public ResponseEntity<Rating> updateRating(@PathVariable String ratingid, @RequestBody Rating updatedRating) {
        Rating rating = ratingService.updateRating(ratingid, updatedRating);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @DeleteMapping("/{ratingid}")
    public ResponseEntity<Void> deleteRating(@PathVariable String ratingid) {
        ratingService.deleteRating(ratingid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}