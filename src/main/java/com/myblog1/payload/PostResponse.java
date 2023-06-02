package com.myblog1.payload;


import lombok.Data;

import java.util.List;

@Data
// this class perform task related pagination like on postman it shows tha how much elments we have for pagination
public class PostResponse {

    private List<PostDto> content;            //content has all the posts into in it
    private int pageSize;
    private int pageNo;
    private long totalElements;
    private  int totalPages;
    private  boolean isLast;          // it will give the answer true or false
}
