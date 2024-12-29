package tech.melvin.springsecurity6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.melvin.springsecurity6.entity.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
