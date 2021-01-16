package com.DongLee99.book.springboot.service;

import com.DongLee99.book.springboot.domain.posts.Posts;
import com.DongLee99.book.springboot.domain.posts.PostsRepository;
import com.DongLee99.book.springboot.web.dto.PostsListResponseDto;
import com.DongLee99.book.springboot.web.dto.PostsResponseDto;
import com.DongLee99.book.springboot.web.dto.PostsSaveRequestDto;
import com.DongLee99.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntitiy()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id = " +id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
        postsRepository.delete(posts);
    }

    public PostsResponseDto findById( Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id = " +id));

        return new PostsResponseDto(entity);
    }
}
