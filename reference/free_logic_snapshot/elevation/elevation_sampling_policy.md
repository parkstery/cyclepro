# Elevation Sampling Policy (Reference)

- SourceId: `FREE-ELEV-001`
- Purpose: 표고 샘플링 수와 캐시 기준 로직 문서화
- AdoptedIn: Pro elevation policy
- Status: reference-only
- DoNotCopyPaste: `true`
- UseAs: `behavior reference and parity tests`
- LastSynced: 2026-04-14

## Reference Links
- SourceId: `FREE-ELEV-001`
- ProL1: `c:/20.HDev/cycle_pro/docs/RTW_PRO_L1_00_BASELINE_CORE.md`
- ProL2: `c:/20.HDev/cycle_pro/docs/RTW_PRO_L2_00_04_ELEVATION.md`

## Baseline
- 기본 샘플 수: `100`
- 샘플링 방식: 경로 인덱스 균등 샘플링
- 캐시 키: 샘플 좌표를 소수점 5자리로 직렬화

## Pro 권장 운영값
- 단거리(<= 5km): 80~100
- 중거리(5~20km): 100~140
- 장거리(>20km): 140~180 (요청량/응답시간 점검 필수)

## Validation
- 동일 코스 재요청 시 캐시 hit 동작 확인
- 누적 상승고도/하강고도 오차 허용범위 정의 후 검증
