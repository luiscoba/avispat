package deployits.com.ec.avispa_t.activity.sesion;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanDispositivoPosicion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.fragment.dialogs.NuevaClave;
import deployits.com.ec.avispa_t.global.Globales;

public class RegistroActivity extends AppCompatActivity {

    private BeanMensajeSistema mensaje;
    private Conexion conexion;

    private DatePicker datePicker;
    private EditText txtNombre, txtApellido, txtEmail;
    private RadioButton radHombre, radMujer;
    private RadioGroup radioGroup;
    private Button btnRegistrase;
    private ProgressDialog progressDialog;
    //private InfoUsuario db;
    String correoStr;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = findViewById(R.id.nombreReg);
        txtApellido = findViewById(R.id.apellidoReg);
        txtEmail = findViewById(R.id.emailReg);

        datePicker = (DatePicker) findViewById(R.id.datePickerFechaNac);//colocamos la fecha inicial del datePicker
        datePicker.init(Globales.año_inicial_datePicker, Globales.mes_inicial_datePicker, Globales.dia_inicial_datePicker, null);

        mensaje = new BeanMensajeSistema(this);
        //permisos para la ejecucion de hilos
        MetodoPara.permisoDeEjecucion();

        this.progressDialog();
        conexion = new Conexion();
    }

    public Date obtenerFecha() {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = "";
        Date fecha = null;

        datePicker = findViewById(R.id.datePickerFechaNac);
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String monthStr = "", dayStr = "";

        if (month >= 0 && month < 9)
            monthStr = "0" + month;
        else
            monthStr = String.valueOf(month);

        if (day > 0 && day < 10)
            dayStr = "0" + day;
        else
            dayStr = "0" + String.valueOf(day);

        strFecha = year + "-" + monthStr + "-" + dayStr;
        System.out.println("Fecha ingresada : " + dayStr + "/" + monthStr + "/" + year + "ó" + strFecha);

        try {
            fecha = formatoDelTexto.parse(strFecha);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        //     System.out.println("fecha.toString() " + fecha.toString());
        return fecha;
    }

    public boolean verificaFechaCambiada(DatePicker datePicker) {
        boolean fechaCambiada = false;
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        if (day == Globales.dia_inicial_datePicker && month == Globales.mes_inicial_datePicker && year == Globales.año_inicial_datePicker)
            fechaCambiada = false;
        else
            fechaCambiada = true;

        return fechaCambiada;
    }

    public void onClickRegistrarNuevoUsuario(View view) {
        //   mensaje.iniciarProgress("Procesando...", "Su petición se está procesando");
        if (validaInformacion()) {

            BeanCliente bCliente = new BeanCliente();
            BeanDispositivoPosicion bDispositivoPosicion = new BeanDispositivoPosicion();

            System.out.println("genero seleccionado : ");
            radHombre = findViewById(R.id.radioHombre);
            radMujer = findViewById(R.id.radioMujer);

            String nombreStr = txtNombre.getText().toString();
            String apellidoStr = txtApellido.getText().toString();
            String emailStr = txtEmail.getText().toString();

            bCliente.setTelefono("mPhoneNumber");  // celularStr este campo luego se obtendrá el numero de telefono

            correoStr = emailStr;

            if (radHombre.isChecked() == true)
                bCliente.setIdGenero(Globales.genero_hombre);
            else
                bCliente.setIdGenero(Globales.genero_mujer);

            bCliente.setCorreo(correoStr);
            bCliente.setNombre(nombreStr);
            bCliente.setApellido(apellidoStr);
            bCliente.setFechaNacimiento(obtenerFecha());//se obtiene del DatePicker
            System.out.println("fecha seleccionado : " + bCliente.getFechaNacimiento());
            bCliente.setDomicilio("");
            bCliente.setPassword("");
            bCliente.setCorreo(emailStr);

            String MODEL = android.os.Build.MODEL; // El nombre visible para el usuario final
            bDispositivoPosicion.setNombreDispositivo(MODEL);

            String ANDROID = android.os.Build.VERSION.RELEASE; //version del sistema operativo
            bDispositivoPosicion.setVersionSO(ANDROID);

            bDispositivoPosicion.setEstado(true);
            bDispositivoPosicion.setFechaRegistro(new Date());

            String idDispositivo = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID); //es el código del dispositivo

            bDispositivoPosicion.setCodigoDispositivo(idDispositivo);
            bDispositivoPosicion.setCorreo(correoStr);

            DialogFragment mostrarDialogFragment = new NuevaClave();
            Bundle bundle = new Bundle();
            bundle.putString("correo", correoStr);
            bundle.putSerializable("beanCliente", bCliente);

            mostrarDialogFragment.setArguments(bundle);
            mostrarDialogFragment.setCancelable(false);
            mostrarDialogFragment.show(getSupportFragmentManager(), "dialogoParaNuevaClave");

        } else {
            mensaje.mostrarMensajeToastActivity("complete los campos requeridos");
            //mensaje.pararProgreso();
        }
    }

    private boolean validaInformacion() {
        View focusView = null;
        radioGroup = findViewById(R.id.radioGenero);
        radHombre = findViewById(R.id.radioHombre);
        radMujer = findViewById(R.id.radioMujer);
        boolean activarButton = true;

        if (txtNombre.getText().toString().trim().isEmpty()) {
            txtNombre.setError(getString(R.string.errorCampoRequerido));
            focusView = txtNombre;
            mensaje.mostrarMensajeToastActivity("Ingrese su nombre");
            activarButton = false;
        } else if (txtApellido.getText().toString().trim().isEmpty()) {
            txtApellido.setError(getString(R.string.errorCampoRequerido));
            focusView = txtApellido;
            mensaje.mostrarMensajeToastActivity("Ingrese su apellido");
            activarButton = false;
        } else if (txtEmail.getText().toString().trim().isEmpty()) {
            txtEmail.setError(getString(R.string.errorCampoRequerido));
            focusView = txtEmail;
            mensaje.mostrarMensajeToastActivity("Ingrese su Email");
            activarButton = false;
        } else if (!validarEmail(txtEmail.getText().toString())) {//revisa si tiene formato de correo
            focusView = txtEmail;
            txtEmail.setError("Email no válido");
            activarButton = false;
        } else if (radHombre.isChecked() == false && radMujer.isChecked() == false) {
            focusView = radioGroup;
            radHombre.setError(getString(R.string.errorGeneroInvalido));
            mensaje.mostrarMensajeToastActivity("Seleccione su género");
            activarButton = false;
        } else if (verificaFechaCambiada(datePicker) == false) {//verificamos si cambio la fecha
            focusView = datePicker;
            mensaje.mostrarMensajeToastActivity("Seleccione fecha de nacimiento");
            activarButton = false;
        }

        return activarButton;
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void progressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Procesando....");
        progressDialog.setTitle("Conexión");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

