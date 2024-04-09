package com.hotelservices.hotelservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hotelservices.hotelservice.entities.Hotel;
public interface Hotelrepo extends JpaRepository<Hotel ,String> {

}
