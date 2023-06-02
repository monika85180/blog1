package com.myblog1.controller;

import com.myblog1.payload.PostDto;
import com.myblog1.payload.PostResponse;
import com.myblog1.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){    // here we taking the data from postman via using postDto object and @RequestBody is use for taking the data from postman
        PostDto dto = postService.createPost(postDto);         // for show the value of Saved post we need to assign it to Dto Variable
        return new ResponseEntity<>(dto, HttpStatus.CREATED);   // createdStatus will give 201 status code

    }

    //http://localhost:8080/api/posts?pageNo=1&pageSize=5&sortBy=id&sortDir=desc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(name="pageNo", defaultValue="0", required=false) int pageNo,
             @RequestParam(name="pageSize", defaultValue = "5", required = true) int pageSize ,
            @RequestParam(name="sortBy", defaultValue = "id" , required = false) String sortBy,
            @RequestParam(name="sortDir", defaultValue="asc" , required = false) String sortDir)

    {
        PostResponse postResponse = postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
    }
    //http://localhost:8080/api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){     // we use ResponseEntity<> , because we have to return status code and some messages on postman after done the CRUD and more operations
        PostDto dto = postService.findById(id);                                  // The @PathVariable annotation is used to extract the value from the URL.
        return new ResponseEntity<>(dto, HttpStatus.OK);                        // OK will give 200 status code
    }

    //http://localhost:8080/api/posts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(postDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post Deleted", HttpStatus.OK);
    }
}
