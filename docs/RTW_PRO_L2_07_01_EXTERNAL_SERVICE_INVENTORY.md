# Ride the World Pro | L2-07-01 | External Service Inventory

## Navigation
- 상위: `RTW_PRO_L1_07_EXTERNAL_SERVICES_REPORT.md`
- 하위: 없음

## 목적
- 외부 서비스별 연계 지점, 장애 시 영향, 폴백 규칙을 세부 정의한다.

## 1) Identity & Account
- 서비스: Google Sign-In + Firebase Auth
- 연계 기능: 로그인, 세션 확인, 토큰 만료 복구
- 계획서 근거: `L1-01 Scope`, `L1-02 Foundation`, `L2-02-03 Auth Flow`
- 코드 경계: `foundation/domain/auth/AuthGateway`, `EnsureSignedInUseCase`
- 장애 영향: 로그인 실패, 홈 진입 차단
- 폴백/대응: 재로그인 유도, 명확한 오류 메시지, 재시도 동선 보장

## 2) Data & Realtime
- 서비스: Firestore, Realtime DB
- 연계 기능: 프로필/메타 저장, 룸/레이스 초기 실시간 동기화
- 계획서 근거: `L1-02 Foundation`
- 코드 경계: `foundation/domain/model/*`, `roomrace/domain/sync/*`
- 장애 영향: 방 입장/상태 동기화 실패, 결과 저장 지연
- 폴백/대응: 재시도 정책, 로컬 임시 상태 유지, 동기화 복구 처리

## 3) Observability
- 서비스: Firebase Crashlytics, Firebase Analytics
- 연계 기능: 크래시 추적, KPI 이벤트 계측
- 계획서 근거: `L1-02 Foundation`, `L1-06 Beta`
- 코드 경계: `ux/domain/analytics/*`, `beta/domain/BetaWeeklyReportBuilder`
- 장애 영향: 품질 이슈 탐지 지연, Go/No-Go 판단 근거 약화
- 폴백/대응: 핵심 이벤트 로컬 버퍼링, 주간 수동 검증 리포트 보완

## 4) Routing
- 서비스: OSRM
- 연계 기능: 경로 탐색, 경로 좌표/거리/시간 계산
- 계획서 근거: `L0 Master`, `L1-00 Baseline Core`, `L2-00-03 OSRM Routing`
- 코드 경계: `baseline/data/routing/OsrmRoutingApi`, `OsrmRoutingService`
- 장애 영향: 코스 생성/주행 준비 실패
- 폴백/대응: 제한 재시도, 캐시 활용, 실패 시 사용자 안내

## 5) Elevation
- 서비스: Open Elevation
- 연계 기능: 표고 조회, 누적 상승/하강 계산
- 계획서 근거: `L0 Master`, `L1-00 Baseline Core`, `L2-00-04 Elevation`
- 코드 경계: `baseline/data/elevation/OpenElevationApi`, `OpenElevationService`, `ElevationProfileCalculator`
- 장애 영향: 고도 프로파일 부정확, 결과 분석 신뢰도 하락
- 폴백/대응: 누락 보간 규칙, 샘플링 간격 제한, 고도 없는 모드 안내

## 6) Map & Street View
- 서비스: 지도 SDK/거리뷰 제공자(문서상 명시, 구현체 확정 필요)
- 연계 기능: 지도 렌더링, 거리뷰 진입/전환, 미커버리지 안내
- 계획서 근거: `L0 Master`, `L1-00 Baseline Core`, `L2-00-01`, `L2-00-02`
- 코드 경계: `baseline/ui/MapFirstPaintCoordinator`, `ui/streetview/StreetViewTransitionCoordinator`
- 장애 영향: 몰입 배경/주행 맥락 손실, 전환 UX 저하
- 폴백/대응: 지도 단독 모드 유지, 파노 없음 안내, 전환 안정성 QA

## 7) Sensor Device Integration
- 서비스: BLE CSC 센서(외부 디바이스/표준 프로파일)
- 연계 기능: 속도/케이던스 수집, 재연결, 가상 입력 대체
- 계획서 근거: `L1-04 Sensor`, `L2-04-01 BLE CSC Spec`
- 코드 경계: `sensor/domain/SensorConnectionCoordinator`, `SensorSignalFilter`, `VirtualInputProvider`
- 장애 영향: 실시간 주행 데이터 품질 저하, 경쟁 공정성 체감 하락
- 폴백/대응: 자동 재연결, 실패 시 가상 입력 전환, 인증 주행 분리 표기

## 8) Notification Channel (TBD)
- 서비스: 푸시 알림 제공자(FCM 우선 검토 권장)
- 연계 기능: 정기 이벤트 사전 알림, 재참여 유도
- 계획서 근거: `L1-05 UX`, `L2-06-04 Retention Experiments`
- 코드 경계(예정): `ux/domain/analytics/UxInstrumentation` 재참여 이벤트와 연동
- 장애 영향: 이벤트 도달률 하락, 재참여율 개선 한계
- 폴백/대응: 인앱 알림 보강, 알림 시간대 실험, 채널 확정 로드맵 수립

## 9) Future Realtime Channel (Conditional)
- 서비스: WebSocket 서버(조건부 도입)
- 연계 기능: 고주기 위치 동기화 및 경쟁 체감 정밀화
- 계획서 근거: `L0 Master`, `L1-03 Room Race`
- 코드 경계(전환 대상): `roomrace/domain/sync/*`
- 장애 영향: 도입 시 운영 복잡도/비용 증가, 미도입 시 고부하 시 체감 지연 상승
- 폴백/대응: Realtime DB 유지 + 단계적 분리, 전환 트리거 기준 사전 정의

## 완료 기준
- 각 외부 서비스에 대해 연계 기능/장애 영향/폴백 규칙이 정의된다.
- 베타 운영 KPI와 연결 가능한 서비스 인벤토리가 완성된다.
- 미확정 제공자 결정은 `L2-07-02 Provider Decision Log`에서 추적한다.
