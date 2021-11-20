package com.clearsky77.colosseum_20211117

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.clearsky77.colosseum_20211117.databinding.ActivityViewTopicDetailBinding
import com.clearsky77.colosseum_20211117.datas.TopicData
import com.clearsky77.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewTopicDetailBinding
    lateinit var mTopicData: TopicData


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

                }

            })
        }
    }

    override fun setValues() {
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        binding.txtTopicTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopic)

        getTopicDetailFromServer()
    }

    //    토론 진영 목록 / 몇 표 획득
    fun getTopicDetailFromServer() { // 서버에서 가져오는 메소드
        ServerUtil.getRequestTopicDetail( mContext, mTopicData.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

                mTopicData = TopicData.getTopicDataFromJson(topicObj)

                runOnUiThread {
                    refreshUI() // 위의 내용이 변경되었으니 리프레쉬해주세요.
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