package com.ratinghotel.ratingservice.services.impl;

import com.ratinghotel.ratingservice.entities.Rating;
import com.ratinghotel.ratingservice.repository.Ratingrepo;
import com.ratinghotel.ratingservice.services.Ratingservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ratingserviceimpl implements Ratingservice {

    @Autowired
    private Ratingrepo repository;

    @Override
    public Rating create(Rating rating) {
        return repository.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return repository.findAll();
    }

    @Override
    public List<Rating> getRatingBYuserid(String userid) {
        return repository.findByUserid(userid);
    }

    @Override
    public List<Rating> getRatingBYhotelid(String hotelid) {
        return repository.findByHotelid(hotelid);
    }

    @Override
    public Rating updateRating(String ratingid, Rating updatedRating) {
        return repository.findById(ratingid)
                .map(rating -> {
                    rating.setUserid(updatedRating.getUserid()); // Corrected here
                    rating.setHotelid(updatedRating.getHotelid()); // Corrected here
                    rating.setRating(updatedRating.getRating());
                    return repository.save(rating);
                })
                .orElseGet(() -> {
                    updatedRating.setRatingid(ratingid);
                    return repository.save(updatedRating);
                });
    }

    @Override
    public void deleteRating(String ratingid) {
        repository.deleteById(ratingid);
    }
}