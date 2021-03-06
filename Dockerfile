# syntax=docker/dockerfile:experimental
FROM openjdk:14-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests
RUN mkdir -p target/dependency \
    && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:14-jdk-alpine
RUN addgroup -S demo \
    && adduser -S demo -G demo
VOLUME /tmp
USER demo
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-cp", "app:app/lib/*", \
    "com.mergipe.recipesapp.RecipesApplication", \
    "--spring.datasource.url=jdbc:mysql://db:3306/db_app"]