package com.example.demo;

import com.example.demo.posting.Tweet;
import com.example.demo.posting.TweetDTO;
import com.example.demo.posting.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class testController {
    @Autowired
    TweetService tweetService;
    @GetMapping
    public String Hello(){
        System.out.println("Hello World");
        return "Hello";
    }
    @PostMapping
    public ResponseEntity<?> tweetPost(@RequestBody Map<String,String> content, @AuthenticationPrincipal UserDetails userDetails){
        TweetDTO tweet=tweetService.postTweet(content.get("content"),userDetails.getUsername());
        return ResponseEntity.ok(tweet);
    }
    @GetMapping("/getTweet")
    public List<TweetDTO> getAllTweet(@RequestParam(required = false) Long cursor){
        return tweetService.getAllTweet(cursor);
    }
    @GetMapping("/getTweetByName")
    public List<TweetDTO> getAllTweet(@RequestParam String name){
        return tweetService.findTweetByname(name);
    }



    @GetMapping("/user")
    public List<TweetDTO> getUserTweets(@AuthenticationPrincipal UserDetails userDetails) {
        return tweetService.findTweetByname(userDetails.getUsername());
    }



}
