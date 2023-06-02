package com.myblog1.controller;


import com.myblog1.payload.CommentDto;
import com.myblog1.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

  private  CommentService commentService;
  public CommentController(CommentService commentService){

      this.commentService = commentService;
  }

    //http://localhost:8080/api/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComments(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.createComment(postId, commentDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);


    }


    //http://localhost:8080/api/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")

    public List<CommentDto> getAllComments(@PathVariable("postId") long postId){
       List<CommentDto> dto =commentService.getAllComment(postId);
       return dto;

    }

    //http://localhost:8080/api/posts/{postId}/comments/{commentId}
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId){
        CommentDto dto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    //http://localhost:8080/api/posts/{postId}/comments/{commentId}
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId, @RequestBody CommentDto commentDto){
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    //http://localhost:8080/api/posts/{postId}/comments/{commentId}
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
     public ResponseEntity<String> deleteCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId)
    {
       commentService.deleteCommentById(postId, commentId);
       return new ResponseEntity<>("Comment Deleted", HttpStatus.OK);
    }


}
