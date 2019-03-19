package deployits.com.ec.avispa_t.activity.sesion;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanRespuesta;

import java.util.regex.Pattern;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

public class RecuperaCuentaActivity extends AppCompatActivity {

    private BeanMensajeSistema mensaje;
    private Conexion conexion;

    private EditText txtCorreo;
    private String correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_cuenta);

        txtCorreo = findViewById(R.id.correo);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mensaje = new BeanMensajeSistema(this);
        conexion = new Conexion();
    }

    public void onClickRecuperaCuenta(View view) {

        BeanCliente beanCliente = new BeanCliente();

        View focusView = null;

        correo = txtCorreo.getText().toString();
        // Comprueba si hay una contraseña válida, si el usuario ingresó una.
        if (correo.isEmpty()) {
            txtCorreo.setError(getString(R.string.errorCampoRequerido));
            focusView = txtCorreo;
            mensaje.mostrarMensajeToastActivity("Ingrese correo electrónico");
        } else {// Revisa si es valido el correo.
            if (!validarEmail(correo)) {//revisa si tiene formato de correo
                txtCorreo.setError("Email no válido");
                focusView = txtCorreo;
            } else {
                beanCliente.setCorreo(correo);
 //               beanCliente.setDocumento(correo);
                try {
                    BeanRespuesta res = conexion.recuperarCuenta(beanCliente);
                    mensaje.mostrarMensajeToastActivity(res.getDescripcionError());

                    Intent irAloginActivity = new Intent(this, LoginActivity.class);
                    startActivity(irAloginActivity);//ahora se dispara el método
                    Globales.beanCliente = conexion.obtenerDatosDeUsuario(beanCliente);
                    finish();
                    overridePendingTransition(R.transition.fade_in, R.transition.fade_out);

                } catch (ConexionHostExcepcion conexionHostExcepcion) {
                    conexionHostExcepcion.printStackTrace();
                }
            }
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
