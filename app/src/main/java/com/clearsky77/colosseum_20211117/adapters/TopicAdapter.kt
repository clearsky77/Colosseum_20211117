package com.clearsky77.colosseum_20211117.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.clearsky77.colosseum_20211117.R
import com.clearsky77.colosseum_20211117.datas.TopicData

class TopicAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<TopicData>
        ):ArrayAdapter<TopicData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if(tempRow==null){
            tempRow = mInflater.inflate(R.layout.topic_list_item, null)
        }

        var row = tempRow!!

        val data = mList[position]

        val imgTopic = row.findViewById<ImageView>(R.id.imgTopic)
        val txtTopicTitle = row.findViewById<TextView>(R.id.txtTopicTitle)
        val txtReplyCount = row.findViewById<TextView>(R.id.txtReplyCount)

        txtTopicTitle.text = data.title
        txtReplyCount.text = "현재 댓글: ${data.replyCount}개"
        Glide.with(mContext).load(data.imageURL).into(imgTopic)

        Log.d("TopicAdapter에 이미지 주소",data.imageURL)

        return row
    }

}