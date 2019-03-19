package deployits.com.ec.avispa_t.activity.sesion;

import android.app.*;
import android.content.*;
import android.os.*;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.activity.MainActivity;
import deployits.com.ec.avispa_t.activity.menu.Categorias;
import deployits.com.ec.avispa_t.global.Globales;

public class SplashActivity extends Activity {

    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(1000);
                    }
                } catch (InterruptedException ex) {
                }

                if (Globales.seActivoLogin == true) {

                } else {
                    Intent intent = new Intent(SplashActivity.this, Categorias.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
                }
                finish();
            }
        };

        mSplashThread.start();


    }
}