<h1> News Portal </h1>
Новостной портал - приложение созданное с использованием Spring MVC, Spring Data JDBC, Spring Security. Запуск и настройка базы данных Postgres производится через Docker-файлы в папке docker. Запросы вводятся через Postman.

Посредством Api-контроллеров приложение выполняет следующие задачи:

1. Создание пользователей и управление ими;
2. Создание категорий новостей и управление ими;
3. Создание новостей и управление ими;
4. Создание комментариев для новостей и управление ими.

Логика работы сервиса:

1. Возврат списка сущностей (findAll) происходит с помощью пагинации;
2. При возврате списка новостей ответ не содержит списка комментариев к каждой новости, а отображает количество комментариев к новости;
3. Когда возвращается одна новость (findById), она содержит все комментарии к ней;
4. Есть фильтрация новостей по категориям и авторам;
5. Реализовано использование мапперов для преобразования тела запроса в сущность, с помощью чего осуществляется работа с базой данных (например, сохранить преобразованную сущность), и сущности в тело ответа.

Также реализованна basic authentication с помощью spring security:

1. В приложении есть следующие роли: ROLE_ADMIN, ROLE_USER, ROLE_MODERATOR.
2. Контроллер, который предоставляет операции взаимодействия с сущностью User:
   - поиск по всем пользователям может производить только клиент с ролью ROLE_ADMIN;
   - получать информацию о пользователе по ID может клиент с одной из следующих ролей: ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR (пользователь, у которого есть только ROLE_USER, может получить только информацию о себе);
   - обновить информацию о пользователе может клиент с одной из следующих ролей: ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR (пользователь, у которого есть только ROLE_USER, имеет право на обновление только информации о себе);
   - удалить профиль пользователя может клиент с одной из следующих ролей: ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR ( пользователь, у которого есть только ROLE_USER, имеет право на обновление только информации о себе);
   - проверка правил на удаление, обновление и получение информации о пользователе по ID происходит через AOP.
3. В контроллере, отвечающем за работу с категориями:
   - получение списка категорий и конкретной категории по ID доступно пользователю с любой из следующих ролей: ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR;
   - создание, редактирование и удаление категорий доступны пользователю с любой из следующих ролей: ROLE_ADMIN, ROLE_MODERATOR.
4. В контроллере, отвечающем за работу с новостями:
   - получать список новостей, конкретную новость по ID, а также создавать новости может пользователь с любой из следующих ролей: ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR;
   - обновлять новость может только пользователь, который её создал;
   - удалить новость может пользователь с одной из следующих ролей: ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR ( пользователь, у которого есть только ROLE_USER, имеет право на удаление только той новости, которую создал сам);
5. В контроллере, отвечающем за работу с комментариями:
   - получать, обновлять, создавать и удалять комментарии может пользователь, у которого есть одна из следующих ролей: ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR;
   - обновлять комментарий может только пользователь, который его создал. Удалить комментарий может только пользователь, который его создал, либо пользователь, имеющий одну из следующих ролей: ROLE_ADMIN, ROLE_MODERATOR.

6. Файл с HTTP запросами для Postman в папке data (Его можно импортировать в Postman)
