package com.hotelservices.hotelservice.services.impal;

import com.hotelservices.hotelservice.entities.Hotel;
import com.hotelservices.hotelservice.exceptions.ResourceNotFoundException;
import com.hotelservices.hotelservice.repository.Hotelrepo;
import com.hotelservices.hotelservice.services.Hotelservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class hotelserviceimpl implements Hotelservice {

    @Autowired
    private Hotelrepo hotelRepository;

    private List<Hotel> hotels = new ArrayList<>();

    @Override
    public Hotel create(Hotel hotel) {
        String hotelid= UUID.randomUUID().toString();
        hotel.setHotelid(hotelid);
        hotels.add(hotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String hotelid) {
        return hotelRepository.findById(hotelid)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel with the given id not found!"));
    }

    @Override
    public Hotel update(String hotelid, Hotel hotel) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelid);
        if (optionalHotel.isPresent()) {
            Hotel existingHotel = optionalHotel.get();
            existingHotel.setHotelname(hotel.getHotelname()); // Corrected method name
            existingHotel.setHotellocation(hotel.getHotellocation()); // Corrected method name
            existingHotel.setAbout(hotel.getAbout()); // Assuming this is correct based on the property name
            // Update other fields as necessary
            return hotelRepository.save(existingHotel);
        } else {
            throw new ResourceNotFoundException("Hotel with the given id not found!");
        }
    }

    @Override
    public void delete(String hotelid) {
        if (hotelRepository.existsById(hotelid)) {
            hotelRepository.deleteById(hotelid);
        } else {
            throw new ResourceNotFoundException("Hotel with the given id not found!");
        }
    }
}