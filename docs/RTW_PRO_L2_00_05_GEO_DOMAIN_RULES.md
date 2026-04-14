# Ride the World Pro | L2-00-05 | Geo Domain Rules

## Navigation
- 상위: `RTW_PRO_L1_00_BASELINE_CORE.md`
- 하위: 없음

## 목적
- 거리, 진행률, heading, 보간 계산 규칙을 표준화한다.

## 핵심 규칙
- 거리 계산은 누적 거리 단조 증가를 보장한다.
- 진행률은 경로 인덱스와 누적 거리 기반으로 계산한다.
- heading은 경로 방향 일관성을 유지한다.

## QA 체크리스트
- [ ] 역전/점프 이상치 확인
- [ ] 단위 변환 정확성 확인
- [ ] 보간 안정성 확인
