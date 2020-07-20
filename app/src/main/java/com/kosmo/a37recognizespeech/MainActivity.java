package com.kosmo.a37recognizespeech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KOSMO61";

    TextView textView1;
    SpeechRecognizer mRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);

        //권한체크여부 확인(오디오 권한)
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)){
            }
            else {
                //음성인식의 결과를 보여줌
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }

        //음성인식 객체 생성 및 리스너 부착
       mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(recognitionListener);
    }//// onCreate End

    public void onBtn1Clicked(View v) {
        try {
            //음성인식의 실행 (1)
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
            //검색결과를 보여주는 갯수
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "음성 검색");

            mRecognizer.startListening(intent);
        }
        catch (ActivityNotFoundException e) {
            Log.d(TAG, "ActivityNotFoundException");
        }
    }//// onBtn1Clicked End

    public void onBtn2Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);
    }//// onBtn2Clicked End

    //음성인식 리스너 정의
    private RecognitionListener recognitionListener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        //음성인식 오류 시
        @Override
        public void onError(int i) {
            textView1.setText("너무 늦게 말하면 오류가 발생합니다.");
        }

        //음성인식 성공했을 때의 콜백메소드
        @Override
        public void onResults(Bundle bundle) {

            //Bundle 객체를 통해 음성인식에 대한 결과를 전달받음
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = bundle.getStringArrayList(key);

            Log.i(TAG, String.valueOf(mResult.size()));
            for(String s : mResult) {
                Log.i(TAG, s);
            }

            String[] rs = new String[mResult.size()];
            //List컬렉션을 배열로 변환하는 함수
            mResult.toArray(rs);
            //결과를 TextView 에 표시함
            textView1.setText(rs[0]);

            ////////////////////////////////////////////////////////
            //인식한 문장내에 '축구장' or '풋살장' 이라는 단어가 있을 경우 토스트로 알림처리
            String[] searchTxtArr = rs[0].split(" ");
            for(String s : searchTxtArr) {
                if(s.contains("축구장")) {
                    Toast.makeText(getApplicationContext(),
                            "축구장이 인식되었습니다.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(s.contains("풋살장")) {
                    Toast.makeText(getApplicationContext(),
                            "풋살장이 인식되었습니다.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle b) {

        }
    }; //// RecognitionListener End

}

























