package com.example.demo.posting;

import com.example.demo.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TweetRepo extends JpaRepository<Tweet,Long> {
    List<Tweet> findByUser(User user);

    @Query("""
            SELECT t FROM Tweet t
            WHERE (:lastSeenId IS NULL OR t.id < :lastSeenId)
            ORDER BY t.id DESC
           """)
    List<Tweet> findNextTweets(@Param("lastSeenTime") LocalDateTime lastSeenTime, Pageable pageable);

}
