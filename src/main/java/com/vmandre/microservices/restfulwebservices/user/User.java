package com.vmandre.microservices.restfulwebservices.user;

import com.vmandre.microservices.restfulwebservices.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class User {

    @Id
    private Integer id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Past(message = "Birth date should be in the past")
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
