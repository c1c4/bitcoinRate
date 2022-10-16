# Bitcoin Rate

## Language, framework, and API used in this project
- [Java 11](https://openjdk.org/projects/jdk/11/)
- [Spring shell](https://spring.io/projects/spring-shell#:~:text=Spring%20Shell's%20features%20include,completion%2C%20colorization%2C%20and%20script%20execution)
- [Jackson databind](https://github.com/FasterXML/jackson-databind)

### Test
- [Powermock](https://github.com/powermock/powermock)
- [Mockito](https://site.mockito.org/)
- [JUnit](https://junit.org/)

### Deploy to JAR
- [Spring boot maven plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)

### API
- [API coindesk](https://api.coindesk.com/v1/bpi)

----

# Purpose of this project

Check the rate, the lowest and highest rate of determined currency.

This is an example how this work.

![How it works](/src/main/resources/screenshots/example.png)

## Commands

The command to check the rates is _bitcoin-info --cc < currency >_

For any other commands you can use _help_ command.

---

# How to

### Deploy

You can use the command `mvn clean package` to create a jar file case you want to generate a new version with new code.

### Docker image

If you want just to test the project withou cloning you can push the docker image:

- `docker push felipeapompeu/bitcoin-rate:1.0`

and after that you can run:

- `docker run -it felipeapompeu/bitcoin-rate:1.0`

and use the commands you see in the Commands section.