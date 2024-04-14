package com.ratinghotel.ratingservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.ratinghotel.ratingservice.entities.Rating;
import com.ratinghotel.ratingservice.services.Ratingservice;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
@RequestMapping("/")
@EnableFeignClients
public class RatingserviceApplication {

	@Autowired
	private Ratingservice ratingService;

	@Autowired
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(RatingserviceApplication.class, args);
	}

	@GetMapping("/rate")
	public String getRatingsByHotelId(@RequestParam("hotelId") String hotelId, @RequestParam("userId") String userId,
			@RequestParam("username") String username, Model model) {
		// Assuming RatingService has a method to fetch ratings by hotelId
		System.out.println("rating service  router hit");
		List<Rating> ratings = ratingService.getRatingBYhotelid(hotelId);
		model.addAttribute("ratings", ratings);
		model.addAttribute("userId", userId);
		model.addAttribute("username", username);
		model.addAttribute("hotelid", hotelId);
		System.out.println("the userid got is" + userId + "name" + username + "hotelid" + hotelId);

		model.addAttribute("rating", ratings);
		List<ServiceInstance> instances = discoveryClient.getInstances("HOTELSERVICE");
		if (!instances.isEmpty()) {
			ServiceInstance instance = instances.get(0);
			String hotelserviceUrl = instance.getUri().toString();
			String backhotelserviceUrl = hotelserviceUrl  + "/home";
			System.out.println("the url got is" + backhotelserviceUrl);
			
			
			model.addAttribute("backhotelserviceUrl", backhotelserviceUrl);

		}
		return "rating"; // This is the name of the Thymeleaf template
	}
	@GetMapping("/")
	public String getRatingByHotelId(@RequestParam("hotelId") String hotelId, @RequestParam("userId") String userId,
			@RequestParam("username") String username, Model model) {
		// Assuming RatingService has a method to fetch ratings by hotelId
		System.out.println("rating / service  router hit");
		List<Rating> ratings = ratingService.getRatingBYhotelid(hotelId);
		model.addAttribute("ratings", ratings);
		model.addAttribute("userId", userId);
		model.addAttribute("username", username);
		model.addAttribute("hotelid", hotelId);
		System.out.println("the userid got is" + userId + "name" + username + "hotelid" + hotelId);

		model.addAttribute("rating", ratings);
		List<ServiceInstance> instances = discoveryClient.getInstances("HOTELSERVICE");
		if (!instances.isEmpty()) {
			ServiceInstance instance = instances.get(0);
			String hotelserviceUrl = instance.getUri().toString();
			String backhotelserviceUrl = hotelserviceUrl  + "/home";
			System.out.println("the url got is" + backhotelserviceUrl);
			
			
			model.addAttribute("backhotelserviceUrl", backhotelserviceUrl);

		}
		return "rating"; // This is the name of the Thymeleaf template
	}

	@GetMapping("/addrating")
	public String addRatingsByHotelId(@RequestParam("hotelId") String hotelId, @RequestParam("userId") String userId,
			@RequestParam("username") String username, Model model) {
		// Assuming RatingService has a method to fetch ratings by hotelId
		System.out.println("rating service router hit");
		model.addAttribute("userId", userId);
		model.addAttribute("username", username);
		model.addAttribute("hotelid", hotelId);
		System.out.println("the userid got is" + userId + "name" + username + "hotelid" + hotelId);

		// Add a new Rating object to the model under the name 'rating'
		model.addAttribute("rating", new Rating());

		return "add"; // This is the name of the Thymeleaf template
	}

	@PostMapping("/add")
public String addRating(@ModelAttribute Rating rating, RedirectAttributes redirectAttributes) {
    System.out.println("rating post service router hit");
    Rating savedRating = ratingService.create(rating);
    // Assuming you want to pass the savedRating to the redirected view
    redirectAttributes.addFlashAttribute("savedRating", savedRating);
    // Correctly construct the redirect URL with the necessary parameters
    return "redirect:/?hotelId=" + savedRating.getHotelid() + "&userId=" + savedRating.getUserid() + "&username=" + savedRating.getUsername();
}

}
