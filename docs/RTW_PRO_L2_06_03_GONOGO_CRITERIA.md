# Ride the World Pro | L2-06-03 | GoNoGo Criteria

## Navigation
- 상위: `RTW_PRO_L1_06_BETA_RELEASE.md`
- 하위: 없음

## Go
- KPI 목표를 2주 연속 만족
- 필수 품질 게이트(로그인/레이스/센서)에서 blocker 이슈가 0건
- 경쟁 정확도 지표(추월 반영 정확도, 순위 일관성)가 목표 이상

## Conditional Go
- 일부 KPI 미달이지만 원인과 개선 일정이 확정
- 미달 KPI가 2개 이하이며 1주 내 개선 실험 계획이 승인됨

## No-Go
- 로그인/레이스/센서 핵심 안정성 미달
- 동기화 P95 또는 경쟁 정확도 지표가 연속 2주 임계 미달
- 베타 사용자 불만 상위 이슈가 재현 가능 상태로 미해결

## 구현 기준
- 판정기: `BetaGoNoGoEvaluator`
- 입력: 최근 2주 `BetaKpiSnapshot`
