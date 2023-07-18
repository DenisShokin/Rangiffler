# ![](readme/img/deer-logo.png) Rangiffler
**Дипломная работа по курсу QA.GURU Advanced**
<hr>

## ![](readme/img/edit-info.png) О проекте
+ Rangiffler - web-приложение для сохранения фотографий посещенных стран на карте мира

### Технологии, использованные в Rangiffler:
- [Spring Authorization Server](https://spring.io/projects/spring-authorization-server)
- [Spring OAuth 2.0 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [Spring data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Web](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#spring-web)
- [Spring actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Spring web-services](https://docs.spring.io/spring-ws/docs/current/reference/html/)
- [Postgres](https://www.postgresql.org/about/)
- [React](https://ru.reactjs.org/docs/getting-started.html)
- [GraphQL](https://graphql.org/)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Jakarta Bean Validation](https://beanvalidation.org/)
- [JUnit 5 (Extensions, Resolvers, etc)](https://junit.org/junit5/docs/current/user-guide/)
- [Allure](https://docs.qameta.io/allure/)
- [Selenide](https://selenide.org/)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle 7.6](https://docs.gradle.org/7.6/release-notes.html)

### Микросервисы rangiffler:
+ [Rangiffler-auth]() - *Сервис авторизации*
+ [Rangiffler-gateway]() - *Api-шлюз*
+ [Rangiffler-photo]() - *Сервис для работы с фотографиями пользователей*
+ [Rangiffler-geo]() - *Сервис для работы с геоданными*
+ [Rangiffler-users]() - *Сервис для работы с профилями пользователей*

<hr>

###  Минимальные предусловия для работы с проектом Rangiffler:
#### 1. Установить PostgresSQL Server
#### 2. Установить одну из программ для визуальной работы с Postgres. 
Например, PgAdmin 4.
#### 3.Подключиться к БД postgres (host: localhost, port: 5432, user: postgres, pass: secret, database name: postgres) из PgAdmin и создать пустые БД микросервисов

```sql
create
database "rangiffler-userdata" with owner postgres;
create
database "rangiffler-photo" with owner postgres;
create
database "rangiffler-geo" with owner postgres;
create
database "rangiffler-auth" with owner postgres;
```
#### 4. Установить Java версии 17 или новее.
#### 5. Установить пакетый менеджер для сборки front-end npm

[Инструкция](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm).
Рекомендованная версия Node.js - 18.13.0 (LTS)
<hr>

## ![](readme/img/editor.png) Запуск Rangiffler локальное в IDE:
#### 1. Запусти фронт Rangiffler, для этого перейти в соответствующий каталог

```posh
dshokin rangiffler % cd rangiffler-client
```
---
Обнови зависимости и запускай фронт:

```posh
dshokin rangiffler-client % npm i
dshokin rangiffler-client % npm start
```

Фронт стартанет в твоем браузере на порту 3001: http://127.0.0.1:3001/

#### 2. Прописать run конфигурацию для всех сервисов rangiffler-* - Active profiles local

Для этого зайти в меню Run -> Edit Configurations -> выбрать main класс -> указать Active profiles: local
[Инструкция](https://stackoverflow.com/questions/39738901/how-do-i-activate-a-spring-boot-profile-when-running-from-intellij).

#### 3. Запустить сервис Rangiffler-auth c помощью gradle или командой Run в IDE:

- Запустить сервис auth

```posh
dshokin rangiffler % cd rangiffler-auth
dshokin rangiffler-auth % gradle bootRun --args='--spring.profiles.active=local'
```

Или просто перейдя к main-классу приложения RangifflerAuthApplication выбрать run в IDEA (предварительно удостовериться что
выполнен предыдущий пункт)

#### 4. Запустить другие сервисы: rangiffler-gateway, rangiffler-geo, rangiffler-photo, rangiffler-userdata

---

## ![](readme/img/testing.png) Запуск e2e тестов локально
Для Rangiffler реализованы e-2-e тесты, покрывающие основные сценарии работы с приложением.
#### 1. Для запуска тестов Rangiffler, необходимо перейти в соответствующий каталог

```posh
dshokin rangiffler % cd rangiffler-e-2-e-tests
```
#### 2. Выполнить gradle task в IDE
```posh
dshokin rangiffler-e-2-e-tests % gradle rangiffler-e-2-e-tests:clean rangiffler-e-2-e-tests:test
```
#### 3. После завершения тестов для просмотра Allure-отчета выполнить команду
```posh
dshokin rangiffler-e-2-e-tests % gradle :rangiffler-e-2-e-tests:allureServe
```
---

## ![](readme/img/docker.png) Запуск Rangiffler в докере:

#### 1. Установить docker (Если не установлен)

Используется docker для БД (Postgres), кроме того, микросервисы запускаются в едином docker network при
помощи docker-compose

[Установка на Windows](https://docs.docker.com/desktop/install/windows-install/)

[Установка на Mac](https://docs.docker.com/desktop/install/mac-install/) (Для ARM и Intel разные пакеты)

[Установка на Linux](https://docs.docker.com/desktop/install/linux-install/)

После установки и запуска docker daemon необходимо убедиться в работе команд docker, например `docker -v`:

```posh
docker -v
Docker version 20.10.14, build a224086
```

#### 2. Создать volume для сохранения данных из БД в docker на вашем компьютере

```posh
docker volume create pgdata
```
#### 3. Прописать в etc/hosts элиас для Docker-имени
#### frontend:  127.0.0.1 client.rangiffler.dc
#### auth:      127.0.0.1 auth.rangiffler.dc
#### gateway:   127.0.0.1 gateway.rangiffler.dc

```posh
##
# Host Database
#
# localhost is used to configure the loopback interface
# when the system is booting.  Do not change this entry.
##
127.0.0.1       localhost
127.0.0.1       client.rangiffler.dc
127.0.0.1       auth.rangiffler.dc
127.0.0.1       gateway.rangiffler.dc
```

#### 4. Перейти в корневой каталог проекта

```posh
cd rangiffler
```

#### 5. Запустить все сервисы:

```posh
bash docker-compose-dev.sh
```

#### 6. Подключиться к контейнеру rangiffler-all-db
Для создания баз данных для микросервисов выполнить скрипты в терминале:
```
psql -U postgres -c "CREATE DATABASE \"rangiffler-auth"\"
    
psql -U postgres -c "CREATE DATABASE \"rangiffler-geo"\"
    
psql -U postgres -c "CREATE DATABASE \"rangiffler-photo"\"
    
psql -U postgres -c "CREATE DATABASE \"rangiffler-userdata"\"
```
Дождаться старта всех контейнеров rangiffler-*

Rangiffler при запуске в докере доступен по адресу http://client.rangiffler.dc/

---
## ![](readme/img/testing.png) Запуск e-2-e тестов в докере:
#### 1. Перейти в корневой каталог проекта

```posh
cd rangiffler
```

#### 2. Запустить все сервисы и тесты:

```posh
rangiffler % bash docker-compose-e2e.sh
```

#### 3. [Selenoid UI](http://localhost:9090/)

#### 4. [Allure](http://localhost:5050/allure-docker-service/projects/rangiffler-e-2-e-tests/reports/latest/index.html)
