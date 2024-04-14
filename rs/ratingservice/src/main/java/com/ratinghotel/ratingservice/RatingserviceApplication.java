package com.ratinghotel.ratingservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;

import com.ratinghotel.ratingservice.entities.Rating;
import com.ratinghotel.ratingservice.services.Ratingservice;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
@RequestMapping("/")

public class RatingserviceApplication {

	@Autowired
	private Ratingservice ratingService;

	@Autowired
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(RatingserviceApplication.class, args);
	}

	@GetMapping("/rate")
	public String getRatingsByHotelId(@RequestParam("hotelId") String hotelId, @RequestParam("userId") String userId, @RequestParam("username") String username, Model model) {
		// Assuming RatingService has a method to fetch ratings by hotelId
		System.out.println("rating service  router hit");
		List<Rating> ratings = ratingService.getRatingBYhotelid(hotelId);
		model.addAttribute("ratings", ratings);
		model.addAttribute("userId", userId);
		model.addAttribute("username", username);
		model.addAttribute("hotelid", hotelId);
		System.out.println("the userid got is"+userId+"name"+username+"hotelid"+hotelId);
		
		model.addAttribute("rating", ratings);
		return "rating"; // This is the name of the Thymeleaf template
	}

}
