# 빌드 스테이지 -> JAR 파일 생성
FROM openjdk:17-alpine AS builder

# 작업 타겟 공간 생성
WORKDIR /app

# 현재 폴더 내용 싹다 복사해서 컨테이너 볼륨으로 이동
COPY . .

# gradle 실행 권한 부여
# RUN chmod +x ./gradlew

# JAR 파일 생성
# clean을 사용하는 이유는, build만 사용하면 필요 없는 파일들이 남아있는데, clean을 사용해서 필요 없는 파일들을 빌드 후 자동으로 제거할 수 있음
# github actions에서 gradle을 사용해서 빌드하나 여기서 하나 다를건 없는데
# github actions보다는 docker에서 gradle을 사용하는게 더 깔끔하니까 (캐시 설정같은거 안해도 됨)
# docker에서 gradle을 사용해서 빌드하는걸 추천드립니다
RUN ./gradlew clean build -x test

# 보통 builder 스테이지랑 runner 스테이지랑 나누는 이유가
# application을 build하고 중간 산출물이나 캐시, 테스트 리소스들이 생겨나는데, 실제 실행하는건 .jar 파일만 필요하니까
# 이런 부산물들이 필요가 없습니다. 뿐만 아니라 .dockerignore 파일에 명시된 파일들도 제외해버릴 수 있습니다.
# 그래서 builder 스테이지에서 build하고, runner 스테이지에 필요한 jar 파일만 딱 복사해와서 docker 이미지 사이즈 자체를
# 확 줄여버릴 수 있습니다.

# 실행 스테이지 -> JAR 파일 실행
FROM openjdk:17-alpine AS runner

# 작업 타겟 공간 생성
WORKDIR /app

# 빌드 스테이지에서 생성한 JAR 파일 불러와서 runner 스테이지 /app 아래에 backend.jar로 복사
COPY --from=builder /app/build/libs/homepage-0.0.1-SNAPSHOT.jar backend.jar

# backend 사용자 그룹 생성
# backend 사용자 생성 및 그룹에 추가
# chown 명령어로 /app 디렉토리의 소유자를 backend_user로 변경
# 이렇게 하는 이유는 보안상의 이유로 root 계정으로 작업하는건 좋지 않은 관행이기 때문 (보안 개선)
RUN addgroup -S backend && \
    adduser -S backend_user -G backend && \
    chown -R backend_user:backend /app

# root 대신 backend_user로 실행하도록 수정
USER backend_user

# 호스트컴퓨터에 노출할 포트 설정
EXPOSE 8080

# 컨테이너 실행 시 실행할 명령어
ENTRYPOINT ["java","-jar","backend.jar"]