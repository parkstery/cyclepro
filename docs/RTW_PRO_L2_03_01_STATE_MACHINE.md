# Ride the World Pro | L2-03-01 | State Machine

## Navigation
- 상위: `RTW_PRO_L1_03_ROOM_RACE.md`
- 하위: 없음

## 목적
- 룸/레이스 상태 전이를 명확히 정의한다.

## 상태
- waiting -> countdown -> running -> ended

## 전이 규칙
- host start 이벤트로 countdown 진입
- server startAt 도달 시 running 진입
- 완주/종료 조건 만족 시 ended 전이
- 구현 기준: `RoomRaceStateMachine`에서 상태 전이 가드 강제

## 예외
- 연결 끊김: reconnecting 서브 상태 처리
