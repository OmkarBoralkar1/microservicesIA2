package com.hotelservices.hotelservice.services;
import com.hotelservices.hotelservice.entities.Hotel;
import java.util.*;
public interface Hotelservice {
    //create 
 Hotel create(Hotel hotel);

 //get all

 List<Hotel> getAll();

 //get single hotel 

 Hotel get(String hotelid);

 Hotel update(String hotelid, Hotel hotel);


 void delete(String hotelid);

}
