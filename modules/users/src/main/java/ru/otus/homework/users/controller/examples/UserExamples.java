package ru.otus.homework.users.controller.examples;

public class UserExamples {

    public static final String CREATE = """
            {
              "username": "johndoe589",
              "firstName": "John",
              "lastName": "Doe",
              "email": "bestjohn@doe.com",
              "phone": "+71002003040"
            }
            """;

    public static final String GET = """
            {
              "id": 0,
              "username": "string",
              "firstName": "string",
              "lastName": "string",
              "email": "user@example.com",
              "phone": "string"
            }
            """;

    public static final String UPDATE = """
            {
              "firstName": "Julie",
              "lastName": "Doe",
              "email": "bestjohn@doe.com",
              "phone": "+71004242424"
            }
            """;

    public static final String UNEXPECTED_ERROR = """
            {
              "code": 0,
              "message": "string"
            }
            """;

}
