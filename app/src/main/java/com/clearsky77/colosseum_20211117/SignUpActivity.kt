package com.clearsky77.colosseum_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.clearsky77.colosseum_20211117.databinding.ActivitySignUpBinding
import com.clearsky77.colosseum_20211117.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        이메일 중복인지 확인
        binding.btnEmailCheck.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            ServerUtil.getRequestDuplCheck("EMAIL",inputEmail,object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")

                    runOnUiThread {

                        if (code == 200) {
//                        사용해도 좋다
                            binding.txtEmailCheckResult.text = "사용해도 좋습니다."
                        }
                        else {
//                        사용하면 안됨
                            binding.txtEmailCheckResult.text = "중복된 이메일입니다. 다른 이메일로 검사해주세요."
                        }

                    }
                }

            })
        }





        binding.btnSignUp.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

// 회원가입 요청.
            ServerUtil.postRequestSignUp(
                inputEmail,
                inputPw,
                inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {
//                    로그인 API를 호출하고 돌아온 상황
//                    결과로 jsonObj 하나를 받아서 돌아온 상황

                        val code = jsonObj.getInt("code")
                        val message = jsonObj.getString("message")
//                    val nickname =  jsonObj.getJSONObject("data").getJSONObject("user").getString("nick_name")


//                    code : 200 -> 로그인 성공 토스트
//                    그 외 -> 로그인 실패 토스트
                        // 백그라운드에서 UI를 쓰려고 하면 오류라 감지하고 앱이 중단된다. 그 해결방안으로 runOnUiThread를 사용한다.
                        if (code == 200) {
                            val dataObj = jsonObj.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val nickname = userObj.getString("nick_name")
                            runOnUiThread {
                                Toast.makeText(
                                    mContext,
                                    "${nickname}님, 회원가입을 환영합니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }
//                            Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show()


                    }

                })

        }
    }

    override fun setValues() {
    }

}