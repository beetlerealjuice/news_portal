package com.example.news_portal.aop;

import com.example.news_portal.exceptions.NotAccessibleException;
import com.example.news_portal.model.Comment;
import com.example.news_portal.model.User;
import com.example.news_portal.services.DatabaseCommentService;
import com.example.news_portal.services.DatabaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CheckCommentOwnerAspect {

    @Autowired
    private DatabaseUserService databaseUserService;

    @Autowired
    private DatabaseCommentService databaseCommentService;

    @Around("@annotation(CheckCommentOwner) && args(comment,..)")
    public Object checkOwner(ProceedingJoinPoint joinPoint, Comment comment) throws Throwable {

        User user = databaseUserService.findById(comment.getUser().getId());

        String currentUser = user.getUsername();
        String databaseUser = databaseCommentService.findCommentOwnerById(comment.getId());

        // Проверяем, что текущий пользователь является создателем комментария
        if (!currentUser.equals(databaseUser)) {
            throw new NotAccessibleException("У вас нет разрешения редактировать комментарий!");
        }
        log.info("Пользователь: {} отредактировал комментарий", currentUser);

        return joinPoint.proceed();
    }
}
