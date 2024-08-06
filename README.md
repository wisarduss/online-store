# online-store

## 1. Сборка проекта 

1. Запустить приложение в контейнере docker-compose up -d

## 2. Получвение и работа с JWT токеном

- Время жизни токена 60 минут 
- Для получения - Для получения сделать POST запрос в Postman "http://localhost:8080/registration"

  {
  "name": "Maxim",
  "surname": "Borodulin",
  "email": "Borodulin@mail.ru",
  "password": "12345"
  }

После окончания жизни токена сделать запрос в Postman "http://localhost:8080/login"

      {
       "email": "Borodulin@mail.ru",
       "password": "12345"
      }

- Полученный JWT токен вставляем в header Authorization: Bearer JWT token

## Диаграмма базы данных 

![img.png](img.png)
