package view;

import base.repository.util.HibernateUtil;
import entity.Tweet;
import entity.User;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import repository.impl.CommentRepositoryImpl;
import repository.impl.LikeRepositoryImpl;
import repository.impl.TweetRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.CommentService;
import service.LikeService;
import service.TweetService;
import service.UserService;
import service.impl.CommentServiceImpl;
import service.impl.LikeServiceImpl;
import service.impl.TweetServiceImpl;
import service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Menu {
    private final Session session = HibernateUtil.getSessionFactory().openSession();
    private final UserService userService = new UserServiceImpl(session, new UserRepositoryImpl(session));
    private final TweetService tweetService = new TweetServiceImpl(session, new TweetRepositoryImpl(session));
    private final LikeService likeService = new LikeServiceImpl(session, new LikeRepositoryImpl(session));
    private final CommentService commentService = new CommentServiceImpl(session, new CommentRepositoryImpl(session));
    private final Scanner scanner = new Scanner(System.in);
    private static User person = new User();
    private final Set<Tweet> tweetList = new HashSet<>();

    public static User getUser() {
        return person;
    }

    public void showMenu() {
        System.out.println("--------------------------Twitter--------------------------");
        System.out.println();
        System.out.println("-----(1)signUp-----------(2)logIn-----------(3)exit------");
        switch (scanner.nextInt()) {
            case 1:
                signUp();
                logIn();
                break;
            case 2:
                logIn();
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("invalid option");
                showMenu();
        }
    }


    public void signUp() {
        System.out.println("name:----------------------");
        String name = scanner.next();
        System.out.println("family:--------------------");
        String family = scanner.next();
        System.out.println("userName:------------------");
        String username = scanner.next();
        System.out.println("password:------------------");
        String password = scanner.next();
        Tweet tweet = new Tweet();
        tweet.setMessage(username + "joined  tweeter ");
        tweetList.add(tweet);
        User user = new User(name, family, username, password);
        tweet.setUser(user);
        if (!userService.validate(user)) {
            showMenu();
        } else {
            person = user;
        }
    }


    public void logIn() {
        System.out.println("=========LogIn=========");
        System.out.println("username:---------------------");
        String userName = scanner.next();
        System.out.println("password:---------------------");
        String password = scanner.next();
        try {
            if (userService.findByUserName(userName).isPresent() &&
                    userService.findByPassword(password).isPresent()) {
                person = userService.findByPassword(password).get();
                showHome();
            }
        } catch (NoResultException e) {
            System.out.println("username and password is inCorrect");
            showMenu();
        }
    }


    public void showProfile() {
        System.out.println("=========Profile=========");
        System.out.println("=====================================================================================================================================");
        System.out.println(person);
        System.out.println("=====================================================================================================================================");
        System.out.println("(1)Edit profile----(2)remove account----(3)log out------");
        switch (scanner.nextInt()) {
            case 1:
                editProfile();
                break;
            case 2:
                userService.delete(person);
                System.out.println("this account is removed....");
                person = null;
                showMenu();
                break;
            case 3:
                person = null;
                showMenu();
                break;
            default:
                System.out.println("invalid option");
                showProfile();
        }
    }


    public void editProfile() {
        System.out.println("=========Edit=========");
        System.out.println("name:----------------------");
        String name = scanner.next();
        System.out.println("family:--------------------");
        String family = scanner.next();
        person.setFirstName(name);
        person.setLastName(family);
        if (!userService.validate(person)) {
            showMenu();
        } else {
            userService.update(person);
            System.out.println("updated.......");
        }
    }
}
