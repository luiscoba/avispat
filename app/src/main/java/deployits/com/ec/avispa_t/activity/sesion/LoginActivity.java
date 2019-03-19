package deployits.com.ec.avispa_t.activity.sesion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanDispositivoPosicion;
import com.ec.deployits.cupones.bean.BeanRespuesta;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.hilos.HiloCargarListasDeUsuarioAlSistema;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.database.InfoUsuario;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.fragment.dialogs.NuevaClave;
import deployits.com.ec.avispa_t.global.Globales;

public class LoginActivity extends AppCompatActivity {

    private BeanMensajeSistema mensaje;
    private Conexion conexion;
    private InfoUsuario db;

    // UI references.
    private AutoCompleteTextView actxvwCorreo;
    private EditText txbxPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new InfoUsuario(this);
        conexion = new Conexion();
        mensaje = new BeanMensajeSistema(this);

        actxvwCorreo = findViewById(R.id.correo);
        txbxPass = findViewById(R.id.password);

        MetodoPara.permisoDeEjecucion();

    }

    private boolean validaInformacion() {
        boolean validaInformacion = true;
        // Reset errors.
        actxvwCorreo.setError(null);
        txbxPass.setError(null);

        View focusView = null;

        // Comprueba si hay una contraseña válida, si el usuario ingresó una.
        if (txbxPass.getText().toString().trim().isEmpty()) {
            txbxPass.setError(getString(R.string.errorPasswordInvalido));
            focusView = txbxPass;
            //          mensaje.mostrarMensajeToast("Ingrese password");
            validaInformacion = false;
        }
        // Revisa si es valido el correo.
        if (actxvwCorreo.getText().toString().trim().isEmpty()) {
            actxvwCorreo.setError(getString(R.string.errorCampoRequerido));
            focusView = actxvwCorreo;
            validaInformacion = false;
        }
        return validaInformacion;
    }

    public void onClickInicioSesion(View view) {

        if (validaInformacion()) {

            String correo = actxvwCorreo.getText().toString();
            String password = txbxPass.getText().toString();

            BeanCliente beanCliente = new BeanCliente();
            beanCliente.setCorreo(correo);
            beanCliente.setPassword(password);
            BeanRespuesta res = null;
            try {
                res = conexion.inicioSesionCliente(beanCliente, new BeanDispositivoPosicion());

                if (res.getCodigoError() == 0) {//inicia correctamente

                    try {
                        Globales.beanCliente = conexion.obtenerDatosDeUsuario(beanCliente);
                    } catch (ConexionHostExcepcion conexionHostExcepcion) {
                        try {
                            Globales.beanCliente = conexion.obtenerDatosDeUsuario(beanCliente);
                        } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                            conexionHostExcepcion1.printStackTrace();
                        }
                    }

                    new HiloCargarListasDeUsuarioAlSistema(LoginActivity.this, LoginActivity.this).execute();

                    MetodoPara.guardarPreferenciaBeanCliente(this);
//                finish(); ya se finaliza en el hilo
                } else if (res.getCodigoError() == 10) {// UsuarioBloqueado | UsuarioNoExiste | ContraseniaNoRegistrada
//                mensaje.mostrarMensajeToast(res.getDescripcionError());
                } else if (res.getCodigoError() == 11) {// ContraseniaExpirada
//                mensaje.mostrarMensajeToast(res.getDescripcionError());

                    //mostramos el dialogFragment
                    DialogFragment mostrarDialogFragment = new NuevaClave();
                    Bundle bundle = new Bundle();
                    bundle.putString("correo", correo);
                    bundle.putString("password", password);
                    mostrarDialogFragment.setArguments(bundle);
                    mostrarDialogFragment.setCancelable(false);
                    mostrarDialogFragment.show(getSupportFragmentManager(), "dialogoParaNuevaClave");
                }
                mensaje.mostrarMensajeToastActivity(res.getDescripcionError());
            } catch (ConexionHostExcepcion conexionHostExcepcion) {
                mensaje.mostrarMensajeToastActivity("Error al conectarse al servidor");
                System.out.println("Error al conectarse al servidor");
            }
        }
    }

    public void onClickCrearCuenta(View view) {//partimos de este activity, indicamos al activity al q nos vamos
        Intent crearCuenta = new Intent(this, RegistroActivity.class);
        startActivity(crearCuenta);//ahora se dispara el método
        finish();
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }

    public void onClickRecuperaCuenta(View view) {//partimos de este activity,vamos a RecuperaCuentaActivity
        Intent recuperaCuenta = new Intent(this, RecuperaCuentaActivity.class);
        startActivity(recuperaCuenta);//ahora se dispara el método
        finish();
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}