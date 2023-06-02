package com.myblog1.service.Impl;

import com.myblog1.entities.Post;
import com.myblog1.exception.ResourceNotFoundException;
import com.myblog1.payload.PostDto;
import com.myblog1.payload.PostResponse;
import com.myblog1.repositories.PostRepository;
import com.myblog1.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper; 
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper){

        this.postRepository= postRepository;
        this.modelMapper = modelMapper;
        
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //covert to Entity
        Post post = mapToEntity(postDto);
        
        Post savedPost = postRepository.save(post);  // return entity object
        //mapToDto
        PostDto dto = mapToDto(savedPost);

        return dto;
    }


    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
    // using ternary operator ?  sortDir will check either the value is ascending or descending with comparison of (Sort.Direction.ASC.name())
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();//? condition1(if true) :condition 2(if false)



        // create Pageable instance

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
            // will get the data in form of page and we store it in variable content
        Page<Post> content = postRepository.findAll(pageable);
         // changing the page data into List of data
        List<Post> posts = content.getContent();

        // For returning the object in the form of List we have to use the Stream API which collects the data and shows it
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());


        // We can use above 2 line as /  return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        //content has all the method to perform operations

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);              // this will show all the contents in postman
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalElements(content.getTotalElements());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setLast(content.isLast());
 // method response type has been changed because of postResponse
        return postResponse;

    }

    @Override
    public PostDto findById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + id));  //lammda expression used here
            // above line will work as if, else statement like if the id found than the id will store in "post" and if
            // if not recived the id it will throw custome exception msg
        PostDto postDto = mapToDto(post);
        return postDto;

    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not found with id:" + id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post Not found with id:" + id));
        postRepository.deleteById(id);
    }


    //creating method to covent DTO object to entity
  Post mapToEntity(PostDto postDto){
      Post post = new Post();
      post.setTitle(postDto.getTitle());
      post.setContent(postDto.getContent());
      post.setDescription(postDto.getDescription());
      return post;
   }

    //creating method to covent Entity 
    
   PostDto mapToDto(Post post){
       PostDto dto = new PostDto();
       dto.setId(post.getId());
       dto.setTitle(post.getTitle());
       dto.setContent(post.getContent());
       dto.setDescription(post.getDescription());
       
       return dto;
    }
    
}
