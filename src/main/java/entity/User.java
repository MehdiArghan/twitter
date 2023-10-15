package entity;

import base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class User extends BaseEntity<Long> {
    String firstName;
    String lastName;
    String userName;
    String password;
    LocalDate birthDate;
    @OneToMany(mappedBy = "user")
    Set<Tweet> tweets = new HashSet<>();
    public User(String firstName, String lastName, String userName, String password, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
    }
}
