package com.example.demo.posting;

import com.example.demo.User;
import com.example.demo.UserPublicDTO;
import com.example.demo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TweetRepo tweetRepo;

    public TweetDTO postTweet(String content,String email){
        Optional<User>userOptional=userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            UserPublicDTO userPublicDTO=convert(userOptional.get());
            Tweet tweet=new Tweet(content,userOptional.get());
            tweetRepo.save(tweet) ;
            return convertToTweetDto(tweet);

        }else throw new UsernameNotFoundException("user not found");

    }

    public List<TweetDTO> getAllTweet(Long LastSeenId){
        return convertToTweetDto(tweetRepo.findAll(PageRequest.of(0, 30, Sort.by("id").descending())).getContent());

    }
    public List<TweetDTO >findTweetByname(String name){
        System.out.println("looknig for user: "+ name);
        Optional<List<User>> users=userRepository.findByName(name);
        if(users.isPresent()){
            List<Tweet >tweets=new ArrayList<>();
            for(User user:users.get())
                tweets.addAll(tweetRepo.findByUser(user));
            System.out.println(tweets.getFirst());
            return convertToTweetDto(tweets);
        }
        return new ArrayList<>();
    }
    public UserPublicDTO convert(User user){
        return new UserPublicDTO(user.getId(), user.getEmail(), user.getName());
    }
    public TweetDTO convertToTweetDto(Tweet tweet){
        return new TweetDTO(convert(tweet.getUser()),tweet.getContent(),tweet.getId());
    }
    public List<TweetDTO> convertToTweetDto(List<Tweet> tweet){
        return tweet.stream()
                .map(this::convertToTweetDto)
                .toList();

    }
}
