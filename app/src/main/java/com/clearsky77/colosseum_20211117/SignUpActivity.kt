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
        binding.btnSignUp.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()
            val inputNickName = binding.edtNickName.text.toString()

// 회원가입 요청.
            ServerUtil.postRequestSignUp(inputEmail, inputPw, inputNickName, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
//                    로그인 API를 호출하고 돌아온 상황
//                    결과로 jsonObj 하나를 받아서 돌아온 상황

                    val code =  jsonObj.getInt("code")
                    val message =  jsonObj.getString("message")
//                    val code =  jsonObj.getJSONObject("data").getJSONObject("user").getString("nick_name")

//                    code : 200 -> 로그인 성공 토스트
//                    그 외 -> 로그인 실패 토스트
                    runOnUiThread {
                        // 백그라운드에서 UI를 쓰려고 하면 오류라 감지하고 앱이 중단된다. 그 해결방안으로 runOnUiThread를 사용한다.
                        if (code == 200) {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
//                            Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show()

                    }

                }

            })

        }
    }

    override fun setValues() {
    }

}