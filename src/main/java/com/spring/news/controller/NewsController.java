package com.spring.news.controller;


import com.spring.news.DTO.NewsViewResponseDTO;
import com.spring.news.entity.News;
import com.spring.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // 컨트롤러 레이어는 테스트 코드 작성하기


    // 뉴스 목록 조회 - GET - /
    @GetMapping
    public ResponseEntity<List<NewsViewResponseDTO>> findAllNews() {
        List<NewsViewResponseDTO> news = newsService.getAllNews()
                .stream()
                .map(NewsViewResponseDTO::new)
                .toList();

        return ResponseEntity.ok(news);
    }


    // 개별 뉴스 조회 - GET - /{newsId}
    @GetMapping("/{newsId}")
    public RedirectView redirectToNewsSite(@PathVariable long newsId) {
        News news = newsService.getNewsById(newsId);
        String newsUrl = news.getNewsUrl();

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(newsUrl);

        return redirectView;
    }


    // ID로 뉴스 삭제  - DELETE - /{newsId}
    @DeleteMapping("/{newsId}")
    public ResponseEntity<String> deleteNewsById(@PathVariable long newsId) {
        newsService.deleteNewsById(newsId);

        return ResponseEntity.ok("뉴스가 삭제되었습니다");
    }


    // 검색 - Get - /"search"
    @GetMapping("/search")
    public ResponseEntity<List<NewsViewResponseDTO>> searchNews(@RequestParam String keyword) {
        List<News> searchResults = newsService.searchNewsByKeyword(keyword);

        List<NewsViewResponseDTO> searchResultsDTO = searchResults.stream()
                .map(NewsViewResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(searchResultsDTO);
    }

}
