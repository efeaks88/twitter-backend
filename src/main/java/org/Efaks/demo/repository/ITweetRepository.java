package org.Efaks.demo.repository;

import org.Efaks.demo.repository.entity.Tweet;
import org.Efaks.demo.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITweetRepository extends JpaRepository<Tweet,Long> {


    List<Tweet> findAllByIsTweetTrueOrderByCreatedAtDesc();
    List<Tweet> findByRetweetUserContainsOrUser_IdAndIsTweetTrueOrderByCreatedAtDesc(User user, Long userId);

    List<Tweet> findByLikesContainingOrderByCreatedAtDesc(User user);

    @Query("SELECT t From Tweet t where t.likes l where l.user.id = :userId")
    List<Tweet> findByLikesUser_id(Long userId);

//    @Query("SELECT t FROM Tweet t WHERE t.user.id = :userId")
//    List<Tweet> findByUserId(Long userId);

}
