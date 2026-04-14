# Routing Mode Policy (Reference)

- SourceId: `FREE-ROUTE-001`
- Purpose: 차/자전거/걸음 모드 분기 및 URL 규칙 기준
- AdoptedIn: Pro route planner policy
- Status: reference-only
- DoNotCopyPaste: `true`
- UseAs: `behavior reference and parity tests`
- LastSynced: 2026-04-14

## Reference Links
- SourceId: `FREE-ROUTE-001`
- ProL1: `c:/20.HDev/cycle_pro/docs/RTW_PRO_L1_00_BASELINE_CORE.md`
- ProL2: `c:/20.HDev/cycle_pro/docs/RTW_PRO_L2_00_03_OSRM_ROUTING.md`

## Mode -> OSRM Base
- `driving` (default): `.../routed-car`
- `cycling` or `bike`: `.../routed-bike`
- `foot` or `walk`: `.../routed-foot`

## Request Rules
- 좌표는 `lng,lat;lng,lat` 형식 사용
- OSRM route 요청은 `overview=full`, `geometries=polyline` 고정
- 사전 nearest 스냅 반경: `50m`
- route 400 응답 시 원본 좌표로 1회 재요청

## Notes for Pro
- UI의 모드 이름과 내부 profile 문자열을 분리해 매핑한다.
- 서버/클라이언트 중 한 곳에서만 최종 프로파일 결정을 담당한다.
