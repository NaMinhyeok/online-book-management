# 실행 방법

## 로컬 실행 방법

```
 DATABASE_HOST = your_host
    DATABASE_PASSWORD = your_password
    DATABASE_NAME = your_database_name
    DATABASE_USER = your_name
```
값을 실행시키고 싶은 값을 넣은 후 실행시키고 싶은 profile로 실행한다.

## 로컬 api 문서 확인법

1. 로컬 실행 방법을 마친다.
2. `http://localhost:8080/swagger-ui.html`로 접속합니다.

## docker-compose를 이용한 실행 방법

1. `.env` 파일을 생성하여 아래와 같이 환경 변수를 설정합니다.
    ```
   DATABASE_HOST = your_host
    DATABASE_PASSWORD = your_password
    DATABASE_NAME = your_database_name
    DATABASE_USER = your_name
   SPRING_PROFILES_ACTIVE = local
    ```
   
2. `docker-compose up` 명령어를 실행합니다.
3. `localhost:8080`을 포트로 이용할 수 있습니다.


## Cloud에 배포되어있는 서버 사용 방법

1. `http://43.201.9.205:8080/swagger-ui/index.html` 을 통해 swagger를 확인 할 수 있습니다.
2. 해당 api를 사용하고 싶다면 `http://43.201.9.205:8080` 을 이용하시면 됩니다.