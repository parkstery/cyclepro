# Ride the World Pro | L2-00-03 | OSRM Routing

## Navigation
- 상위: `RTW_PRO_L1_00_BASELINE_CORE.md`
- 하위: 없음

## 목적
- 경로 탐색 요청/응답/실패 처리 기준을 명확히 한다.

## 핵심 규칙
- 출발/도착/경유지로 경로를 조회한다.
- 실패 시 제한된 재시도 후 사용자에게 실패를 알린다.
- 경로 캐시를 적용해 중복 요청을 줄인다.
- 모드 문자열은 `RouteMode`로 정규화 후 OSRM profile path로 매핑한다.
- 어댑터 경계는 `OsrmRoutingApi` 인터페이스로 분리한다.
- 재시도 실행은 `RetryExecutor`/`RetryPolicy`를 통해 일관 처리한다.

## QA 체크리스트
- [ ] 정상 경로 조회
- [ ] 실패/재시도 동작
- [ ] 거리/시간 파싱 정확성
