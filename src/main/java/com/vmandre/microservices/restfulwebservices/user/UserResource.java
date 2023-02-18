package com.vmandre.microservices.restfulwebservices.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class UserResource {

    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Integer id) {
        User user = getUser(id);

        // HATEOAS
        EntityModel<User> model = EntityModel.of(user);
        Link linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users");
        model.add(linkTo);

        return model;
    }

    //    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/users")
    public void deleteUser(@PathVariable Integer id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    private User getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new UserNotFoundException(String.format("Id %s not found", id));
    }


}
