# java-backend

## Description

Simple backend service using java. Everything new knowledge can be documented here.

## Development Guide

### Prerequisite

- SDK version 17
- Gradle version 7.4

### Guide

1. Copy `env.sample` to `.env`, change env as needed.

   ```sh
   cp env.sample .env
   ```

2. Run docker under `dev` directory

   ```sh
   docker-compose up -d 
   ```
   This will start mysql, redis, and kafka.

   If you want to stop docker you can use this command.
   ```
   docker-compose down
   ```
   or you can directly run docker from IntelliJ IDEA

3. Run app.

   ```sh
   ./gdradlew run
   ```
   or you can directly run from IntelliJ IDEA under Main.java file

4. Web service is available on port 7725.


### Dependencies
- MySQL
- Vert.x
- Dotenv


## Sources
- https://github.com/ReactiveX/RxJava
- https://www.youtube.com/@codiguard
