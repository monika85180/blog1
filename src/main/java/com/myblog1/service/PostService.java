package com.myblog1.service;

import com.myblog1.entities.Post;
import com.myblog1.payload.PostDto;
import com.myblog1.payload.PostResponse;

import java.util.List;

public interface PostService {
     PostDto createPost(PostDto postDto);           // we create an interface which return us a PostDto object

     PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

     PostDto findById(long id);

     PostDto updatePost(PostDto postDto, long id);

     void deletePostById(long id);
}
