package org.Efaks.demo.service;

import org.Efaks.demo.exception.TweetException;
import org.Efaks.demo.exception.UserException;
import org.Efaks.demo.repository.entity.Like;
import org.Efaks.demo.repository.entity.User;

import java.util.List;

public interface LikeService {
    Like likeTweet(Long tweetId, User user) throws TweetException, UserException;

    List<Like> getAllLikes(Long tweetId) throws TweetException;

}
