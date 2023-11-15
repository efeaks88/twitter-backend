package org.Efaks.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetReplyRequest {
    private String content;
    private Long tweetId;
    private LocalDate createdAt;
    private String image;
}
