package com.hotelservices.hotelservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotelservices.hotelservice.entities.Hotel;
import java.util.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface Hotelrepo extends JpaRepository<Hotel, String> {

    @Query("SELECT h FROM Hotel h WHERE " +
        "LOWER(h.hotelname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(h.hotellocation) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
        "LOWER(h.about) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Hotel> searchHotels(@Param("query") String query);


}