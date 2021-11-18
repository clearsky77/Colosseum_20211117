package com.clearsky77.colosseum_20211117.utils

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler {
        fun onResponse(jsonObj: JSONObject)  //응답이 돌아오면 어떻게 할 거냐. 강의 - 17일 16:50
    }

    companion object {

//        여기에 적는 변수 / 함수는 => JAVA의 static에 대응됨.
//        클래스이름.기능() 로 활용 가능.

        // 모든 함수 (기능) 가 공유할 서버 컴퓨터 주소.
        val HOST_URL = "http://54.180.52.26"

        //      로그인 함수 - POST
        fun postRequestLogIn(
            email: String,
            pw: String,
            handler: JsonResponseHandler?
        ) { // Json 응답 받아서 어떻게 처리할 건지 같이

            //  1. 어디로 갈래? URL
            val urlString = "${HOST_URL}/user"

            // 2. 어떤 데이터를 들고가? (파라미터)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

            // 3. 어떤 메쏘드 + 1/2 데이터 결합 => 어떤 요청인지 완성
            val request = Request.Builder()
                .url(urlString)
                .post(formData) // post로 갈거야.
                .build()

            // 4. 완성된 Request를 실제로 호출 => 클라이언트 역할
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                // enqueue 응답이 돌아왔을 때 뭐할지 등록
                // Callback 답이 돌아왔을 때

                // 실패했을 때
                override fun onFailure(call: Call, e: IOException) {
//                    실패 : 물리적 접속 실패.
//                    보통 토스트 띄우는 것으로 대체함.
                }

                // 응답이 돌아 왔을 때
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string() // 본문만 String으로 변환.
                    // bodyString은 JSON 양식으로 가공됨 . => 한글도 임시 변환된 상태 (encoding) 깨짐.
                    // 강사 서버에서는 body가 null인 경우가 없어서 !!한다.

                    // 일반 String -> JSONObject로 변환  (한글도 원상복구)
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    // 나를 호출한 화면에게 JsonObj 처리를 위임하자.
                    // 아래 코드는 if(handler!=null){ handler.onResponse(jsonObj) } 이 코드와 같아
                    handler?.onResponse(jsonObj) // ?가 붙으면 없으면 실행하지 않는다.
                }

            })

        }

        //      회원가입 함수 - PUT
        fun putRequestSignUp(
            email: String,
            pw: String,
            nickname: String,
            handler: JsonResponseHandler?
        ) { // Json 응답 받아서 어떻게 처리할 건지 같이

            // 1. 어디로 갈래? URL
            val urlString = "${HOST_URL}/user"

            // 2. 어떤 데이터를 들고가? (파라미터)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()

            // 3. 어떤 메쏘드 + 1/2 데이터 결합 => 어떤 요청인지 완성
            val request = Request.Builder()
                .url(urlString)
                .put(formData) // post로 갈거야.
                .build()

            // 4. 완성된 Request를 실제로 호출 => 클라이언트 역할
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                // enqueue 응답이 돌아왔을 때 뭐할지 등록
                // Callback 답이 돌아왔을 때

                // 실패했을 때
                override fun onFailure(call: Call, e: IOException) {
//                    실패 : 물리적 접속 실패.
//                    보통 토스트 띄우는 것으로 대체함.
                }

                // 응답이 돌아 왔을 때
                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string() // 본문만 String으로 변환.
                    // bodyString은 JSON 양식으로 가공됨 . => 한글도 임시 변환된 상태 (encoding) 깨짐.
                    // 강사 서버에서는 body가 null인 경우가 없어서 !!한다.

                    // 일반 String -> JSONObject로 변환  (한글도 원상복구)
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

                    // 나를 호출한 화면에게 JsonObj 처리를 위임하자.
                    // 아래 코드는 if(handler!=null){ handler.onResponse(jsonObj) } 이 코드와 같아
                    handler?.onResponse(jsonObj) // ?가 붙으면 없으면 실행하지 않는다.
                }

            })


        }
    }
}