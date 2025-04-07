# 빌드 스테이지 -> JAR 파일 생성
FROM openjdk:17-alpine AS builder

# 작업 타겟 공간 생성
WORKDIR /app

# 현재 폴더 내용 싹다 복사해서 컨테이너 볼륨으로 이동
COPY . .

# JAR 파일 생성
# clean을 사용하는 이유는, build만 사용하면 필요 없는 파일들이 남아있는데, clean을 사용해서 필요 없는 파일들을 빌드 후 자동으로 제거할 수 있음
RUN ./gradlew clean build -x test

# 실행 스테이지 -> JAR 파일 실행
FROM openjdk:17-alpine AS runner

# 작업 타겟 공간 생성
WORKDIR /app

# 빌드 스테이지에서 생성한 JAR 파일 불러와서 runner 스테이지 /app 아래에 backend.jar로 복사
COPY --from=builder /app/build/libs/*.jar backend.jar

# backend 사용자 그룹 생성
# backend 사용자 생성 및 그룹에 추가
# chown 명령어로 /app 디렉토리의 소유자를 backend_user로 변경
# 이렇게 하는 이유는 보안상의 이유로 root 계정으로 작업하는건 좋지 않은 관행이기 때문
RUN addgroup -S backend && \
    adduser -S backend_user -G backend \
    chown -R backend_user:backend /app

# root 대신 backend_user로 실행하도록 수정
USER backend_user

# 호스트컴퓨터에 노출할 포트 설정
EXPOSE 8080

# 컨테이너 실행 시 실행할 명령어
ENTRYPOINT ["java","-jar","backend.jar"]