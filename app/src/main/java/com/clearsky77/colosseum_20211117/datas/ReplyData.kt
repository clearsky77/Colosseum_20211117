package com.clearsky77.colosseum_20211117.datas

import org.json.JSONObject

class ReplyData{
    var id = 0;
    var content = ""
    var writer = UserData()
    var seletedSide = SideData()

    companion object{
        fun getReplyDataFromJson(jsonObj: JSONObject): ReplyData{
            val replyData = ReplyData()

            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")

            val userObj = jsonObj.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromJson(userObj)

            var sideObj = jsonObj.getJSONObject("selected_side")
            replyData.seletedSide = SideData.getSideDataFromJson(sideObj)

            return replyData
        }
    }
}