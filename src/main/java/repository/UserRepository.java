package repository;

import base.repository.BaseRepository;
import entity.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<Long, User> {
    Optional<User> findByUserName(String UserName);
}
