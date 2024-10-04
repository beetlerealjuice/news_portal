package com.example.news_portal.aop;

import com.example.news_portal.exceptions.NotRightsException;
import com.example.news_portal.model.Comment;
import com.example.news_portal.model.News;
import com.example.news_portal.model.User;
import com.example.news_portal.services.DatabaseCommentService;
import com.example.news_portal.services.DatabaseNewsService;
import com.example.news_portal.services.DatabaseUserService;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.stream.Collectors;

@Aspect
@Component
@Data
public class LoggingAspect {

    private final DatabaseUserService userService;
    private final DatabaseNewsService newsService;
    private final DatabaseCommentService commentService;

    @Before("@annotation(Loggable)")
    public void checkUserId(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (joinPoint.getSignature().getName().contains("Comment")) {
            if (joinPoint.getSignature().getName().contains("delete")) {
                deleteComment(args);
            } else {
                commentCheck(args);
            }
        }
        if (joinPoint.getSignature().getName().contains("News")) {
            if (joinPoint.getSignature().getName().contains("delete")) {
                deleteNews(args);
            } else {
                newsCheck(args);
            }

        }
        if (joinPoint.getSignature().getName().contains("User")) {
            userCheck(args);
        }
    }

    private void userCheck(Object[] args) {

        User foundUser = userService.findById((Long) args[0]);
        String roleOfAsker = getRoles((UserDetails) args[1]);
        if (roleOfAsker.contains("ROLE_USER")) {
            boolean existsRole = foundUser.getRoles().stream().anyMatch(role ->
                    String.valueOf(role.getAuthority()).equals(roleOfAsker));
            if (!existsRole)
                throw new NotRightsException("You don't have enough rights, because you ROLE is: ROLE_USER");
        }
    }

    private String getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

    }

    private void deleteNews(Object[] args) {
        String roleOfAsker = getRoles((UserDetails) args[1]);
        if (roleOfAsker.contains("ROLE_USER")) {
            newsCheck(args);
        }
    }
    private void newsCheck(Object[] args) {

        News news = newsService.findById((Long) args[0]);
        String userName = ((UserDetails) args[1]).getUsername();
        User user = userService.findByUsername(userName);

        if (!user.getUsername().equals(news.getUser().getUsername())) {
            throw new NotRightsException(MessageFormat
                    .format("User {0} not created this news", userName));
        }
    }

    private void deleteComment(Object[] args) {
        String roleOfAsker = getRoles((UserDetails) args[1]);
        if (roleOfAsker.contains("ROLE_USER")) {
            commentCheck(args);
        }
    }

    private void commentCheck(Object[] args) {
        Comment comment = commentService.findById((Long) args[0]);
        String userName = ((UserDetails) args[1]).getUsername();
        User user = userService.findByUsername(userName);

        if (!user.getUsername().equals(comment.getUser().getUsername())) {
            throw new NotRightsException(MessageFormat
                    .format("User {0} not created this comment", userName));
        }
    }

}
