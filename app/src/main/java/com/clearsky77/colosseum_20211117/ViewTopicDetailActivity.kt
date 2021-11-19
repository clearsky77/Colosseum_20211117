package com.clearsky77.colosseum_20211117

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.clearsky77.colosseum_20211117.databinding.ActivityViewTopicDetailBinding
import com.clearsky77.colosseum_20211117.datas.TopicData
import com.clearsky77.colosseum_20211117.utils.ServerUtil

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

    }

    override fun setValues() {
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        binding.txtTopicTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopic)

        getTopicDetailFromServer()
    }

//    토론 진영 목록 / 몇 표 획득
    fun getTopicDetailFromServer(){ // 서버에서 가져오는 메소드


    }
}