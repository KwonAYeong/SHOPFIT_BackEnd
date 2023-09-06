package com.spring.community.controller;

import com.spring.community.DTO.LikeRequestDTO;
import com.spring.community.DTO.LikeResponseDTO;
import com.spring.community.service.LikeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class LikeController {

    private final LikeService likeService;


    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("like/add")
    public ResponseEntity<String> pushLike(@RequestBody LikeRequestDTO likeRequestDTO){
        likeService.saveLike(likeRequestDTO); //받아야 할 정보 : 글주인 nickname과 postId, 좋아요 누른사람 userId
        return ResponseEntity.ok("좋아요 누르기 성공");
    }

    @PostMapping("like/delete")
    public ResponseEntity<String> cancelLike(@RequestBody LikeRequestDTO likeRequestDTO){
        likeService.deleteLike(likeRequestDTO);
        return ResponseEntity.ok("좋아요 취소 성공");
    }

    @PostMapping("/like")
    public ResponseEntity<LikeResponseDTO> findLike(@RequestBody LikeRequestDTO likeRequestDTO){
        int isLiked = likeService.isLiked(likeRequestDTO);

        LikeResponseDTO likeInfo = new LikeResponseDTO();

        likeInfo.setLikeCnt(likeRequestDTO.getPostId());
        likeInfo.setIsLiked(isLiked);

        return ResponseEntity.ok(likeInfo);
    }




}
