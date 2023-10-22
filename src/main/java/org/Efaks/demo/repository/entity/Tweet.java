package org.Efaks.demo.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String content;

    @OneToMany(mappedBy = "tweet",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Like> likes=new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<Tweet> replyTweets=new ArrayList<>();

    @ManyToMany
    @Builder.Default
    private List<User> retweetUsers=new ArrayList<>();

    @ManyToOne
    private Tweet replyFor;

    private Boolean isReply;

    private Boolean isTweet;

}
