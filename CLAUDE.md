# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) for the DeepMirror project.

## 1. 프로젝트 개요
DeepMirror는 성격 분석 및 미러링을 위한 Spring Boot 기반 백엔드 서버입니다.
- **Environment**: Ubuntu Server 24.04, Docker Compose
- **Tech Stack**: Java 17, Spring Boot 3.4.0, Gradle (Groovy), PostgreSQL, JPA

## 2. 코딩 컨벤션 (매우 중요 ★★★)
이 규칙은 모든 코드 생성 시 반드시 지켜야 합니다.
1. **NO LOMBOK**: Lombok 어노테이션(@Data, @Getter, @Builder 등)을 **절대 사용하지 마십시오.**
   - Getter, Setter, 생성자, Builder 패턴이 필요하면 **직접 코드로 작성**하십시오.
   - 이유: 코드의 명시성과 순수 Java 구조 유지를 위함.
2. **생성자 주입(Constructor Injection)**: `@Autowired` 대신 `final` 필드와 생성자를 사용하십시오.
3. **패키지 구조**:
   - `com.deepmirror.server.domain` (Entity 위치)
   - `com.deepmirror.server.repository`
   - `com.deepmirror.server.service`
   - `com.deepmirror.server.controller`
   - `com.deepmirror.server.dto`

## 3. 환경 설정
### 환경변수 파일 (.env)
**중요**: 프로젝트 실행 전 반드시 `.env` 파일을 생성해야 합니다.
1. `.env.example` 파일을 복사: `cp .env.example .env`
2. `.env` 파일에서 실제 값으로 수정:
   - `POSTGRES_PASSWORD`: DB 비밀번호 설정
   - `SPRING_DATASOURCE_PASSWORD`: DB 비밀번호 (위와 동일)
   - `GEMINI_API_KEY`: Google AI Studio에서 발급받은 Gemini API 키
3. **주의**: `.env` 파일은 `.gitignore`에 포함되어 git에 커밋되지 않습니다.

## 4. 빌드 및 실행 명령어
### 빌드
- `chmod +x gradlew` (권한 없을 시)
- `./gradlew build -x test` (테스트 제외 빠른 빌드 - MVP 단계 권장)

### 실행
- **Docker (메인)**: `docker compose up -d --build` (DB + App 실행)
- **Log 확인**: `docker compose logs -f app`
- **DB 재설정**: `docker compose down -v` (데이터 초기화 필요시만 사용)

### 로컬 테스트 (단위 테스트)
- `./gradlew test`

## 5. 데이터베이스 설정
- **DB**: PostgreSQL 15 (Docker Service: `db`)
- **접속 정보**:
  - URL: `jdbc:postgresql://db:5432/deepmirror` (내부), `localhost:5432` (외부)
  - User: `${POSTGRES_USER}` (환경변수로 관리)
  - Password: `${POSTGRES_PASSWORD}`
- **JPA**: `ddl-auto: update` (자동 테이블 관리)

## 6. 아키텍처 및 개발 패턴
1. **Entity**: `domain/` 패키지에 `@Entity` 클래스 생성.
2. **Repository**: `repository/`에 `JpaRepository` 상속 인터페이스 생성.
3. **DTO**: Entity를 직접 반환하지 말고, `dto/` 패키지의 DTO 객체로 변환하여 반환.
4. **API**: REST Controller 사용.

## 7. API 문서 (Swagger UI)
- **접속 URL**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- Spring Boot 실행 후 브라우저에서 Swagger UI로 API 테스트 가능

## 8. 보안 주의사항
- **절대 금지**: `.env` 파일을 git에 커밋하지 마십시오.
- **API 키 관리**: Gemini API 키는 반드시 `.env` 파일로 관리
- **비밀번호**: 운영 환경에서는 강력한 비밀번호로 변경 필수
- **포트**: 호스트의 8080 포트를 사용합니다.
- **데이터베이스**: 외부 포트 노출 없이 내부 네트워크만 사용
- **커밋**: 커밋은 사용자 요청시에만 실행합니다.
