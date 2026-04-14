# Ride the World Pro | L2-05-04 | Usability Test Plan

## Navigation
- 상위: `RTW_PRO_L1_05_UX.md`
- 하위: 없음

## 테스트 시나리오
- 사용자가 방을 찾고 참가한다.
- 카운트다운 후 주행을 완료한다.
- 결과 화면에서 기록 비교를 확인한다.
- 결과 화면에서 추월 타임라인과 구간별 편차를 해석한다.
- 다음 이벤트 재참여(즉시 참가 또는 알림 설정)를 수행한다.

## 측정 지표
- 방 입장까지 걸린 시간
- 주행 중 오조작 횟수
- 완주율
- 재참여 의향 점수
- 결과 화면 핵심 인사이트 이해도 점수(자기평가 5점 척도)
- 센서 인증 배지 의미 이해도(공정성 인식 점수)

## 계측 이벤트 매핑
- `ROOM_JOIN_STARTED` / `ROOM_JOIN_COMPLETED`: 방 입장 시간 측정
- `SOCIAL_EVENT_SHOWN`: 소셜 이벤트 노출 빈도/맥락 확인
- `HUD_CORE_VIEWED`, `HUD_AUX_EXPANDED`: CORE/AUX 정보 접근성 확인
- `POST_RACE_SUMMARY_VIEWED`, `POST_RACE_DRILLDOWN_OPENED`: 인사이트 소비 패턴 확인
- `POST_RACE_RETRY_TAPPED`, `POST_RACE_REMINDER_SET`: 재참여 액션 추적

## 수행 절차
1. 테스트 시작 전 계측 로그 수집 ON 확인
2. 시나리오 1~5 순차 수행(중단 시 사유 기록)
3. 시나리오 종료 직후 이해도/의향 점수 설문 수집
4. 로그와 설문을 동일 세션 ID로 매칭
5. 이슈를 `치명/중요/개선`으로 분류

## 합격 기준
- 방 입장 중앙값 60초 이내
- 결과 화면 핵심 인사이트 이해도 평균 4.0/5.0 이상
- 테스트 참가자의 재도전/알림설정 액션 실행률 50% 이상
