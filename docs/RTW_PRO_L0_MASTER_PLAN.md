# Ride the World Pro | L0 | Master Plan

## Navigation
- 상위: 없음
- 하위: `RTW_PRO_L1_00_BASELINE_CORE.md`, `RTW_PRO_L1_01_SCOPE.md`, `RTW_PRO_L1_02_FOUNDATION.md`, `RTW_PRO_L1_03_ROOM_RACE.md`, `RTW_PRO_L1_04_SENSOR.md`, `RTW_PRO_L1_05_UX.md`, `RTW_PRO_L1_06_BETA_RELEASE.md`, `RTW_PRO_L1_07_EXTERNAL_SERVICES_REPORT.md`

## 비전
- 전 세계 실제 도로 기반의 실내 라이딩 경험을 멀티 유저 경쟁/커뮤니티 경험으로 확장한다.
- 화려한 3D 대체보다 동시성, 안정성, 재참여를 우선한다.
- Pro 버전의 성공 기준은 "안정적 완주"와 "신뢰 가능한 경쟁 체감"을 동시에 충족하는 것이다.

## 아키텍처 원칙
- 클라이언트: Android Native(Kotlin + Compose)
- 인증/기본 데이터: Firebase(Auth + 메타 데이터)
- 실시간 경쟁: 초기 저주기 동기화 -> 고주기 필요 시 WebSocket 분리
- 경로/고도: OSRM + Open Elevation 유지
- 거리뷰: 몰입 배경 역할, 경쟁 정보는 HUD/맵 중심

## 단계 로드맵
- L1-00 Baseline Core(지도/거리뷰/경로/고도 parity)
- L1-01 Scope
- L1-02 Foundation
- L1-03 Room Race
- L1-04 Sensor
- L1-05 UX
- L1-06 Beta Release
- L1-07 External Services Report

## 운영 원칙
- 문서 변경 순서: `L0 -> L1 -> L2`
- 기능 추가 시 MVP/후순위(R&D) 분리 유지
- KPI는 안정성 지표와 Pro 가치 지표(경쟁 정확도/재참여)를 함께 관리한다.
