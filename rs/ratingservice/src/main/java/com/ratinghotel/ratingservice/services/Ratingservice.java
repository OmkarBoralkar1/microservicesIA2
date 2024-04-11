package com.ratinghotel.ratingservice.services;

import org.springframework.stereotype.Service;
import com.ratinghotel.ratingservice.entities.Rating;
import java.util.*;
@Service
public interface Ratingservice {

    //create

    Rating create(Rating Rating);



    //get all ratings

    List<Rating>getRatings();

    //get all by userid

    List<Rating> getRatingBYuserid(String userid);

    //get all by hotel

    List<Rating> getRatingBYhotelid(String hotelid);
    //update
    Rating updateRating(String ratingid, Rating updatedRating);

    //delete
    void deleteRating(String ratingid);


}
