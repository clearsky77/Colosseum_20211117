package com.clearsky77.colosseum_20211117

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.clearsky77.colosseum_20211117.databinding.ActivityLoginBinding
import com.clearsky77.colosseum_20211117.utils.ContextUtil
import com.clearsky77.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

//        로그인 버튼 클릭
        binding.btnLogin.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

            // 로그인 요청. 이메일과 비번이 맞는가?
            ServerUtil.postRequestLogIn(
                inputEmail,
                inputPw,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {
//                    로그인 API를 호출하고 돌아온 상황
//                    결과로 jsonObj 하나를 받아서 돌아온 상황

                        val code = jsonObj.getInt("code")
                        val message = jsonObj.getString("message")
//                    val code =  jsonObj.getJSONObject("data").getJSONObject("user").getString("nick_name")

//                    code : 200 -> 로그인 성공 토스트
//                    그 외 -> 로그인 실패 토스트
                        // 백그라운드에서 UI를 쓰려고 하면 오류라 감지하고 앱이 중단된다. 그 해결방안으로 runOnUiThread를 사용한다.
                        if (code == 200) {
                            val dataObj = jsonObj.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val nickname = userObj.getString("nick_name")
                            runOnUiThread {
                                Toast.makeText(mContext, "${nickname}님, 환영합니다.", Toast.LENGTH_SHORT).show()

                                // 서버에게서 받은 토큰 추출
                                val token = dataObj.getString("token")
                                // 공용 저장소에 저장
                                ContextUtil.setToken(mContext, token)
                                // 공용 저장소에 이메일 저장
                                ContextUtil.setLoginEmail(mContext, inputEmail)

                                val myIntent = Intent(mContext, MainActivity::class.java)
                                startActivity(myIntent)

                                finish() // 로그인 화면은 종료시켜줘야한다.
                            }

                        } else {
                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                })
        }

//        회원가입 버튼 클릭
        binding.btnSignUpLink.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
    }


}