package deployits.com.ec.avispa_t.activity.notificaciones;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import deployits.com.ec.avispa_t.R;

public class IniciadoSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciado_sesion);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
