package com.clearsky77.colosseum_20211117

import android.content.Intent
import android.os.Bundle
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.clearsky77.colosseum_20211117.adapters.TopicAdapter
import com.clearsky77.colosseum_20211117.databinding.ActivityLoginBinding
import com.clearsky77.colosseum_20211117.databinding.ActivityMainBinding
import com.clearsky77.colosseum_20211117.datas.TopicData
import com.clearsky77.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject
import java.math.MathContext

class MainActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding

    val mTopicList = ArrayList<TopicData>()

    lateinit var mTopAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
        setCustomActionBar()
    }

    override fun setupEvents() {
        binding.topicListView.setOnItemClickListener{ adapterView, view, position, l ->

            val clickedTopicData = mTopicList[position]
            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic", clickedTopicData)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
//        연습 - 내 정보 API 호출
//        getMyInfoFormServer()
        getTopicListFromServer() // 서버에서 받아서
        mTopAdapter = TopicAdapter(mContext, R.layout.topic_list_item, mTopicList)
        binding.topicListView.adapter = mTopAdapter
//
    }

    fun getTopicListFromServer() {
        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                var topicsArr = dataObj.getJSONArray("topics")

                for (i in 0 until topicsArr.length()) {
                    // 하나의 토론 주제를 표현하는 { } 추출
                    val topicObj = topicsArr.getJSONObject(i)
                    val topicData = TopicData.getTopicDataFromJson(topicObj)

                    // mTopicList에 추가
                    mTopicList.add(topicData)

                    // 서버가 더 늦게 끝났다면, 리스트뷰 내용 변경됨
                    runOnUiThread{
                        mTopAdapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }


//    private fun getMyInfoFormServer() {
//        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler{
//            override fun onResponse(jsonObj: JSONObject) {
//                var dataObj = jsonObj.getJSONObject("data")
//                var userObj = dataObj.getJSONObject("user")
//                var nickname = userObj.getString("nick_name")
//
//                runOnUiThread {
//                    binding.txtUserNickname.text = nickname
//                }
//            }
//
//        })
//    }


}