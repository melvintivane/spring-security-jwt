package tech.melvin.springsecurity6.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.melvin.springsecurity6.controller.dto.CreateTweetDTO;
import tech.melvin.springsecurity6.controller.dto.FeedDTO;
import tech.melvin.springsecurity6.service.TweetService;

@RestController
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDTO> getFeed(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        FeedDTO feedDTO = tweetService.getFeed(page, pageSize);
        return ResponseEntity.ok(feedDTO);
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDTO createTweetDTO,
                                            JwtAuthenticationToken token) {

        tweetService.createTweet(createTweetDTO, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId,
                                            JwtAuthenticationToken token) {

        tweetService.deleteTweet(tweetId, token);
        return ResponseEntity.ok().build();
    }
}
