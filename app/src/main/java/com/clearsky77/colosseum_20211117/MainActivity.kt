package com.clearsky77.colosseum_20211117

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.clearsky77.colosseum_20211117.databinding.ActivityMainBinding
import com.clearsky77.colosseum_20211117.utils.ServerUtil

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
            ServerUtil.postRequestLogIn(inputEmail, inputPw)
        }
    }

    override fun setValues() {
    }


}