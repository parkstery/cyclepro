package com.rtw.pro.map.domain

import com.rtw.pro.map.data.MapBindErrorCode

object MapUiMessagePolicy {
    fun bindMessage(errorCode: MapBindErrorCode?): String {
        return when (errorCode) {
            null -> "지도가 정상 준비되었습니다."
            MapBindErrorCode.MAP_API_KEY_MISSING -> "지도 설정 키가 누락되었습니다."
            MapBindErrorCode.STREETVIEW_CONFIG_INVALID -> "거리뷰 설정값이 유효하지 않습니다."
            MapBindErrorCode.LOCATION_PERMISSION_DENIED -> "위치 권한이 필요합니다. 권한을 허용해 주세요."
            MapBindErrorCode.MAP_SDK_INIT_FAILED -> "지도 SDK 초기화에 실패했습니다."
            MapBindErrorCode.STREETVIEW_SDK_INIT_FAILED -> "거리뷰 초기화에 실패해 지도 모드로 전환합니다."
            MapBindErrorCode.UNKNOWN -> "지도 준비 중 오류가 발생했습니다."
        }
    }
}
