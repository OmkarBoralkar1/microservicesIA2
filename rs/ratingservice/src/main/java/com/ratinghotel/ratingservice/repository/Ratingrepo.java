package com.ratinghotel.ratingservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.ratinghotel.ratingservice.entities.Rating;
import java.util.*;


public interface Ratingrepo extends MongoRepository<Rating,String> {

//custom finder methds 


List<Rating> findByUserid(String userid);
List<Rating> findByHotelid(String hotelid);




}
