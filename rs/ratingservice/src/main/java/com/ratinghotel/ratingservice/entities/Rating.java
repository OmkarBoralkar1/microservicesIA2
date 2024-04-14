package com.ratinghotel.ratingservice.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document; // Import the Document annotation

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("user_ratings") // Use the Document annotation
public class Rating {
    @Id
    private String ratingid;
    private String userid;
    private String username;
    private String hotelid;
    private String rating;
    private String feedback;
}