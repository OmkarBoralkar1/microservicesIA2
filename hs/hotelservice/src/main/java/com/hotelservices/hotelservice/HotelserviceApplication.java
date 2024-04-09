package com.hotelservices.hotelservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import com.hotelservices.hotelservice.entities.Hotel;
import com.hotelservices.hotelservice.services.Hotelservice;
import java.util.*;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class HotelserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelserviceApplication.class, args);
	}

	@Autowired
	private Hotelservice hotelService;

	@GetMapping("/")
	public String getAllHotels(Model model) {
		System.out.println("get hotel hit");
		List<Hotel> hotels = hotelService.getAll();
		model.addAttribute("hotels", hotels);
		return "home"; // This should match the name of your HTML file without the .html extension
	}

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		System.out.println("create router hit");
		model.addAttribute("hotel", new Hotel());
		return "create";
	}

	@PostMapping("/create")
	public String createHotel(@ModelAttribute Hotel hotel) {
		hotelService.create(hotel);
		return "redirect:/";
	}

	@GetMapping("/update/{hotelid}")
	public String showUpdateForm(@PathVariable String hotelid, Model model) {
		System.out.println("update get hit");
		Hotel hotel = hotelService.get(hotelid);
		model.addAttribute("hotel", hotel);
		return "update";
	}

	@PostMapping("/update/{hotelid}")
	public String updateHotel(@PathVariable String hotelid, @ModelAttribute Hotel hotel) {
		System.out.println("update post hit");
		hotelService.update(hotelid, hotel);
		return "redirect:/";
	}

	@GetMapping("/delete/{hotelid}")
	public String showDeleteConfirmation(@PathVariable String hotelid, Model model) {
		System.out.println("delete get hit");
		Hotel hotel = hotelService.get(hotelid);
		model.addAttribute("hotel", hotel);
		return "delete"; // This should match the name of your HTML file for the delete confirmation page
	}

	@PostMapping("/delete/{hotelid}")
	public String deleteHotel(@PathVariable String hotelid) {
		System.out.println("delete post hit");
		hotelService.delete(hotelid);
		return "redirect:/"; // Redirects to the main page after deletion
	}

}
