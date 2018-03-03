package erickhdez.com.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnPlayClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }
}
