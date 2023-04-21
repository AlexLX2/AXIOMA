# back
# устанавливаем самую лёгкую версию JVM
FROM --platform=linux/amd64 openjdk:17-alpine

# указываем ярлык. Например, разработчика образа и проч. Необязательный пункт.
LABEL maintainer="alexei.kolosov@akdev.md"

# указываем точку монтирования для внешних данных внутри контейнера (как мы помним, это Линукс)
VOLUME /tmp

# внешний порт, по которому наше приложение будет доступно извне
EXPOSE 8080

# указываем, где в нашем приложении лежит джарник
ARG JAR_FILE=target/backend-0.0.1-SNAPSHOT.jar

# добавляем джарник в образ под именем rebounder-chain-backend.jar
ADD ${JAR_FILE} aksema.jar

# команда запуска джарника
ENTRYPOINT ["java","-jar","/aksema.jar"]