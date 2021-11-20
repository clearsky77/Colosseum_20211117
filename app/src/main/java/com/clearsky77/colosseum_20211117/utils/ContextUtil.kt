package com.clearsky77.colosseum_20211117.utils

import android.content.Context

class ContextUtil {

    companion object{
        private val prefName = "ColosseumPref" // 비유: 메모장 이름
        private val TOKEN = "TOKEN"
        private val LOGIN_EMAIL = "LOGIN_EMAIL"
        private val AUTO_LOGIN = "AUTO_LOGIN"

        fun setAutoLogin( context: Context, autoLogin: Boolean ) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()
        }

        fun getAutoLogin( context: Context ) : Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false) // 두 번째 파라미터는 값이 없을 때 어떻게 할지 지정.
        }


        fun setToken(context: Context, token: String){
            // 1. 메모장 열기 prefName에 해당하는 SharedPre열기
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE) //이 파일 열어줘
            // 2. 토큰값을 TOKEN 항목에 저장
            pref.edit().putString(TOKEN, token).apply()
        }

        fun getToken(context: Context) : String{ // 스트링으로 반환
            // 1. 메모장 열기 prefName에 해당하는 SharedPre열기
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE) //이 파일 열어줘
            // 2. 저장된 토큰값을 리턴
            return pref.getString(TOKEN, "")!! // 어쨌든 얘는 널이 아니야.
        }


        fun setLoginEmail(context: Context, email: String){
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE) //이 파일 열어줘
            pref.edit().putString(LOGIN_EMAIL, email).apply()
        }

        fun getLoginEmail(context: Context) : String{ // 스트링으로 반환
            var pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE) //이 파일 열어줘
            return pref.getString(LOGIN_EMAIL, "")!! // 없으면 ""로 반환
        }

    }
}