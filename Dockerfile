FROM openjdk:17-alpine

# 작업 타겟 공간 생성
WORKDIR /app

# Jar 파일 위치 환경변수 설정
# Jar 파일은 Github Actions의 build 단계에서 생성된다. (아티팩트)
ARG JAR_FILE=./build/libs/homepage-0.0.1-SNAPSHOT.jar

# Jar 파일을 컨테이너의 /app 디렉토리로 복사
COPY ${JAR_FILE} /app/backend.jar

# backend 사용자 그룹 생성
# backend 사용자 생성 및 그룹에 추가
# chown 명령어로 /app 디렉토리의 소유자를 backend_user로 변경
# 이렇게 하는 이유는 보안상의 이유로 root 계정으로 작업하는건 좋지 않은 관행이기 때문 (보안 개선)
RUN addgroup -S backend && \
    adduser -S backend_user -G backend && \
    chown -R backend_user:backend /app

# root 대신 backend_user로 실행하도록 수정 (보안 개선)
USER backend_user

# 호스트컴퓨터에 노출할 포트 설정
EXPOSE 8080

# 컨테이너 실행 시 실행할 명령어
ENTRYPOINT ["java","-jar","backend.jar"]