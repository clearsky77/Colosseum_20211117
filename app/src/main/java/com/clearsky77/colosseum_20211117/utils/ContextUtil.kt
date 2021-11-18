package com.clearsky77.colosseum_20211117.utils

import android.content.Context

class ContextUtil {

    companion object{
        private val prefName = "ColosseumPref" // 비유: 메모장 이름
        private val TOKEN = "TOKEN"

        fun setToken(context: Context, token: String){
            // 1. 메모장 열기 prefName에 해당하는 SharedPre열기
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE) //이 파일 열어줘
            // 2. 토큰값을 TOKEN 할ㅇ목에 저장
            pref.edit().putString(TOKEN, token).apply()

        }
    }
}