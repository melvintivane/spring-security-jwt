package tech.melvin.springsecurity6.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.melvin.springsecurity6.dto.CreateTweetDTO;
import tech.melvin.springsecurity6.dto.FeedDTO;
import tech.melvin.springsecurity6.dto.FeedItemDTO;
import tech.melvin.springsecurity6.entity.Role;
import tech.melvin.springsecurity6.entity.Tweet;
import tech.melvin.springsecurity6.repository.TweetRepository;
import tech.melvin.springsecurity6.repository.UserRepository;

import java.util.UUID;

@Service
public class TweetService {
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public TweetService(UserRepository userRepository, TweetRepository tweetRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }

    public FeedDTO getFeed(int page, int pageSize) {
        var tweets = tweetRepository.findAll(
                  PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
             .map(tweet -> new FeedItemDTO(
                  tweet.getTweetId(),
                  tweet.getContent(),
                  tweet.getUser().getUsername()
             ));

        return new FeedDTO(
             tweets.getContent(),
             page,
             pageSize,
             tweets.getTotalPages(),
             tweets.getTotalElements()
        );
    }

    public void createTweet(CreateTweetDTO createTweetDTO, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()))
             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        var tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(createTweetDTO.content());

        tweetRepository.save(tweet);
    }

    public void deleteTweet(Long tweetId, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()))
             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));

        var tweet = tweetRepository.findById(tweetId)
             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found."));

        var isAdmin = user.getRoles()
             .stream()
             .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            tweetRepository.deleteById(tweetId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden.");
        }
    }
}
