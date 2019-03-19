package deployits.com.ec.avispa_t.activity.notificaciones;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.activity.MainActivity;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.global.Globales;

public class SinConexionActivity extends AppCompatActivity {

    private Timer timer;
    private TimerTask timerTask;
    private BeanMensajeSistema mensajeSistema;
    private boolean hayConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_conexion);

        Button btnIrAWiFi = findViewById(R.id.btnWiFi);

        Globales.regresoDesdeSinConexionActivity = true;

        btnIrAWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                //finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        hayConexion = MetodoPara.isConnected(this);

        if (hayConexion) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            System.out.println("-------------------------------------------------");
            System.out.println("-----  onBackPressed me quedo en SinConexionActivity  ------");
            System.out.println("-------------------------------------------------");
        }
        finish();
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }

    @Override
    public void onRestart() {
        super.onRestart();

        mensajeSistema = new BeanMensajeSistema(this);
        mensajeSistema.mostrarMensajeToastActivity("entra en restart sin conexión");

        System.out.println("entra en restart sin conexión SinConexionActivity");

        seVerificaConexion();

    }

    @Override
    public void onStop() {
        super.onStop();

        seVerificaConexion();

    }

    @Override
    protected void onResume() {
        super.onResume();

        startTimer();

        seVerificaConexion();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 0, 20000); //
    }

    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {


                seVerificaConexion();

/*              hayConexion = MetodoPara.isConnected(SinConexionActivity.this);
                if (hayConexion) {
                    finish();

                    System.out.println("Bienvenido");
                } else {

                    System.out.println("Sin conexion****");
                }
*/
            }
        };
    }

    public void seVerificaConexion() {

        hayConexion = MetodoPara.isConnected(this);

        if (hayConexion) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            System.out.println("-------------------------------------------------");
            System.out.println("-----  onBackPressed me quedo en SinConexionActivity  ------");
            System.out.println("-------------------------------------------------");

            finish();
            overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
        }
    }
}
