package com.clearsky77.colosseum_20211117.datas

import org.json.JSONObject
import java.io.Serializable

class ReplyData{
    var id = 0;
    var content = ""

    companion object{
        fun getReplyDataFromJson(jsonObj: JSONObject): ReplyData{
            val replyData = ReplyData()

            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")

            return replyData
        }
    }
}