package entity;

import base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Comment extends BaseEntity<Long> {
    String massage;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    Tweet tweet;
    @OneToMany(mappedBy = "comment")
    Set<Like> likes = new HashSet<>();

    public Comment(String massage, User user, Tweet tweet) {
        this.massage = massage;
        this.user = user;
        this.tweet = tweet;
    }
}
