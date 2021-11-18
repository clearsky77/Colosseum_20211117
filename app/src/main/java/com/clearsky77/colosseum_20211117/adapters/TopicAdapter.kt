package com.clearsky77.colosseum_20211117.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        return row
    }

}