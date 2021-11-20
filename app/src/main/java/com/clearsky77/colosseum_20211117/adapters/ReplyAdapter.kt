package com.clearsky77.colosseum_20211117.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.clearsky77.colosseum_20211117.R
import com.clearsky77.colosseum_20211117.datas.ReplyData

class ReplyAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<ReplyData>
        ):ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if(tempRow==null){
            tempRow = mInflater.inflate(R.layout.reply_list_item, null)
        }

        var row = tempRow!!

        val data = mList[position]

        val txtWriterNickname = row.findViewById<TextView>(R.id.txtWriterNickname)
        val txtSelectedSide = row.findViewById<TextView>(R.id.txtSelectedSide)
        val txtReplyContext = row.findViewById<TextView>(R.id.txtReplyContent)

        txtWriterNickname.text = data.writer.nickname
        txtSelectedSide.text = "(${data.seletedSide.title})"
        txtReplyContext.text = data.content

        return row
    }

}