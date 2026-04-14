# Ride the World Pro | L2-02-03 | Auth Flow

## Navigation
- 상위: `RTW_PRO_L1_02_FOUNDATION.md`
- 하위: 없음

## 목적
- Firebase Auth 기반 로그인 흐름을 표준화한다.

## 플로우
1. 앱 시작 시 기존 세션 확인
2. 무세션이면 Google 로그인
3. 성공 시 프로필 업서트 후 홈 진입
4. 실패/만료 시 재로그인 유도

## QA 체크리스트
- [ ] 첫 로그인
- [ ] 자동 로그인
- [ ] 토큰 만료 복구
