# Ride the World Pro | L2-00-02 | StreetView Display

## Navigation
- 상위: `RTW_PRO_L1_00_BASELINE_CORE.md`
- 하위: 없음

## 목적
- 거리뷰 진입, 파노라마 전환, 미커버리지 구간 처리 규칙을 정의한다.

## 핵심 규칙
- 파노라마 로딩 타임아웃을 적용한다.
- 파노라마 없음 구간은 안내 메시지로 폴백한다.
- 지도/거리뷰 전환 시 레이아웃 깨짐이 없어야 한다.
- `no_pano`, `timeout`, `over_query_limit`는 분리된 실패 상태로 취급한다.
- coverage 비율이 최소 기준 미만이면 거리뷰를 비활성화하고 지도 모드로 전환한다.

## QA 체크리스트
- [ ] 진입/종료 동작 확인
- [ ] 파노 전환 지연 측정
- [ ] 미커버리지 안내 확인
- [ ] StreetView -> MapOnly 전환 후 레이아웃 안정성(`isLayoutStable`) 확인
