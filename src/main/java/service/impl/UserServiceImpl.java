package service.impl;

import base.service.impl.BaseServiceImpl;
import entity.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import repository.UserRepository;
import service.UserService;

import java.util.Optional;
import java.util.Set;

public class UserServiceImpl extends BaseServiceImpl<Long, User, UserRepository> implements UserService {
    public UserServiceImpl(Session session, UserRepository repository) {
        super(session, repository);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public Optional<User> findByPassword(String password) {
        return repository.findByPassword(password);
    }

    @Override
    public void singUp(User user) {
        try {
            session.getTransaction().begin();
            repository.save(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public boolean validate(User user) {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider()
                .configure().messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                System.out.println(violation.getMessage());
            }
            validatorFactory.close();
            return false;
        } else {
            singUp(user);
            return true;
        }
    }
}