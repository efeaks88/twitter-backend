package org.Efaks.demo.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String location;
    private String website;
    private String birthDate;
    private String email;
    private String password;
    private String mobile;
    private String image;
    private String backgroundImage;
    private String bio;
    private Boolean req_user;
    private Boolean login_with_google;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Tweet> tweet=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Like> likes=new ArrayList<>();

    @Embedded
    private Varification verification;  //verification diye bir tanım var o yüzden doğru adıyla tanımlayamadım .d

    @JsonIgnore
    @ManyToMany
    @Builder.Default
    private List<User> followers=new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @Builder.Default
    private List<User> followings=new ArrayList<>();

}
