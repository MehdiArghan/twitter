package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
public class Tweet extends BaseEntity<Long> {
    @Size(max = 280, message = "Max number of characters: 280")
    @Column(length = 280)
    String message;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.REMOVE)
    Set<Like> likes;
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.REMOVE)
    Set<Comment> comments;

    public Tweet(String message, User user) {
        this.message = message;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "message='" + message + '\'' +
                ", user=" + user +
                "} ";
    }
}
