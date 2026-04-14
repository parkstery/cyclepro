# Ride the World Pro | L2-04-03 | Filtering Rules

## Navigation
- 상위: `RTW_PRO_L1_04_SENSOR.md`
- 하위: 없음

## 목적
- 센서 신호 노이즈를 안정적으로 보정한다.

## 규칙
- 급격한 스파이크는 완화한다.
- 최근 N샘플 이동평균을 적용한다.
- 무수신 구간은 속도 감쇠 후 0 처리한다.
- 비정상 최대값은 무시한다.
- 구현 기준: `SensorSignalFilter`, `FilteringRules`
