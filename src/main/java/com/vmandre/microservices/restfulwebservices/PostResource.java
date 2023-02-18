package com.vmandre.microservices.restfulwebservices;

import com.vmandre.microservices.restfulwebservices.exception.PostNotFoundException;
import com.vmandre.microservices.restfulwebservices.post.Post;
import com.vmandre.microservices.restfulwebservices.post.PostRepository;
import com.vmandre.microservices.restfulwebservices.user.User;
import com.vmandre.microservices.restfulwebservices.user.UserNotFoundException;
import com.vmandre.microservices.restfulwebservices.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class PostResource {

    private UserRepository userRepository;
    private PostRepository postRepository;

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllUserPosts(@PathVariable Integer userId) {
        User user = searchUser(userId);
        return postRepository.findByUser(user);
    }

    @GetMapping("/users/{id}/posts/{id}")
    public Post retrievepost(@PathVariable Integer userId, @PathVariable Integer postId) {
        searchUser(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        }

        throw new PostNotFoundException(String.format("Id %s not found", postId));
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable Integer userId, @RequestBody Post post) {
        User user = searchUser(userId);
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        URI location = buildLocation(savedPost.getId());
        return ResponseEntity.created(location).build();
    }

    private User searchUser(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new UserNotFoundException(String.format("User id %s not found", userId));
    }

    private URI buildLocation(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
