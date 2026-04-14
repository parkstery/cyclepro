# StreetView Stable Display Rules (Reference)

- SourceId: `FREE-SV-001`
- Purpose: 주행 방향 거리뷰 안정 표시 조건/폴백 규칙 기준 문서화
- AdoptedIn: Pro streetview policy + selector module
- Status: reference-only
- DoNotCopyPaste: `true`
- UseAs: `behavior reference and parity tests`
- LastSynced: 2026-04-14

## Reference Links
- SourceId: `FREE-SV-001`
- ProL1: `c:/20.HDev/cycle_pro/docs/RTW_PRO_L1_00_BASELINE_CORE.md`
- ProL2: `c:/20.HDev/cycle_pro/docs/RTW_PRO_L2_00_02_STREETVIEW_DISPLAY.md`

## Core Timeout / Fallback
- `SV_GET_PANORAMA_TIMEOUT_MS = 6000`
- `PANORAMA_VIEW_TIMEOUT_MS = 6000`
- `UNRECOVERABLE_STATUS = ['OVER_QUERY_LIMIT']`
- 조회 순서: `GOOGLE` 우선 -> 실패 시 `DEFAULT` 폴백
- timeout과 no_pano를 구분해 처리(UX 메시지 분리)

## Direction and Candidate Selection
- Pass1 반경: `50m`, 각도: `±40deg`
- Pass1 완화 각도: `±90deg`
- Pass2 반경: `120m` (방향 제한 없음)
- indoor 추정 제외: `/Shop|Indoor|Business/i`
- 점수(거리/방향 가중치): `0.6 / 0.4`
- 최소 커버리지 기준: `COVERAGE_MIN = 0.7`

## Pro 적용 메모
- 거리뷰 선택 로직은 UI와 분리해 순수 selector 함수로 구현
- no_pano, timeout, over_query_limit을 별도 상태로 모델링
- coverage < min이면 StreetView 비활성/안내 배지 처리
