package org.Efaks.demo.service;

import org.Efaks.demo.dto.request.TweetReplyRequest;
import org.Efaks.demo.exception.TweetException;
import org.Efaks.demo.exception.UserException;
import org.Efaks.demo.repository.ITweetRepository;
import org.Efaks.demo.repository.entity.Tweet;
import org.Efaks.demo.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TweetServiceImplementation implements TweetService {

    @Autowired
    private ITweetRepository tweetRepository;

    @Override
    public Tweet createTweet(Tweet req, User user) throws UserException {
        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDate.now());
        tweet.setImage(req.getImage());
        tweet.setIsReply(false);
        tweet.setIsTweet(true);
        tweet.setVideo(req.getVideo());
        tweet.setUser(user);

        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findAllTweets() {
        return tweetRepository.findAllByIsTweetTrueOrderByCreatedAtDesc();
    }

    @Override
    public Tweet reTweet(Long tweetId, User user) throws UserException, TweetException {
        Tweet tweet = findById(tweetId);
        if (tweet.getRetweetUsers().contains(user)) {
            tweet.getRetweetUsers().remove(user);
        } else {
            tweet.getRetweetUsers().add(user);
        }
        return tweetRepository.save(tweet);
    }

    @Override
    public Tweet findById(Long tweetId) throws TweetException {
        return tweetRepository.findById(tweetId).orElseThrow(() -> new TweetException("Tweet not found"));
    }

    @Override
    public void deleteTweetById(Long tweetId, Long userId) throws TweetException {
        Tweet tweet = findById(tweetId);
        if (tweet.getUser().getId().equals(userId)) {
            tweetRepository.deleteById(tweetId);
        } else {
            throw new TweetException("Başkasının tweetini silemezsin");
        }
    }

    @Override
    public Tweet removeFromRetweet(Long tweetId, User user) throws TweetException, UserException {
        return null;
    }

    @Override
    public Tweet createdReply(TweetReplyRequest req, User user) throws TweetException {
        Tweet replyFor= findById(req.getTweetId());

        Tweet tweet = new Tweet();
        tweet.setContent(req.getContent());
        tweet.setCreatedAt(LocalDate.now());
        tweet.setImage(req.getImage());
        tweet.setIsReply(true);
        tweet.setIsTweet(false);
        tweet.setUser(user);
        tweet.setReplyFor(replyFor);
        Tweet savedReply= tweetRepository.save(tweet);
        tweet.getReplyTweets().add(savedReply);
        return replyFor;
    }

    @Override
    public List<Tweet> getUserTwit(User user) {
        return tweetRepository.findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(user,user.getId());
    }

    @Override
    public List<Tweet> findByLikesContainsUser(User user) {
        return tweetRepository.findByLikesUser_id(user.getId());
    }
}
