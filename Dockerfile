FROM openjdk:17-jdk
LABEL authors="kmini"
ARG JAR_FILE=build/libs/rest-0.0.1-SNAPSHOT.jar
# JAR_FILE 경로에 있는 파일을 이미지 내부에 rest-springboot.jar로 저장한다
ADD ${JAR_FILE} rest-springboot.jar
# 컨테이너 포트 포워딩하려면 프로세스 자체에서도 포트를 변경해 줘야 한다.
ENTRYPOINT ["java", "-Dserver.port=3000", "-jar", "rest-springboot.jar"]