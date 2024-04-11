package com.userservice.userservice.services.externalservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "HOTELSERVICE")
public interface useerHotelservice {
 
     @GetMapping("/home")
     String redirectToHome( @RequestParam("userId") String userId, @RequestParam("username") String username);



}
