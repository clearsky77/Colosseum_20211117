package com.clearsky77.colosseum_20211117.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData : Serializable{
    var id = 0 // Int 자리 표시하기 위해 0을 넣는다
    var title = "" // String 자리를 표시하기 위해
    var imageURL = ""

    var replyCount = 0

    var sideList = ArrayList<SideData>()

    companion object{
        fun getTopicDataFromJson(jsonObj:JSONObject) : TopicData{
            val resultTopicData = TopicData()

            resultTopicData.id = jsonObj.getInt("id")
            resultTopicData.title = jsonObj.getString("title")
            resultTopicData.imageURL = jsonObj.getString("img_url")

            resultTopicData.replyCount = jsonObj.getInt("reply_count")

            val sidesArr = jsonObj.getJSONArray("sides")
            for(i in 0 until sidesArr.length()) {
                val sideObj = sidesArr.getJSONObject(i)
                val sideData = SideData.getSideDataFromJson(sideObj)
                resultTopicData.sideList.add(sideData)
            }
            return resultTopicData
        }
    }

}