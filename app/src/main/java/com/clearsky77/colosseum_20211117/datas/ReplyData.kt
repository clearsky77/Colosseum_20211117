package com.clearsky77.colosseum_20211117.datas

import org.json.JSONObject

class ReplyData{
    var id = 0;
    var content = ""
    var writer = UserData()

    companion object{
        fun getReplyDataFromJson(jsonObj: JSONObject): ReplyData{
            val replyData = ReplyData()

            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")

            val userObj = jsonObj.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromJson(userObj)

            return replyData
        }
    }
}