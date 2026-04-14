# free_logic_snapshot

프리버전에서 검증된 핵심 로직을 Pro 개발 시 참조하기 위한 스냅샷 모음입니다.

## 원칙
- 이 폴더는 `reference-only` 입니다. 런타임 코드로 직접 사용하지 않습니다.
- Pro 구현은 `domain`/`data`에서 새로 작성하고, 이 스냅샷은 동등성 검증 기준으로 사용합니다.
- 각 파일 상단의 Source/Purpose/AdoptedIn/Status/LastSynced 메타를 유지합니다.

## 포함 대상
- `geo/`: 경로 디코딩, 거리/방위/오프셋 계산
- `routing/`: OSRM 프로파일 분기, 스냅, 재시도 규칙
- `elevation/`: 샘플링, 캐시 키, Open Elevation 조회 규칙
- `streetview/`: 거리뷰 안정 표시 조건/폴백/방향 필터 규칙

## Traceability Rule
- 모든 스냅샷 문서는 아래 3개 참조 링크를 포함해야 합니다.
  - `SourceId`: 프리버전 원본 규칙 식별자(직접 경로 비노출)
  - `ProL1`: Pro 상위 계획 문서
  - `ProL2`: Pro 세부 구현/정책 문서

## Anti-CopyPaste Policy
- 스냅샷은 동작 기준 참조용이며 직접 복붙 금지
- 각 파일 메타에 아래 항목을 포함합니다.
  - `DoNotCopyPaste: true`
  - `UseAs: behavior reference and parity tests`
- 원본 경로는 이 폴더의 `source_index.md`에만 보관합니다.
