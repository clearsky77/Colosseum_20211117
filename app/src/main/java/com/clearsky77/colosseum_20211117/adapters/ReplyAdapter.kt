package com.clearsky77.colosseum_20211117.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.clearsky77.colosseum_20211117.R
import com.clearsky77.colosseum_20211117.ViewTopicDetailActivity
import com.clearsky77.colosseum_20211117.datas.ReplyData
import com.clearsky77.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

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
        val txtCreatedAt = row.findViewById<TextView>(R.id.txtCreatedAt)
        val txtReplyCount = row.findViewById<TextView>(R.id.txtReplyCount)
        val txtLikeCount = row.findViewById<TextView>(R.id.txtLikeCount)
        val txtDislikeCount = row.findViewById<TextView>(R.id.txtDislikeCount)


        txtWriterNickname.text = data.writer.nickname
        txtSelectedSide.text = "(${data.seletedSide.title})"
        txtReplyContext.text = data.content
//        val sdf = SimpleDateFormat("yyyy/MM/dd a h시 m분") // 재가공 도움을 주는 // 이제 필요없다.
        txtCreatedAt.text = data.getFormattedCreateAt()

        txtReplyCount.text = "답글: ${data.replyCount.toString()}개"
        txtLikeCount.text = "좋아요: ${data.likeCount.toString()}개"
        txtDislikeCount.text = "싫어요: ${data.dislikeCount.toString()}개"

        // 각 줄의 좋아요 개수에 이벤트 처리.
        // '좋아요' 처리
        txtLikeCount.setOnClickListener {
            // 이 댓글에 좋아요를 남겼다고 -> 서버 API 호출.
            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id, true, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    // 토론 상세 현황 화면의 기능 활용.
//                     => 토론 주제 상세 다시 가져오기. (댓글도 가져오게 됨)
                    (mContext as ViewTopicDetailActivity).getTopicDetailFromServer()
                }
            })
        }

        // '싫어요' 처리
        txtDislikeCount.setOnClickListener {
            // 이 댓글에 싫어요를 남겼다고 -> 서버 API 호출.
            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, false, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                }

            })
        }

        return row
    }

}