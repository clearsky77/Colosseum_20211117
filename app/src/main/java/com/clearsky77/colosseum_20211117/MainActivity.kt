package com.clearsky77.colosseum_20211117

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.clearsky77.colosseum_20211117.databinding.ActivityMainBinding
import com.clearsky77.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
        binding.btnLogin.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

            // 로그인 요청. 이메일과 비번이 맞는가?
            ServerUtil.postRequestLogIn(inputEmail, inputPw, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
//                    로그인 API를 호출하고 돌아온 상황
//                    결과로 jsonObj 하나를 받아서 돌아온 상황

                    val code =  jsonObj.getInt("code")

//                    code : 200 -> 로그인 성공 토스트
//                    그 외 -> 로그인 실패 토스트
                    runOnUiThread {
                    // 백그라운드에서 UI를 쓰려고 하면 오류라 감지하고 앱이 중단된다. 그 해결방안으로 runOnUiThread를 사용한다.
                        if (code == 200) {
                            Toast.makeText(mContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(mContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            })
        }
    }

    override fun setValues() {
    }


}