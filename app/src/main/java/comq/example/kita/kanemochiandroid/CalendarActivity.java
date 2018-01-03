package comq.example.kita.kanemochiandroid;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class CalendarActivity extends AppCompatActivity{
    private MaterialCalendarView calendar;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private int beforeDay;
    private ServerThread serverThread;
    private int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        calendar = (MaterialCalendarView) findViewById(R.id.calendarView);
        scrollView = (ScrollView)findViewById(R.id.data_scroll_view);
        linearLayout = (LinearLayout)findViewById(R.id.scroll_in_layout);

        calendar.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                serverThread = new ServerThread("http://10.10.17.67:8888/test/jsontest");//임시임.
                serverThread.start();
            }
        });

    }
    private class ServerThread extends Thread{
        String addr;

        public ServerThread(String addr){
            this.addr = addr;
        }

        @Override
        public void run() {
            super.run();

            String result = test2(addr); // 서버에 메세지 보내고 받음.
            Message message = handler.obtainMessage();
            message.obj = result;
            handler.sendMessage(message);
        }

        // 네트워크 로직 작성
        // 서버에 요청 보냄. (서버 주소, 프로젝트 명)
        // 메소드 따로 작성
        private String test2(String addr){
            StringBuilder sb = new StringBuilder();

            try {
                URL url = new URL(addr);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                if(httpURLConnection != null){
                    // 서버에 보낼 준비 작업
                    // 서버에 요청 보냈지만 응답 없을 때, 몇초까지 기다릴 것인가.
                    httpURLConnection.setConnectTimeout(10000);
                    httpURLConnection.setUseCaches(false); // 캐시 사용 여부
                    httpURLConnection.setRequestMethod("POST"); // get 또는 post 방식

                    //추가
                    httpURLConnection.setRequestProperty("Content-Type", "text/plain;charset=UTF-8");


                    // 서버에 메세지 보내기
                    OutputStream outputStream = httpURLConnection.getOutputStream(); // 서버에 보낼 문자열
                    outputStream.write("안드로이드 메세지".getBytes("utf-8")); // 보내는 방식이 조금 특이
                    outputStream.close(); // 자원 봔환; 메모리 반환

                    // 서버에 응답했을 때 로직
                    if(httpURLConnection.getResponseCode() == httpURLConnection.HTTP_OK){
                        InputStreamReader is = new InputStreamReader(httpURLConnection.getInputStream());
                        // Reader 가 붙으면 문자열로 인풋, 아웃풋 스트림으로 주고받음..

                        int ch;
                        while((ch = is.read()) != -1){
                            sb.append((char)ch); // 한 글자씩 붙여줌
                        }
                        is.close(); // 작업 끝나면 close
                        // jsonText = sb.toString(); // streamBuilder 를 문자열로 받아서 잠시 jsonText 에 담아 둠
                    }
                    httpURLConnection.disconnect(); // 더이상 필요하지 않으므로 통신 끊어줌.
                }

            } catch (Exception e) {
                Toast.makeText(CalendarActivity.this, "스레드 에러", Toast.LENGTH_SHORT).show();
                //e.printStackTrace();
            }
            return sb.toString();
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String)msg.obj;
            LinearLayout dataLinearView = (LinearLayout) View.inflate(CalendarActivity.this,R.layout.activity_dayview,null);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scroll_in_layout);
            TextView moneyView = (TextView)dataLinearView.findViewById(R.id.textMoney);
            TextView categoryView = (TextView)dataLinearView.findViewById(R.id.textCategory);
            TextView tagView = (TextView)dataLinearView.findViewById(R.id.textTag);


//            linearLayout.removeAllViews();
            if(result == null) return;
            JSONArray jsonArray = null;
            JSONObject jsonObject = null;

            try {
                jsonArray = new JSONArray(result);
                if(i==3) i=0;
                jsonObject = jsonArray.getJSONObject(i);
                categoryView.setText(jsonObject.getString("num"));
                tagView.setText(jsonObject.getString("name"));
                moneyView.setText("$4달라");
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (linearLayout.getChildCount() % 7){
                case 0:
                    dataLinearView.setBackgroundColor(Color.rgb(240,128,128));
                    break;
                case 1:
                    dataLinearView.setBackgroundColor(Color.rgb(255,165,0));
                    break;
                case 2:
                    dataLinearView.setBackgroundColor(Color.rgb(240,230,140));
                    break;
                case 3:
                    dataLinearView.setBackgroundColor(Color.rgb(50,205,50));
                    break;
                case 4:
                    dataLinearView.setBackgroundColor(Color.rgb(0,191,255));
                    break;
                case 5:
                    dataLinearView.setBackgroundColor(Color.rgb(0,0,128));
                    break;
                case 6:
                    dataLinearView.setBackgroundColor(Color.rgb(128,0,128));
                    break;
            }
            linearLayout.addView(dataLinearView);
        }
    };

}
