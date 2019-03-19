package deployits.com.ec.avispa_t.hilos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.conexion.Conexion;

public class HiloOrdenarCategorias extends AsyncTask<Void, Void, Boolean> {
    // Dialogo de mostrar progreso
    private ProgressDialog progreso;
    private StringBuilder errores;
    private Context context;
    private Conexion conexion;

    public HiloOrdenarCategorias(Context context) {
        this.context = context;
    }

    /**
     * Se ejecuta antes de iniciar el proceso en segundo plano
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        conexion = new Conexion();
        errores = new StringBuilder();
        progreso = new ProgressDialog(context);
        progreso.setCancelable(true);
        progreso.setMessage("Cargando...");
        /*
        Listener que permite capturar si el dialogo se cancela para entonces
        cancelar el proceso
         */
        progreso.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                HiloOrdenarCategorias.this.cancel(true);
            }
        });
        progreso.show();
    }

    /**
     * Trabajo en segundo plano
     *
     * @param voids es el tipo definido en el primer generico
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        /*
          Metodo que se usa para ir publicando el progreso del trabajo
          que se haya programado.
         */
        publishProgress(/* tipos de valores que se pasan */);

        /**
         Metodo de chequeo si se ha cancelado la operacion
         */
        isCancelled();

        MetodoPara.ordenarCuponesComerciosPorCategorias();

        return true;
    }

    /**
     * Metodo que se dispara cuando se ejecuta el publishProgress();
     *
     * @param values Valores definidos en el segundo generico de la clase
     */
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
            /*
            Si en el proceso se quiere actualizar algun componente UI y este diera algun error
            entonces debe usarse otro hilo mas interno para realizar la operacion, el mas recomendado es:

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });*/
    }

    /**
     * Metodo que se ejecuta en la terminacion del proceso en segundo plano
     *
     * @param aBoolean Valor definido en el tercer generico
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (!aBoolean) {
            MetodoPara.alerta(context, "ERROR", errores.toString());
        } else {
            /*
            Log.i("XML al fragmento", xml);
            Fragment fragmento = MostrarObjetos.newInstance(xml, tipo);
            showFragment(fragmento);
            */
        }
        progreso.dismiss();
    }

    /**
     * Metodo que se ejecuta si se ha cancelado el proceso.
     * NOTA: No es posible cancelar las operaciones de conexion en curso, para eso estan los
     * timeout que se les programan.
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        progreso.dismiss();
        Toast.makeText(context, R.string.operacion_cancelada, Toast.LENGTH_SHORT).show();
    }
}
