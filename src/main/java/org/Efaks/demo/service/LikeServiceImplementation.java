package org.Efaks.demo.service;

import org.Efaks.demo.exception.TweetException;
import org.Efaks.demo.exception.UserException;
import org.Efaks.demo.repository.ILikeRepository;
import org.Efaks.demo.repository.ITweetRepository;
import org.Efaks.demo.repository.entity.Like;
import org.Efaks.demo.repository.entity.Tweet;
import org.Efaks.demo.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LikeServiceImplementation implements LikeService {

    @Autowired
    ILikeRepository likeRepository;
    @Autowired
    TweetService tweetService;
    @Autowired
    ITweetRepository tweetRepository;
    @Override
    public Like likeTweet(Long tweetId, User user) throws TweetException, UserException {
        Like isLikeExist=likeRepository.isLikeExist(user.getId(),tweetId);
        if(isLikeExist==null){
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }
        Tweet tweet=tweetService.findById(tweetId);
        Like like=new Like();
        like.setTweet(tweet);
        like.setUser(user);
        Like savedLike=likeRepository.save(like);
        tweet.getLikes().add(savedLike);
        tweetRepository.save(tweet);
        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(Long tweetId) throws TweetException {
        Tweet tweet=tweetService.findById(tweetId);
        List<Like> likes=likeRepository.findByTweetId(tweetId);
        return likes;
    }

}
