# java-shareit

![ER-диаграмма](ShareIt.png)
Пересоздание БД:

```postgresql
DROP USER IF EXISTS test;
CREATE USER test WITH SUPERUSER PASSWORD 'test';
DROP DATABASE shareit;
CREATE DATABASE shareit WITH OWNER test;
```

Удаление таблиц и сиквенсов:

```postgresql
DROP TABLE IF EXISTS comments;
DROP SEQUENCE IF EXISTS comment_seq;
DROP TABLE IF EXISTS bookings;
DROP SEQUENCE IF EXISTS booking_seq;
DROP TABLE IF EXISTS items;
DROP SEQUENCE IF EXISTS item_seq;
DROP TABLE IF EXISTS requests;
DROP SEQUENCE IF EXISTS request_seq;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS user_seq;
```

Для проверки покрытия на локальной машине, в `pom.xml` добавить `<phase>test</phase>` к цели `<id>jacoco-check</id>`
и запустить `mvn test` (или переименовать `pom.debug.xml` в `pom.xml`):

```xml

<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    ...
    <executions>
        ...
        <execution>
            <id>jacoco-check</id>
            <phase>test</phase>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                ...
```

Для проверки Postman-тестов на локальной машине, в `docker-compose.yml` добавить `TZ=Europe/Moscow` в массив `environment`
для каждого из `services` (или переименовать `docker-compose.debug.yml` в `docker-compose.yml`):

```yaml
services:
  gateway:
    ...
    environment:
      ...
      - TZ=Europe/Moscow

  server:
    ...
    environment:
      ...
      - TZ=Europe/Moscow

  db:
    ...
    environment:
      ...
      - TZ=Europe/Moscow
```