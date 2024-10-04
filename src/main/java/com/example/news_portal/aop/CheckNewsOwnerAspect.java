package com.example.news_portal.aop;

import com.example.news_portal.exceptions.NotAccessibleException;
import com.example.news_portal.model.News;
import com.example.news_portal.model.User;
import com.example.news_portal.services.DatabaseNewsService;
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
public class CheckNewsOwnerAspect {

    @Autowired
    private DatabaseUserService databaseUserService;

    @Autowired
    private DatabaseNewsService databaseNewsService;

    @Around("@annotation(CheckNewsOwner) && args(news,..)")
    public Object checkOwner(ProceedingJoinPoint joinPoint, News news) throws Throwable {

        User user = databaseUserService.findById(news.getUser().getId());

        String currentUser = user.getUsername();
        String databaseUser = databaseNewsService.findNewsOwnerById(news.getId());

        // Проверяем, что текущий пользователь является создателем новости
        if (!currentUser.equals(databaseUser)) {
            throw new NotAccessibleException("У вас нет разрешения редактировать новость!");
        }
        log.info("Пользователь: {} отредактировал новость", currentUser);

        return joinPoint.proceed();
    }
}
