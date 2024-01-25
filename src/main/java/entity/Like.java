package entity;

import base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "likes")
public class Like extends BaseEntity<Long> {
    String likes;
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    Tweet tweet;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;


    public Like(String likes, Tweet tweet) {
        this.likes = likes;
        this.tweet = tweet;
    }

    public Like(String likes, Comment comment) {
        this.likes = likes;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Like{" +
                "likes='" + likes + '\'' +
                "} " + super.toString();
    }
}
