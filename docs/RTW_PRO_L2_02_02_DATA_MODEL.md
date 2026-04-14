# Ride the World Pro | L2-02-02 | Data Model

## Navigation
- 상위: `RTW_PRO_L1_02_FOUNDATION.md`
- 하위: 없음

## 목적
- 도메인 엔터티와 저장 스키마를 통일한다.

## 엔터티
- UserProfile(uid, displayName, createdAt)
- Room(roomId, hostUid, routeId, status, startAt)
- Participant(uid, roomId, state, lastSeenAt)
- RaceState(roomId, riders, ranking, updatedAt)
- RaceResult(roomId, uid, rank, elapsedTimeSec)

## 규칙
- 상태값은 enum으로 제한한다.
- 서버 시각 필드를 기준으로 정렬한다.
- `RoomStatus`, `ParticipantState`는 미정의 문자열을 허용하지 않는다.
- `createdAt`, `startAt`, `lastSeenAt`, `updatedAt`는 epoch millis 기준으로 통일한다.
