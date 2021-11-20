package com.clearsky77.colosseum_20211117.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReplyData{
    var id = 0;
    var content = ""
    var writer = UserData()
    var seletedSide = SideData()

    // 댓글 작성 일자 표시
    var createdAt = Calendar.getInstance() // 기본 Calendar 대입 -> 현재 일시가 저장됨.

    companion object{
        fun getReplyDataFromJson(jsonObj: JSONObject): ReplyData{
            val replyData = ReplyData()

            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")

            val userObj = jsonObj.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromJson(userObj)

            var sideObj = jsonObj.getJSONObject("selected_side")
            replyData.seletedSide = SideData.getSideDataFromJson(sideObj)

            // 작성 일시
            // 작성일시 파싱 -> String으로 서버가 내려줌. (String으로 임시 추출)
            val createdAtStr = jsonObj.getString("created_at")

            // 임시 추출 (String -> Calendar) 형태로 변환 -> 댓글데이터의 작성일시로 반영.
            // 서버가 준 String을 분석하기 위한 SimpleDateFormat 만들기
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            // parse로 Date형태의 일시 생성 -> Calendar변수.time 에 대입.
            replyData.createdAt.time = sdf.parse(createdAtStr)

            return replyData
        }
    }
}