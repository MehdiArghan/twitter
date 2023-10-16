package repository.impl;

import base.repository.impl.BaseRepositoryImpl;
import entity.Comment;
import org.hibernate.Session;
import repository.CommentRepository;

public class CommentRepositoryImpl extends BaseRepositoryImpl<Long, Comment> implements CommentRepository {
    protected final Session session;

    public CommentRepositoryImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
