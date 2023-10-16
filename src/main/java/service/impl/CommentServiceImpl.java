package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.Comment;
import org.hibernate.Session;
import repository.CommentRepository;
import service.CommentService;

public class CommentServiceImpl extends BaseServiceImpl<Long, Comment, CommentRepository> implements CommentService {
    public CommentServiceImpl(Session session, CommentRepository repository) {
        super(session, repository);
    }
}
