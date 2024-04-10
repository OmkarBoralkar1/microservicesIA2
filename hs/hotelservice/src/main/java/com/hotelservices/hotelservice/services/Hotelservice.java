package com.hotelservices.hotelservice.services;
import com.hotelservices.hotelservice.entities.Hotel;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
public interface Hotelservice {
    //create 
 Hotel create(Hotel hotel);

 //get all

 List<Hotel> getAll();

 //get single hotel 

 Hotel get(String hotelid);

 Hotel update(String hotelid, Hotel hotel);
 Path loadHotelVideo(String hotelId);
 void saveHotelVideo(MultipartFile file, Hotel hotel) throws IOException;
 void delete(String hotelid);
 
 

}
