package madroid.bisioota;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {

                finish();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                //SharedPreferences prefs = getSharedPreferences(sd.Login_flag, MODE_PRIVATE);
               // Integer islogin = prefs.getInt("login_status", 0);
//                if (islogin == 0) {
//                    Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(LauncherActivity.this, ScanActivity.class);
//                    startActivity(intent);
//                }

            }
        }, 2000);
    }
}
