package deployits.com.ec.avispa_t.bean;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class BeanMensajeSistema {

    private Activity activity;
    private Context context;

    public BeanMensajeSistema(Activity activity) {

        this.activity = activity;
    }

    public BeanMensajeSistema(Context context) {

        this.context = context;
    }

    public void mostrarMensajeToastActivity(String mensaje) {
        Toast.makeText(this.activity, mensaje, Toast.LENGTH_LONG).show();
    }

    public void mostrarMensajeToastContext(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }

    public void notificacionPromociones(String titulo, String contenido) {

    }
}
