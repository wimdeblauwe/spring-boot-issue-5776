# spring-boot-issue-5776
Sample project for [Spring Boot issue 5776](https://github.com/spring-projects/spring-boot/issues/5776#issuecomment-220768492)

To see the issue:

Run `UserDtoTest`. It will fail with:

> org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'entityManagerFactory' is defined

If you comment out the `@ComponentScan` in `Issue5776Application`, then you get a different error:

> java.lang.IllegalArgumentException: At least one JPA metamodel must be present!

The test works if you add `@SpringBootTest` and `@AutoConfigureJsonTesters` instead of `@JsonTest`


