# CLAUDE.md

DeepMirror 프로젝트를 위한 Claude Code 가이드

## 1. 프로젝트 개요
성격 분석 및 미러링을 위한 Spring Boot 웹 애플리케이션

**Tech Stack**: Java 17, Spring Boot 3.4.0, PostgreSQL 15, Gemini AI, Thymeleaf, Docker Compose

## 2. 코딩 컨벤션 (필수 ★★★)
### NO LOMBOK 규칙
- Lombok 어노테이션(@Data, @Getter, @Builder 등) **절대 사용 금지**
- Getter, Setter, 생성자, Builder는 **직접 작성**
- 이유: 코드 명시성 및 순수 Java 구조 유지

### 기타 규칙
- **생성자 주입**: `@Autowired` 대신 `final` 필드 + 생성자 사용
- **DTO 사용**: Entity를 직접 반환하지 말고 DTO로 변환
- **패키지 구조**: `com.deepmirror.server.{domain, repository, service, controller, dto}`

## 3. 환경 설정
### 필수 환경변수 (.env)
`.env.example` 파일을 복사하여 `.env` 파일 생성 후 실제 값으로 변경:

```bash
cp .env.example .env
```

**데이터베이스 설정**:
- `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD`: PostgreSQL 설정
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`: Spring Boot DB 연결

**애플리케이션 설정**:
- `GEMINI_API_KEY`: Gemini API 키 (https://aistudio.google.com/app/apikey)
- `JPA_DDL_AUTO`: 개발=update, 프로덕션=validate/none
- `CLOUDFLARE_TUNNEL_TOKEN`: HTTPS 접근용 토큰
- `DISCORD_WEBHOOK_URL`: 피드백 알림 (선택사항)

## 4. 빌드 및 실행
### 빌드
```bash
./gradlew build -x test
```

### Docker 실행
```bash
# 전체 실행 (DB + App + Cloudflare Tunnel)
docker compose up -d --build

# 로그 확인
docker compose logs -f app
docker compose logs -f tunnel

# DB 초기화 (주의: 데이터 삭제됨)
docker compose down -v
```

### 접속
- **로컬**: http://localhost:8080
- **프로덕션**: Cloudflare Tunnel (HTTPS)
- **API 문서**: http://localhost:8080/swagger-ui.html

## 5. 아키텍처
**표준 Spring Boot 레이어드 아키텍처**:
- Controller/DTO ↔ Service ↔ Repository/Entity

**주요 컴포넌트**:
- `Result`: 성격 분석 결과 저장
- `Feedback`: 사용자 피드백 수집 + Discord 알림
- `GeminiService`: Gemini AI 성격 분석 (gemini-3-flash-preview)

## 6. 보안 주의사항
### 개발 환경
- `.env` 파일 git 커밋 **절대 금지**
- 민감 정보는 반드시 `.env`로 관리 (API 키, DB 비밀번호, Webhook URL, Tunnel 토큰)

### 프로덕션 환경
**이미 적용된 보안 설정** (docker-compose.yml):
- 포트 바인딩: 127.0.0.1 전용 (외부 직접 접근 차단)
- Cloudflare Tunnel: HTTPS 자동 적용, DDoS 방어
- 에러 메시지 숨김: application.yml 설정 완료

**운영 시 필수 조치**:
- 강력한 비밀번호 사용 (16자 이상)
- JPA_DDL_AUTO를 `validate` 또는 `none`으로 변경
- API 키 및 토큰 정기 갱신
- 환경별 `.env` 파일 분리 관리

## 7. Git 커밋 규칙
- **커밋 타이밍**: 사용자 요청 시에만
- **커밋 메시지**: 한글로 간결하고 명확하게
