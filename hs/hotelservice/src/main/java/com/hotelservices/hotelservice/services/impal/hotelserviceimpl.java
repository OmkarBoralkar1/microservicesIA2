package com.hotelservices.hotelservice.services.impal;

import com.hotelservices.hotelservice.entities.Hotel;
import com.hotelservices.hotelservice.exceptions.ResourceNotFoundException;
import com.hotelservices.hotelservice.repository.Hotelrepo;
import com.hotelservices.hotelservice.services.Hotelservice;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 // Hotelserviceimpl.java
 import org.springframework.data.domain.Example;
 import org.springframework.data.domain.ExampleMatcher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.nio.file.Path;


@Service
public class hotelserviceimpl implements Hotelservice {

    @Autowired
    private Hotelrepo hotelRepository;

    private List<Hotel> hotels = new ArrayList<>();

    @Override
    public Hotel create(Hotel hotel) {
        String hotelid = UUID.randomUUID().toString();
        hotel.setHotelid(hotelid);
        hotels.add(hotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public Path loadHotelVideo(String hotelId) {
        // Retrieve the hotel entity using the hotelId
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));

        // Get the hotelvideo from the hotel entity
        MultipartFile hotelVideo = hotel.getHotelvideo();

        // Check if the hotelvideo is null
        if (hotelVideo == null || hotelVideo.isEmpty()) {
            throw new ResourceNotFoundException("Hotel video not found for id " + hotelId);
        }

        // Get the original filename of the hotelvideo
        String filename = hotelVideo.getOriginalFilename();

        // Check if filename is null (unlikely but a safe check)
        if (filename == null) {
            throw new ResourceNotFoundException("Original filename not found for hotel video with id " + hotelId);
        }

        // Construct the file path
        return Paths.get("uploads", filename);
    }

    @Override
    public void saveHotelVideo(MultipartFile file, Hotel hotel) throws IOException {
        Path path = Paths.get("uploads/" + "/" + file.getOriginalFilename());
        Files.createDirectories(path.getParent()); // Ensure the directory exists
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        hotel.setHotelVideoPath(file.getOriginalFilename());
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
            existingHotel.setHoteldate(hotel.getHoteldate());
            existingHotel.setHotelname(hotel.getHotelname());
            existingHotel.setHotellocation(hotel.getHotellocation());
            existingHotel.setAbout(hotel.getAbout());
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

   

    @Override
    public List<Hotel> searchHotels(String query) {
        // Create an example hotel with the query string in the name, location, and about fields
        Hotel exampleHotel = new Hotel();
        exampleHotel.setHotelname(query);
        exampleHotel.setHotellocation(query);
        exampleHotel.setAbout(query);
    
        // Create an ExampleMatcher to configure how the query should be matched
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // Use CONTAINING to match any part of the string
                .withIgnoreCase() // Ignore case for the match
                .withIgnorePaths("hotelid"); // Ignore ID field
    
        // Create an Example from the exampleHotel and matcher
        Example<Hotel> example = Example.of(exampleHotel, matcher);
    
        // Use the example to find all matching hotels
        List<Hotel> results = hotelRepository.findAll(example);
    
        // Print debug information
        System.out.println("Generated SQL query: " + example.toString());
        System.out.println("Number of hotels found: " + results.size());
        return results;
    }

}