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

    //    내 핸드폰의 시간을 고려해서, 시차를 보정해서 / 가공해서 작성일시를 알려주는 함수.

    fun getFormattedCreateAt() : String {

//        서버 주는 시간 : GMT +0
//        내 폰의 시간 : 설정된 GMT +?  시차가 얼마나 나는가?
//        ex. 서울 -> +9  => 게시글 작성시간도 9시간 더해서 보여주자.

//        내 핸드폰에 설정된 시간대가 어느 시간대? => ex. 서울
        val timeZone = Calendar.getInstance().timeZone

//        몇시간 차이? rawOffset 이라는 것이 시차값을 반환해준다 (ms 단위) => 시간단위로 변경
        val timeOffset =  timeZone.rawOffset / 1000 / 60 / 60 // 1000밀리초/60초/60분. => 시간이 나온다

//        이 글의 작성시간을 시차만큼 더해주자.  => 서버 표준시간에 시차를 더해서 내 핸드폰 (로컬시간) 보정
        val tempCreatedAt = Calendar.getInstance() // 불러올 때마다 원본(this)에 더하니 문제가 있어서 임시 변수 작성
        tempCreatedAt.time = this.createdAt.time
        tempCreatedAt.add( Calendar.HOUR, timeOffset ) // 시간 자리에 앞에서 구한 시차만큼 더해준다.

//        결과 String으로 가공.
        val sdf = SimpleDateFormat("yyyy/MM/dd a h시 m분")

        return sdf.format( tempCreatedAt.time )
    }

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