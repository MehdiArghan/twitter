package entity;

import base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Like extends BaseEntity<Long> {
    int likes;
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    Tweet tweet;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    Comment comment;
}
