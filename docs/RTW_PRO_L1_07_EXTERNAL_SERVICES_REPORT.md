# Ride the World Pro | L1-07 | External Services Report

## Navigation
- 상위: `RTW_PRO_L0_MASTER_PLAN.md`
- 하위: `RTW_PRO_L2_07_01_EXTERNAL_SERVICE_INVENTORY.md`

## 목적
- 계획서에 명시된 앱 연계 외부 서비스를 운영/개발 관점에서 한 번에 확인할 수 있도록 정리한다.

## 외부 서비스 요약
- 인증/사용자: Google 로그인 + Firebase Auth
- 데이터/실시간/로그: Firestore, Realtime DB, Firebase Crashlytics, Firebase Analytics
- 경로/지리 데이터: OSRM, Open Elevation
- 시각화: 지도/거리뷰 서비스(현재 문서 기준 구현 세부는 별도 확정 필요)
- 하드웨어 연동: BLE CSC 센서(외부 디바이스)
- 통신 확장: 고주기 동기화 필요 시 WebSocket 분리 가능성
- 사용자 리텐션: 푸시 알림 채널(FCM 등) 도입 필요

## 서비스별 역할
- Firebase Auth: 로그인, 세션 유지, 토큰 만료 복구
- Firestore/Realtime DB: 프로필/메타 저장, 초기 실시간 동기화
- Crashlytics/Analytics: 장애 추적, KPI 계측
- OSRM: 경로 계산(거리/시간/좌표)
- Open Elevation: 표고 조회, 상승/하강 고도 계산
- 지도/거리뷰: 주행 배경 및 경로 시각화
- BLE CSC: 속도/케이던스 원천 데이터 수집

## 핵심 의존 리스크
- 외부 API 실패/지연 시 사용자 경험 급락(경로, 고도, 거리뷰 진입 실패)
- Firebase 인증/세션 문제 시 앱 진입 차단
- 실시간 동기화 증가 시 Realtime DB만으로 한계 가능, WebSocket 전환 판단 필요
- 센서 연결 품질 편차로 공정성 인식 저하 가능
- 알림 채널 미정의 시 정기 이벤트 재참여율 개선 한계

## 운영 권고
- 외부 서비스별 SLO(성공률/지연/재시도 한계) 문서화
- KPI 대시보드에 서비스 장애 기여도(로그인/경로/고도/센서) 태깅
- WebSocket 분리 트리거 조건(동접, 지연 P95, 추월 정확도) 사전 합의
- 푸시 제공자(FCM) 확정 후 이벤트 알림 실험과 연계

## 완료 기준
- 외부 서비스 목록/역할/리스크/운영 방안이 한 문서에서 확인 가능하다.
- 상세 항목은 `L2-07-01` 인벤토리와 일치한다.
