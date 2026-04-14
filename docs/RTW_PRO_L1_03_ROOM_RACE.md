# Ride the World Pro | L1-03 | Room Race

## Navigation
- 상위: `RTW_PRO_L0_MASTER_PLAN.md`
- 하위: `RTW_PRO_L2_03_01_STATE_MACHINE.md`, `RTW_PRO_L2_03_02_SYNC_POLICY.md`, `RTW_PRO_L2_03_03_RESULT_RULES.md`, `RTW_PRO_L2_03_04_COMPETITION_ACCURACY_METRICS.md`

## 상태 전이
- `waiting`: 방 생성, 참가 가능
- `countdown`: 서버 시각 기준 출발 카운트다운
- `running`: 센서/가상 입력으로 진행거리 갱신
- `ended`: 완주 또는 시간 종료 후 결과 확정

## 주요 시나리오
1. 방장 생성 -> 코스/시작시간 설정
2. 참가자 입장 -> 준비 상태 전환
3. 카운트다운 시작(서버 시간 동기화)
4. 주행 중 위치/순위 업데이트
5. 완주 및 결과 저장

## 동기화 규칙(초기)
- 클라이언트 업로드: 1~2Hz
- 서버 계산 주기: 1Hz
- UI 보간(Interpolation)으로 시각적 끊김 최소화
- 베타에서는 평균 지연뿐 아니라 P95/P99 지연을 함께 모니터링한다.

## 예외 처리
- 네트워크 끊김: 마지막 유효 속도 기반 짧은 예측 이동 후 정지
- 재입장: 동일 roomId로 RaceState 스냅샷 복구
- 중도 이탈: `DNF` 상태로 결과 기록

## 순위 계산 규칙
- 1순위: 진행거리(미터)
- 2순위: 동일 진행거리일 때 서버 수신 시각
- 완주자는 완주 시각 기준으로 최종 순위 고정

## 완료 기준
- room lifecycle이 문서상 완결된다.
- 복구/이탈/동기화 정책이 포함되어 QA 가능 상태가 된다.
- 경쟁 정확도 측정 항목(추월/갭/순위 일관성)이 정의되어 운영 대시보드와 연결된다.
