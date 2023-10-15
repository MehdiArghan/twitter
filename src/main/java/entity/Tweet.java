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
public class Tweet extends BaseEntity<Long> {
    String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "tweet")
    Set<Like> likes = new HashSet<>();
    @OneToMany(mappedBy = "tweet")
    Set<Comment> comments = new HashSet<>();
    public Tweet(String message, User user) {
        this.message = message;
        this.user = user;
    }
}
