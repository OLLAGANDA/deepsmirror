# DeepsMirror

> AI 기반 성격 분석 및 미러링 웹 애플리케이션

DeepsMirror는 Gemini AI를 활용하여 사용자의 성격을 분석하고, 그에 맞는 인사이트를 제공하는 Spring Boot 기반 웹 애플리케이션입니다.

## 주요 기능

- **AI 성격 분석**: Gemini AI(gemini-3-flash-preview)를 활용한 심층 성격 분석
- **실시간 피드백**: Discord Webhook을 통한 사용자 피드백 수집 및 알림
- **안전한 배포**: Cloudflare Tunnel을 통한 HTTPS 자동 적용 및 DDoS 방어
- **API 문서화**: Swagger UI를 통한 대화형 API 문서 제공
- **컨테이너화**: Docker Compose 기반 원클릭 배포

## 기술 스택

### Backend
- **Java 17** - 최신 LTS 버전
- **Spring Boot 3.4.0** - 웹 프레임워크
- **Spring Data JPA** - ORM 및 데이터 액세스
- **Spring AI** - Gemini AI 통합

### Database
- **PostgreSQL 15** - 관계형 데이터베이스

### AI & Integration
- **Gemini AI** - Google의 생성형 AI 모델
- **Discord Webhook** - 실시간 피드백 알림

### Infrastructure
- **Docker & Docker Compose** - 컨테이너화 및 오케스트레이션
- **Cloudflare Tunnel** - 보안 HTTPS 터널링
- **Gradle** - 빌드 도구

### Frontend
- **Thymeleaf** - 서버 사이드 템플릿 엔진

## 시작하기

### 사전 요구사항

- Docker 및 Docker Compose 설치
- Gemini API 키 ([발급하기](https://aistudio.google.com/app/apikey))
- (선택) Discord Webhook URL
- (선택) Cloudflare Tunnel 토큰

### 설치 및 실행

1. **저장소 클론**
```bash
git clone <repository-url>
cd deepsmirror
```

2. **환경 변수 설정**
```bash
cp .env.example .env
```

`.env` 파일을 열어 다음 값을 설정:
```env
# 데이터베이스 설정
POSTGRES_DB=deepmirror
POSTGRES_USER=deepmirror_user
POSTGRES_PASSWORD=your_secure_password

SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/deepmirror
SPRING_DATASOURCE_USERNAME=deepmirror_user
SPRING_DATASOURCE_PASSWORD=your_secure_password

# AI 설정
GEMINI_API_KEY=your_gemini_api_key

# JPA 설정 (개발: update, 프로덕션: validate)
JPA_DDL_AUTO=update

# 선택 사항
DISCORD_WEBHOOK_URL=your_discord_webhook_url
CLOUDFLARE_TUNNEL_TOKEN=your_cloudflare_tunnel_token
```

3. **Docker Compose로 실행**
```bash
# 전체 스택 실행 (DB + App + Cloudflare Tunnel)
docker compose up -d --build

# 로그 확인
docker compose logs -f app
docker compose logs -f tunnel

# 중지
docker compose down

# 데이터베이스 초기화 (주의: 모든 데이터 삭제됨)
docker compose down -v
```

4. **로컬 개발 (Docker 없이)**
```bash
# PostgreSQL이 로컬에서 실행 중이어야 함
./gradlew bootRun
```

### 접속

- **로컬 개발**: http://localhost:8080
- **API 문서**: http://localhost:8080/swagger-ui.html
- **프로덕션**: Cloudflare Tunnel URL (HTTPS 자동 적용)

## 프로젝트 구조

```
src/main/java/com/deepmirror/server/
├── domain/         # Entity 클래스 (Result, Feedback 등)
├── repository/     # JPA Repository 인터페이스
├── service/        # 비즈니스 로직 (GeminiService 등)
├── controller/     # REST API 및 웹 컨트롤러
└── dto/            # 데이터 전송 객체

src/main/resources/
├── application.yml # Spring Boot 설정
├── static/         # 정적 리소스 (CSS, JS, 이미지)
└── templates/      # Thymeleaf 템플릿

docker-compose.yml  # Docker Compose 설정
Dockerfile          # 애플리케이션 컨테이너 이미지
.env.example        # 환경 변수 템플릿
```

## 코딩 컨벤션

### NO LOMBOK 정책
- Lombok 어노테이션 사용 금지 (`@Data`, `@Getter`, `@Builder` 등)
- Getter, Setter, 생성자, Builder는 **직접 작성**
- 이유: 코드 명시성 및 순수 Java 구조 유지

### 기타 규칙
- **생성자 주입**: `@Autowired` 대신 `final` 필드 + 생성자 사용
- **DTO 사용**: Entity를 직접 반환하지 말고 DTO로 변환
- **표준 레이어드 아키텍처**: Controller ↔ Service ↔ Repository

자세한 내용은 [CLAUDE.md](./CLAUDE.md)를 참조하세요.

## 보안

### 개발 환경
- `.env` 파일은 절대 Git에 커밋하지 마세요
- 모든 민감 정보는 환경 변수로 관리

### 프로덕션 환경
이미 적용된 보안 설정:
- 포트 바인딩: `127.0.0.1` 전용 (외부 직접 접근 차단)
- Cloudflare Tunnel: HTTPS, DDoS 방어
- 에러 메시지 숨김 처리

운영 시 필수 조치:
- 강력한 비밀번호 사용 (16자 이상)
- `JPA_DDL_AUTO`를 `validate` 또는 `none`으로 변경
- API 키 및 토큰 정기 갱신

## API 문서

Swagger UI를 통해 모든 API 엔드포인트를 확인하고 테스트할 수 있습니다.

```
http://localhost:8080/swagger-ui.html
```

주요 엔드포인트:
- `POST /api/analyze` - 성격 분석 요청
- `GET /api/results/{id}` - 분석 결과 조회
- `POST /api/feedback` - 피드백 제출

## 빌드

```bash
# 테스트 제외하고 빌드
./gradlew build -x test

# 테스트 포함 빌드
./gradlew build

# JAR 파일 직접 실행
java -jar build/libs/deepmirror-0.0.1-SNAPSHOT.jar
```

## 문제 해결

### Docker 컨테이너가 시작되지 않을 때
```bash
# 로그 확인
docker compose logs app
docker compose logs db

# 컨테이너 재시작
docker compose restart app
```

### 데이터베이스 연결 오류
- `.env` 파일의 데이터베이스 설정 확인
- PostgreSQL 컨테이너가 정상 실행 중인지 확인: `docker compose ps`

### Gemini API 오류
- `GEMINI_API_KEY`가 올바르게 설정되었는지 확인
- API 키의 할당량 및 권한 확인

## 라이선스

이 프로젝트는 개인 프로젝트입니다.

## 기여

버그 리포트나 기능 제안은 Issues를 통해 제출해주세요.

---

**Built with ❤️ using Spring Boot and Gemini AI**
