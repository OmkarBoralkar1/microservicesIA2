package com.hotelservices.hotelservice;

import com.hotelservices.hotelservice.entities.Hotel;
import com.hotelservices.hotelservice.filestorage.FileStorageException;
import com.hotelservices.hotelservice.services.Hotelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.core.io.Resource;
// import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
// import java.net.MalformedURLException;
import java.nio.file.Files;
// 
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
// import com.hotelservices.hotelservice.FileStorageException;

// import com.hotelservices.hotelservice.filestorage.FileStorageException;
@SpringBootApplication
@Controller
@RequestMapping("/")
public class HotelserviceApplication {

	@Autowired
	private Hotelservice hotelService;

	public static void main(String[] args) {
		SpringApplication.run(HotelserviceApplication.class, args);
	}

	@GetMapping("/")
	public String getAllHotels(Model model) {
		System.out.println("hotel  post  router hit");
		List<Hotel> hotels = hotelService.getAll();
		model.addAttribute("hotels", hotels);
		return "home";
	}

	@GetMapping("/videos/{filename:.+}")
	public ResponseEntity<StreamingResponseBody> serveVideo(@PathVariable String filename) throws IOException {
		Path filePath = Paths.get("uploads", filename);
		if (!Files.exists(filePath)) {
			throw new FileNotFoundException("File not found " + filename);
		}

		StreamingResponseBody responseBody = outputStream -> {
			try (var inputStream = Files.newInputStream(filePath)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}
		};

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(responseBody);
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		System.out.println("create get router hit");
		model.addAttribute("hotel", new Hotel());
		return "create";
	}

	// HotelserviceApplication.java

	// HotelserviceApplication.java

	@PostMapping("/create")
	public String createHotel(@ModelAttribute Hotel hotel) {
		System.out.println("create post router hit");
		try {
			// Assuming you have a method to handle the video upload and update the hotel
			// entity
			hotelService.saveHotelVideo(hotel.getHotelvideo(), hotel);

			// Save the hotel entity
			hotelService.create(hotel);

			//Redirect to the home page or any other appropriate page after successful
			//creation
			return "redirect:/";
		} 
		catch (IOException e) {
			//Handle file storage exception
			throw new FileStorageException(
					"Could not store file for hotel " + hotel.getHotelid() + ". Please try again!", e);
		}
	}

	@GetMapping("/update/{hotelid}")
	public String showUpdateForm(@PathVariable String hotelid, Model model) {
		Hotel hotel = hotelService.get(hotelid);
		model.addAttribute("hotel", hotel);
		return "update";
	}

	@PostMapping("/update/{hotelid}")
	public String updateHotel(@PathVariable String hotelid, @ModelAttribute Hotel hotel) {
		System.out.println("Hotel name: " + hotel.getHotelname());
		System.out.println("Hotel location: " + hotel.getHotellocation());
		System.out.println("Hotel about: " + hotel.getAbout());
		hotelService.update(hotelid, hotel);
		return "redirect:/";
	}

	@GetMapping("/delete/{hotelid}")
	public String showDeleteConfirmation(@PathVariable String hotelid, Model model) {
		System.out.println("delete get router hit");
		Hotel hotel = hotelService.get(hotelid);
		model.addAttribute("hotel", hotel);
		return "delete";
	}

	@PostMapping("/delete/{hotelid}")
	public String deleteHotel(@PathVariable String hotelid) 
	{
		System.out.println("delete post router hit");
		hotelService.delete(hotelid);
		return "redirect:/";
	}
}