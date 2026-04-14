# Ride the World Pro | L2-00-06 | Baseline QA Checklist

## Navigation
- 상위: `RTW_PRO_L1_00_BASELINE_CORE.md`
- 하위: 없음

## 공통 합격 규칙
- 각 항목은 Pass/Fail와 근거 로그를 기록한다.
- 도시/교외/고저차/단거리/장거리 코스로 반복 검증한다.

## 체크리스트
- [ ] 지도 첫 렌더 시간 기준 충족
- [ ] 거리뷰 진입/전환 성공률 기준 충족
- [ ] OSRM 경로 요청 실패 시 재시도/에러 처리 동작
- [ ] Elevation 누락값 처리 및 표시 일관성 확인
- [ ] 단일 라이딩 루프(시작-주행-종료) 안정성 확인
- [ ] 네트워크 단절 후 복구 시나리오 통과

## 게이트
- 필수 항목 Fail 0건일 때만 L1-03(Room Race) 구현 단계로 진입
- 게이트 판정은 `BaselineQaGate` 기준으로 required=true 항목 Fail 여부를 평가한다.
