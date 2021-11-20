package com.clearsky77.colosseum_20211117

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.clearsky77.colosseum_20211117.adapters.ReplyAdapter
import com.clearsky77.colosseum_20211117.databinding.ActivityViewTopicDetailBinding
import com.clearsky77.colosseum_20211117.datas.ReplyData
import com.clearsky77.colosseum_20211117.datas.TopicData
import com.clearsky77.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewTopicDetailBinding
    lateinit var mTopicData: TopicData
    var mReplyList = ArrayList<ReplyData>()
    lateinit var mReplyAdapter: ReplyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnVote01.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopicData.sideList[0].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")
                    if(code==200){
                        // 득표 수를 서버에서 다시 받아오자(새로고침). 그래야 내가 투표한 것도 반영되서 보인다.
                        getTopicDetailFromServer()
                    }else{
                        val message = jsonObj.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        binding.btnVote02.setOnClickListener {
            ServerUtil.postRequestVote(mContext, mTopicData.sideList[1].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")
                    if(code==200){
                        getTopicDetailFromServer() // 투표 완료 -> 새로고침. 그래야 내가 투표한 것도 반영되서 보인다.
                    }else{
                        val message = jsonObj.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }


    override fun setValues() {
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        binding.txtTopicTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopic)

        getTopicDetailFromServer()

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        binding.replyListView.adapter = mReplyAdapter
    }

    //    토론 상세정보. 토론 상세정보 새로고침 때도 쓰인다!
    fun getTopicDetailFromServer() { // 서버에서 가져오는 메소드
        ServerUtil.getRequestTopicDetail( mContext, mTopicData.id, "NEW", object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

                mTopicData = TopicData.getTopicDataFromJson(topicObj)

                runOnUiThread {
                    refreshUI() // 위의 내용이 변경되었으니 리프레쉬해주세요.
                }

                val repliesArr = topicObj.getJSONArray("replies")
                for(i in 0 until repliesArr.length()){
                    val replyObj = repliesArr.getJSONObject(i)
                    val replyData = ReplyData.getReplyDataFromJson( replyObj )
                    mReplyList.add( replyData )
                }

                // 서버가 더 늦게 끝났다면? 리스트뷰 내용 변경됨.
                runOnUiThread{
                    mReplyAdapter.notifyDataSetChanged()
                }

            }
        })

    }

//    mTopicData가 변경되었으면 UI에 새로 반영해주시오.
    fun refreshUI(){
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopic)
        binding.txtTopicTitle.text = mTopicData.title
        binding.txtReplyCount.text = "댓글: ${mTopicData.replyCount}개"
        binding.txtSideTitle01.text = mTopicData.sideList[0].title
        binding.txtSideTitle02.text = mTopicData.sideList[1].title

        binding.txtSideVoteCount01.text = "${mTopicData.sideList[0].voteCount}표"
        binding.txtSideVoteCount02.text = "${mTopicData.sideList[1].voteCount}표"
    }
}