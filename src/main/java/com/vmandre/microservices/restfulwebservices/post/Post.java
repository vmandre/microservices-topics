package com.vmandre.microservices.restfulwebservices.post;

import com.vmandre.microservices.restfulwebservices.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {

    @Id
    private Integer id;
    private Date date;
    @ManyToOne
    private User user;
}
