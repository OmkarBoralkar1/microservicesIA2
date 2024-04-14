package com.hotelservices.hotelservice;

import com.hotelservices.hotelservice.entities.Hotel;
import com.hotelservices.hotelservice.filestorage.FileStorageException;
import com.hotelservices.hotelservice.services.Hotelservice;

import jakarta.ws.rs.GET;
import reactor.core.publisher.Mono;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
// import java.net.MalformedURLException;
import java.nio.file.Files;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
// import com.hotelservices.hotelservice.FileStorageException;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatus;

import org.springframework.web.servlet.view.RedirectView;

// import com.hotelservices.hotelservice.filestorage.FileStorageException;
@SpringBootApplication
@Controller
@EnableFeignClients
@RequestMapping("/")
@SessionAttributes({ "userId", "username" })
public class HotelserviceApplication {

	@Autowired
	private Hotelservice hotelService;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private WebClient.Builder webClientBuilder;

	public static void main(String[] args) {
		SpringApplication.run(HotelserviceApplication.class, args);
	}

	@GetMapping("/")
	public String getAllHotels(Model model) {
		System.out.println("hotel  post  router hit");
		String userId = (String) model.getAttribute("userId");
		String username = (String) model.getAttribute("username");
		System.out.println("the username and the  to go to the home page is" + username + "Id is" + userId);
		model.addAttribute("username", username);
		List<Hotel> hotels = hotelService.getAll();
		model.addAttribute("hotels", hotels);
		List<ServiceInstance> instances = discoveryClient.getInstances("USERSERVICE");
		if (!instances.isEmpty()) {
			ServiceInstance instance = instances.get(0);
			String userserviceUrl = instance.getUri().toString();
			String userserviceUrlWithLogin = userserviceUrl + "/login";
			System.out.println("the url got is" + userserviceUrlWithLogin);
			model.addAttribute("userserviceUrl", userserviceUrlWithLogin);
			String userserviceUrlWithprofile = String.format("%s/update/%s", userserviceUrl, userId);
			System.out.println("the url got is" + userserviceUrlWithprofile);
			model.addAttribute("userserviceprofileurl", userserviceUrlWithprofile);

		}
		return "home";
	}

	@GetMapping("/home")
	public String showHomePage(@RequestParam("userId") String userId, @RequestParam("username") String username,
			Model model) {
		// Retrieve userId and username from the session
		// String sessionUserId = (String) session.getAttribute("userId");
		// String sessionUsername = (String) session.getAttribute("username");

		// System.out.println("userId from session: " + sessionUserId);
		// System.out.println("username from session: " + sessionUsername);
		// Store userId and username in the session
		model.addAttribute("userId", userId);
		model.addAttribute("username", username);
		List<Hotel> hotels = hotelService.getAll();
		model.addAttribute("hotels", hotels);

		// Fetch userservice URL from Eureka
		List<ServiceInstance> instances = discoveryClient.getInstances("USERSERVICE");
		if (!instances.isEmpty()) {
			ServiceInstance instance = instances.get(0);
			String userserviceUrl = instance.getUri().toString();
			String userserviceUrlWithLogin = userserviceUrl + "/login";
			System.out.println("the url got is" + userserviceUrlWithLogin);
			model.addAttribute("userserviceUrl", userserviceUrlWithLogin);
			String userserviceUrlWithprofile = String.format("%s/update/%s", userserviceUrl, userId);
			System.out.println("the url got is" + userserviceUrlWithprofile);
			model.addAttribute("userserviceprofileurl", userserviceUrlWithprofile);
		}

		return "home"; // This should match the name of your HTML template file without the .html
						// extension
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
		String userId = (String) model.getAttribute("userId");
		String username = (String) model.getAttribute("username");
		System.out.println("the username and the  to go to the home page is" + username + "Id is" + userId);
		model.addAttribute("userId", userId);
		model.addAttribute("hotel", new Hotel());
		return "createhotel";
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

			// Redirect to the home page or any other appropriate page after successful
			// creation
			return "redirect:/";
		} catch (IOException e) {
			// Handle file storage exception
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
	public String deleteHotel(@PathVariable String hotelid) {
		System.out.println("delete post router hit");
		hotelService.delete(hotelid);
		return "redirect:/";
	}

	@GetMapping("/myvideos")
	public String getUserHotels(Model model) {
		System.out.println("hotel post router hit");
		String userId = (String) model.getAttribute("userId");
		String username = (String) model.getAttribute("username");
		System.out.println("the username and the to go to the home page is" + username + "Id is" + userId);
		model.addAttribute("username", username);

		// Assuming hotelService.getAll() returns a List<Hotel>
		// Modify this method to include a filter that only returns hotels associated
		// with the current user
		List<Hotel> hotels = hotelService.getAll().stream()
				.filter(hotel -> hotel.getUserid().equals(userId))
				.collect(Collectors.toList());

		model.addAttribute("hotels", hotels);

		List<ServiceInstance> instances = discoveryClient.getInstances("USERSERVICE");
		if (!instances.isEmpty()) {
			ServiceInstance instance = instances.get(0);
			String userserviceUrl = instance.getUri().toString();
			String userserviceUrlWithLogin = userserviceUrl + "/login";
			System.out.println("the url got is" + userserviceUrlWithLogin);
			model.addAttribute("userserviceUrl", userserviceUrlWithLogin);
			String userserviceUrlWithprofile = String.format("%s/update/%s", userserviceUrl, userId);
			System.out.println("the url got is" + userserviceUrlWithprofile);
			model.addAttribute("userserviceprofileurl", userserviceUrlWithprofile);
		}
		return "myvideos";
	}

	@GetMapping("/rate/{hotelid}")
    public ResponseEntity<RedirectView> rateHotel(@PathVariable String hotelid, Model model) {
        String userId = (String) model.getAttribute("userId");
        String username = (String) model.getAttribute("username");

        // Find the RatingService instance from Eureka
        List<ServiceInstance> instances = discoveryClient.getInstances("RATINGSERVICE");
        if (!instances.isEmpty()) {
            ServiceInstance instance = instances.get(0);
            String ratingservice = instance.getUri().toString();
            String ratingServiceUrl = ratingservice + "/rate?userId=" + userId + "&hotelId=" + hotelid + "&username=" + username;

            // Create a RedirectView instance
            RedirectView redirectView = new RedirectView(ratingServiceUrl);
            redirectView.setStatusCode(HttpStatus.FOUND); // Use HttpStatus.FOUND for a temporary redirect

            // Return the RedirectView wrapped in a ResponseEntity
			return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(ratingServiceUrl)).build();
        } else {
            // Handle the case where the RatingService is not found
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}