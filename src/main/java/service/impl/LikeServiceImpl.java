package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Like;
import org.hibernate.Session;
import repository.LikeRepository;
import service.LikeService;

public class LikeServiceImpl extends BaseServiceImpl<Long, Like, LikeRepository> implements LikeService {
    public LikeServiceImpl(Session session, LikeRepository repository) {
        super(session, repository);
    }
}