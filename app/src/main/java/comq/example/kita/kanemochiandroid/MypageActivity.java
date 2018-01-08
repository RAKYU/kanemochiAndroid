package comq.example.kita.kanemochiandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MypageActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
    }

    public void write(View view){
        Intent intent = new Intent(MypageActivity.this, WriteActivity.class);
        startActivityForResult(intent,100);
    }

    public void chart(View view){
        Intent intent = new Intent(MypageActivity.this, ChartActivity.class);
        startActivityForResult(intent,200);
    }

}
