# Ride the World Pro | L2-03-02 | Sync Policy

## Navigation
- 상위: `RTW_PRO_L1_03_ROOM_RACE.md`
- 하위: 없음

## 목적
- 실시간 동기화 주기와 보간 정책을 정의한다.

## 정책
- 클라이언트 업로드 1~2Hz
- 서버 집계 1Hz
- UI 보간으로 중간 프레임 생성

## 장애 대응
- 일정 시간 무수신 시 stale 상태 표시
- 재수신 시 서버 스냅샷으로 정합성 복구
- 구현 기준: `SyncPolicy`, `SyncInterpolator`
- 단절 시 짧은 예측 이동은 `PredictionPolicy.maxPredictMs` 범위 내에서만 허용한다.
- 재입장 복구는 room snapshot에서 동일 uid 상태를 우선 복원한다.
