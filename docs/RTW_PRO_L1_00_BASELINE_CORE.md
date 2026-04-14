# Ride the World Pro | L1-00 | Baseline Core

## Navigation
- 상위: `RTW_PRO_L0_MASTER_PLAN.md`
- 하위: `RTW_PRO_L2_00_01_MAP_DISPLAY.md`, `RTW_PRO_L2_00_02_STREETVIEW_DISPLAY.md`, `RTW_PRO_L2_00_03_OSRM_ROUTING.md`, `RTW_PRO_L2_00_04_ELEVATION.md`, `RTW_PRO_L2_00_05_GEO_DOMAIN_RULES.md`, `RTW_PRO_L2_00_06_BASELINE_QA_CHECKLIST.md`

## 목적
- Pro 개발 시작 전 프리버전의 핵심 기반 기능 parity를 확보한다.

## 범위
- 지도 로딩/경로 표시
- 거리뷰 표시/전환
- OSRM 경로 탐색
- Open Elevation 표고 처리
- 공통 지오 계산(거리/진행률/heading/보간)

## 완료 기준
- 단일 라이딩 루프(시작-주행-종료) 동작
- 네트워크/타임아웃/거리뷰 없음 구간 대응 확인
- 프리버전 대비 거리/시간/평균속도 결과 오차 허용 범위 내

## 연계 L2 문서
- `RTW_PRO_L2_00_01_MAP_DISPLAY.md`
- `RTW_PRO_L2_00_02_STREETVIEW_DISPLAY.md`
- `RTW_PRO_L2_00_03_OSRM_ROUTING.md`
- `RTW_PRO_L2_00_04_ELEVATION.md`
- `RTW_PRO_L2_00_05_GEO_DOMAIN_RULES.md`
- `RTW_PRO_L2_00_06_BASELINE_QA_CHECKLIST.md`
- `RTW_PRO_L2_00_07_STEP1_QA_LOG_TEMPLATE.md`

## 실행 단계 (순차 진행)
### Step 1. Baseline Skeleton + Map First Paint
- 목표: Pro 코드베이스에서 "지도 표시 + 라이딩 루프 골격"이 실제 동작 가능한 최소 단위를 만든다.
- 산출물:
  - Baseline 모듈 구조(도메인/데이터/UI 경계) 정의
  - 지도 초기화/첫 렌더 성공 경로
  - 라이딩 루프 상태(시작-주행-종료) 최소 상태 전이
- 완료 기준:
  - 앱 실행 후 지도 첫 렌더 성공
  - 시작/종료 이벤트로 루프 상태 전환 확인
  - 실패 시 사용자 안내 메시지 노출

### Step 2. Route + Elevation Parity
- 목표: OSRM/Open Elevation 경로를 안정적으로 처리하고 프리버전과 동등성 기준을 맞춘다.
- 산출물:
  - 경로 좌표 로드/표시
  - 표고 샘플링 및 누락 보간 규칙 반영
  - 거리/시간/평균속도 계산 파이프라인
- 완료 기준:
  - 대표 코스에서 결과 오차 허용 범위 내
  - 네트워크 실패/타임아웃에 재시도 정책 동작
- 진행 체크리스트:
  - [x] S2-01 OSRM 모드 매핑/폴리라인 파서 기초 구현
  - [x] S2-02 Elevation 샘플링/캐시 키 규칙 구현
  - [x] S2-03 누락 고도 보간 + 상승/하강 계산 구현
  - [x] S2-04 거리/시간/평균속도 계산 파이프라인 구현
  - [x] S2-05 실제 API 어댑터 연결 구조 및 통합 오케스트레이션 구현

### Step 3. Street View Fallback + Baseline QA Gate
- 목표: 거리뷰 유무와 관계없이 단일 라이딩 루프를 안정 완주 가능하게 만든다.
- 산출물:
  - 거리뷰 진입/전환 로직
  - 미커버리지 구간 폴백(지도 단독 모드)
  - Baseline QA 체크리스트 Pass/Fail 근거 기록
- 완료 기준:
  - 필수 QA 항목 Fail 0건
  - L1-03(Room Race) 진입 승인
- 진행 체크리스트:
  - [x] S3-01 거리뷰 실패 상태 모델(no_pano/timeout/limit) 구현
  - [x] S3-02 미커버리지/실패 시 지도 단독 모드 폴백 구현
  - [x] S3-03 Baseline QA Gate 판정 로직 구현
  - [x] S3-04 UI 전환 어댑터/레이아웃 무결성 검증 경로 구현

## 현재 착수 범위 (Step 1)
- [x] S1-01 모듈/패키지 골격 생성(도메인, 데이터, UI)
- [x] S1-02 지도 첫 렌더/재시도 정책 구현 (`MapFirstPaintCoordinator`, `MapRenderPolicy`)
- [x] S1-03 라이딩 루프 최소 상태 머신(Idle -> Running -> Finished)
- [x] S1-04 사용자 오류 메시지/재시도 동선 구현 (실패 메시지/재시도 콜백)
- [x] S1-05 Step 1 QA 기록 템플릿 작성 (`RTW_PRO_L2_00_07_STEP1_QA_LOG_TEMPLATE.md`)
