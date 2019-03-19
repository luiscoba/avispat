package deployits.com.ec.avispa_t.hilos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ec.deployits.cupones.bean.BeanCategoria;
import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanSucursal;

import java.util.ArrayList;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

/*
* Los tres valores genericos indican <Valor1,Valor2,Valor3>
* Valor1 - Tipo de entrada del doInBackground (ejecutor en segundo plano)
* Valor2 - Tipo de entrada del onProgressUpdate (ejecutor del progreso)
* Valor3 - Tipo de salida del doInBackground y de entrada para el onPostExecute (ejecutor final )
*/
public class HiloCargarListasAlSistema extends AsyncTask <Void, Void, Boolean> {
    // Dialogo de mostrar progreso
    private ProgressDialog progreso;
    private StringBuilder errores;
    private Context context;
    private Conexion conexion;

    public HiloCargarListasAlSistema(Context context) {
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
                HiloCargarListasAlSistema.this.cancel(true);
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

        // se carga todas las listas en el sistema <<<<<<<<<---------------<<<<<<<<<<<<<<<<<<---------
        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> CARGA LISTAS AL SISTEMA ");
        System.out.println("______________________________________________________________________________________");

        Globales.lstCategorias = new ArrayList <>();
        // carga de la lista de categorias
        try {
            Globales.lstCategorias = conexion.obtenerTodasLasCategorias(Globales.beanCliente);
        } catch (ConexionHostExcepcion conexionHostExcepcion) {
            try {
                Globales.lstCategorias = conexion.obtenerTodasLasCategorias(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                conexionHostExcepcion1.printStackTrace();
            }
        }

        // carga de la lista de categoriasStr que sirve para cargar el Spinner de categorias
        for (BeanCategoria categoria : Globales.lstCategorias) {
            Globales.lstCategoriasStrSpinner.add(categoria.getNombre());
        }// agregamos en la posicion 0, la palabra "todas las categorias"
        Globales.lstCategoriasStrSpinner.add(0, Globales.item_Spinner_todas_las_categorias); //se inserta en la primera posicion

        // carga de la lista de cupones
        try {
            Globales.lstTodosLosCuponesSistema = conexion.obtenerTodasLasPromociones(Globales.beanCliente);
        } catch (ConexionHostExcepcion conexionHostExcepcion) {
            try {
                Globales.lstTodosLosCuponesSistema = conexion.obtenerTodasLasPromociones(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                conexionHostExcepcion1.printStackTrace();
            }
        }

        // carga de la lista de comercios
        try {
            Globales.lstTodosLosComerciosSistema = conexion.obtenerTodosLosComercios(Globales.beanCliente);
        } catch (ConexionHostExcepcion conexionHostExcepcion) {
            //conexionHostExcepcion.printStackTrace();
            try {
                Globales.lstTodosLosComerciosSistema = conexion.obtenerTodosLosComercios(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                conexionHostExcepcion1.printStackTrace();
            }
        }
        // carga las listas de cupones y comercios a sus respectivos adaptadores

        // se asigna lstTodosLosCupones a lstCuponesPorCategoriaAdaptador
        Globales.lstCuponesPorCategoriaAdaptador = new ArrayList <>();
        for (BeanPromocion promocion : Globales.lstTodosLosCuponesSistema) {
            Globales.lstCuponesPorCategoriaAdaptador.add(promocion);
        }
        // se asigna lstTodosLosComercios a lstComerciosPorCategoriaAdaptador
        Globales.lstComerciosPorCategoriaAdaptador = new ArrayList <>();
        for (BeanSucursal sucursal : Globales.lstTodosLosComerciosSistema) {
            Globales.lstComerciosPorCategoriaAdaptador.add(sucursal);
        }
        //<<<<<<<<<<<<<<---------------<<<<<<<<<<<<<<<<<<---------

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
