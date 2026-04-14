# Ride the World Pro | L2-02-01 | Architecture

## Navigation
- 상위: `RTW_PRO_L1_02_FOUNDATION.md`
- 하위: 없음

## 목적
- 앱 계층/모듈 경계를 확정한다.

## 계층
- Presentation(Compose UI)
- Domain(UseCase/Rules)
- Data(Firebase/OSRM/Elevation/BLE)

## 규칙
- UI는 도메인 로직을 직접 호출하지 않는다.
- 네트워크/센서는 Data 계층으로 캡슐화한다.
- Domain은 외부 SDK 타입을 직접 참조하지 않는다.
- 외부 연동은 Port/Gateway 인터페이스로 경계 분리한다.
