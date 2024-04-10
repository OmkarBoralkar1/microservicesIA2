package com.hotelservices.hotelservice.entities;



// import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name ="hotels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hotel {
    @Id
    private String hotelid;
    @Transient
    private MultipartFile hotelvideo;
    private String hotelVideoPath;
    private String Userid;
    private String hotelname;
    private String hoteldate;
    private String hotellocation;
    private String about;

}


