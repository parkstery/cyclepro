# Ride the World Pro | L2-07-02 | Provider Decision Log

## Navigation
- 상위: `RTW_PRO_L1_07_EXTERNAL_SERVICES_REPORT.md`
- 하위: 없음

## 목적
- 미확정 외부 제공자(Map SDK/StreetView/Push)의 선정 근거와 결정을 추적 가능하게 기록한다.

## 공통 선정 기준
- 기능 적합성: 필수 기능 충족 여부(지도/거리뷰/알림)
- 안정성: 장애율, SDK 안정성, 운영 사례
- 성능: 렌더/응답 지연, 모바일 배터리/메모리 영향
- 비용: 월간 예상 트래픽 기준 비용/한도
- 운영성: 모니터링/장애 대응/키 관리 편의성
- 확장성: 글로벌 확장/멀티 채널 지원 가능성

## 결정 대상 A: Map SDK / StreetView
- 후보:
  - Option A: Google Maps SDK + Street View
  - Option B: Mapbox Maps + 대체 파노라마 공급자 조합
- 필수 요구사항:
  - 지도 렌더 안정성(첫 렌더/재시도)
  - 거리뷰 미커버리지 폴백 지원
  - Android SDK 유지보수 주기/문서 품질
- 점수표 (5점 만점, 가중치: 비용 30 / 성능 40 / 운영성 30):

| 후보 | 비용(30) | 성능(40) | 운영성(30) | 가중 합계(100) | 코멘트 |
|---|---:|---:|---:|---:|---|
| Google Maps SDK + Street View | 3.0 | 4.5 | 4.5 | 402 | Android 성숙도/문서/운영 사례 우수 |
| Mapbox + 대체 파노라마 | 3.5 | 3.8 | 3.4 | 357 | 커스텀 유연성은 높지만 통합 복잡도 증가 |
- 결정:
  - 선택안: Google Maps SDK + Street View
  - 결정일: 2026-04-14
  - 승인자: PM, Tech Lead
  - 근거: 성능/운영성에서 우위이며 Baseline의 StreetView 폴백 정책과 직접 연동이 용이함

## 결정 대상 B: Push Provider
- 후보:
  - FCM
  - 기타 후보: OneSignal
- 필수 요구사항:
  - 예약/정기 이벤트 알림
  - 토픽/세그먼트 발송
  - 전송 성공률/실패 사유 추적
- 점수표 (5점 만점, 가중치: 비용 30 / 성능 35 / 운영성 35):

| 후보 | 비용(30) | 성능(35) | 운영성(35) | 가중 합계(100) | 코멘트 |
|---|---:|---:|---:|---:|---|
| FCM | 5.0 | 4.2 | 4.6 | 458 | Firebase 생태계 일관성, 운영 단순성 우수 |
| OneSignal | 3.7 | 4.0 | 4.1 | 393 | 기능 풍부하나 추가 운영 계층 필요 |
- 결정:
  - 선택안: FCM
  - 결정일: 2026-04-14
  - 승인자: PM, Tech Lead
  - 근거: 비용 효율/운영성 우위, KPI/Analytics 파이프라인과 통합 리드타임 최소

## 최종 선정안 요약
- Map/StreetView: Google Maps SDK + Street View
- Push: FCM
- 적용 범위:
  - 베타 릴리스 기본 제공자
  - 예외 상황 시 인앱 알림 및 지도 단독 모드 폴백 유지
- 코드 반영 상태:
  - Auth: `FirebaseAuthGateway`, `FirestoreUserProfileGateway`
  - Routing/Elevation: `OsrmHttpRoutingApi`, `OpenElevationHttpApi`
  - Push: `FcmPushNotifier`
  - Provider Config/Health: `AuthProviderConfig`, `AuthProviderHealthChecker`, `MapProviderConfig`, `FcmTopicSubscriptionManager`

## 변경 이력
| ID | Date | Target | Decision | Reason | Approved By |
|---|---|---|---|---|---|
| PDL-001 | 2026-04-14 | Process | Decision log initialized | E7-04 completion baseline | PM/Tech Lead |
| PDL-002 | 2026-04-14 | Map/StreetView | Google Maps SDK + Street View selected | 성능/운영성 가중 점수 우위 | PM/Tech Lead |
| PDL-003 | 2026-04-14 | Push | FCM selected | 비용/운영성 가중 점수 우위 | PM/Tech Lead |
