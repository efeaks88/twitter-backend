package org.Efaks.demo.service;

import org.Efaks.demo.dto.request.TweetReplyRequest;
import org.Efaks.demo.exception.TweetException;
import org.Efaks.demo.exception.UserException;
import org.Efaks.demo.repository.entity.Tweet;
import org.Efaks.demo.repository.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TweetService {

    Tweet createTweet(Tweet tweet, User user) throws UserException;

    List<Tweet> findAllTweets();

    Tweet reTweet(Long tweetId, User user) throws UserException, TweetException;
    Tweet findById(Long tweetId) throws TweetException;

    void deleteTweetById(Long tweetId,Long userId) throws TweetException;

    Tweet removeFromRetweet(Long tweetId,User user) throws TweetException, UserException;

    Tweet createdReply(TweetReplyRequest req,User user) throws TweetException;

    List<Tweet> getUserTwit(User user);

    List<Tweet> findByLikesContainsUser(User user);

}
