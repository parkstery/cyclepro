package com.rtw.pro.notification.domain

import com.rtw.pro.notification.data.FcmErrorCode

object PushUiMessagePolicy {
    fun tokenSyncMessage(errorCode: FcmErrorCode?): String {
        return when (errorCode) {
            null -> "알림 설정이 정상 동기화되었습니다."
            FcmErrorCode.TOKEN_UNAVAILABLE -> "알림 토큰을 아직 받을 수 없습니다. 잠시 후 다시 시도해 주세요."
            FcmErrorCode.NETWORK_ERROR -> "네트워크 문제로 알림 동기화가 지연됩니다."
            FcmErrorCode.UNAUTHORIZED -> "알림 권한이 유효하지 않습니다. 설정을 확인해 주세요."
            FcmErrorCode.INVALID_TOKEN -> "알림 토큰이 유효하지 않아 재발급이 필요합니다."
            FcmErrorCode.UNKNOWN -> "알림 동기화 중 오류가 발생했습니다."
        }
    }

    fun topicSubscriptionMessage(errorCode: FcmErrorCode?, subscribed: Boolean): String {
        if (errorCode == null) {
            return if (subscribed) {
                "이벤트 알림 구독이 완료되었습니다."
            } else {
                "이벤트 알림 구독이 해제되었습니다."
            }
        }
        return when (errorCode) {
            FcmErrorCode.NETWORK_ERROR -> "네트워크 문제로 알림 설정 변경에 실패했습니다."
            FcmErrorCode.UNAUTHORIZED -> "알림 권한이 유효하지 않아 설정 변경에 실패했습니다."
            FcmErrorCode.INVALID_TOKEN -> "토큰 상태가 유효하지 않아 알림 설정 변경에 실패했습니다."
            FcmErrorCode.TOKEN_UNAVAILABLE -> "알림 토큰이 없어 설정 변경을 진행할 수 없습니다."
            FcmErrorCode.UNKNOWN -> "알림 설정 변경 중 오류가 발생했습니다."
        }
    }
}
