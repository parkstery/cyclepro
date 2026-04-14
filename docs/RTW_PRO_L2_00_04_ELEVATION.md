# Ride the World Pro | L2-00-04 | Elevation

## Navigation
- 상위: `RTW_PRO_L1_00_BASELINE_CORE.md`
- 하위: 없음

## 목적
- Open Elevation 기반 표고 조회 및 고도 프로파일 계산 규칙을 정의한다.

## 핵심 규칙
- 경로 샘플링 간격을 고정해 요청량을 제어한다.
- 누락값 발생 시 보간/대체 규칙을 적용한다.
- 누적 상승고도/하강고도 계산 방식을 통일한다.
- 샘플 수는 거리 구간별 권장값(`100/120/160`)을 기준으로 선택한다.
- 어댑터 경계는 `OpenElevationApi` 인터페이스로 분리한다.
- 통합 계산은 `ElevationProfileCalculator`에서 단일 기준으로 수행한다.

## QA 체크리스트
- [ ] 고도 조회 성공률 확인
- [ ] 누락값 처리 확인
- [ ] 차트/수치 일관성 확인
