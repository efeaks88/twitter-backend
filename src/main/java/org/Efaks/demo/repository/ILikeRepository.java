package org.Efaks.demo.repository;

import org.Efaks.demo.repository.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ILikeRepository extends JpaRepository<Like,Long> {

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.tweet.id = :tweetId")
    Like isLikeExist(@Param("userId") Long userId,@Param("tweetId")Long tweetId);

    List<Like> findByTweetId(Long tweetId);
}
