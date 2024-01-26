package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Comment extends BaseEntity<Long> {
    String massage;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    Tweet tweet;
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REFRESH)
    Set<Like> likes;

    public Comment(String massage, Tweet tweet) {
        this.massage = massage;
        this.tweet = tweet;
    }

    public Comment(String massage, Set<Like> likes) {
        this.massage = massage;
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "massage='" + massage + '\'' +
                ", user=" + user +
                ", tweet=" + tweet +
                "} " + super.toString();
    }
}