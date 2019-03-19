package deployits.com.ec.avispa_t.activity.formulario;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanRespuesta;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

public class Formulario extends AppCompatActivity {

    private Conexion conexion;
    private BeanPromocion beanPromocion;

    private BeanRespuesta res;
    // Recuperamos los atributos del layout que vamos a controlar
    private EditText nombre, apellido, cedula, correo, domicilio, txtParticipantes;
    private Spinner formaDePago;
    private Integer formaDePagoSel;
    private TextView txtCantidad;
    private Button Guardar, Cancelar;
    private Integer intCantidad;
    private String strParticipantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Toolbar toolbar = findViewById(R.id.toolbarManual);
        setSupportActionBar(toolbar);
        //       getSupportActionBar().setIcon(R.drawable.usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        conexion = new Conexion();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {     // verificar si se usa idPromocionIntent
            Integer idPromocionIntent = getIntent().getExtras().getInt("idPromocionIntent");
            beanPromocion = obtenerPromocionPorIdPromocion(idPromocionIntent);
            //        posicion = getIntent().getExtras().getInt("posicion");
        }
        /*
          Vinculamos los atributos del layout
         */
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        cedula = findViewById(R.id.cedula);
         /*
          Spinner con su adaptador y evento
         */
        formaDePago = findViewById(R.id.spnFormaDePago);
        ArrayAdapter <CharSequence> formaDePagoAdapter = ArrayAdapter.createFromResource(this,
                R.array.forma_de_pago, android.R.layout.simple_spinner_dropdown_item);
        formaDePago.setAdapter(formaDePagoAdapter);
        formaDePago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

                formaDePagoSel = formaDePago.getSelectedItemPosition() + 1; //parent.getAdapter().getItem(position).toString;
                System.out.println("formaDePagoSel " + formaDePagoSel);
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        correo = findViewById(R.id.correo);
        domicilio = findViewById(R.id.domicilio);
        txtParticipantes = findViewById(R.id.participantes);

        txtCantidad = findViewById(R.id.cantidadParticipantes);
        intCantidad = beanPromocion.getCantidad();
        txtCantidad.setText(String.valueOf(intCantidad));

        boolean estadoDelCupon = extras.getBoolean("estadoDelCupon");
        if (estadoDelCupon) {

            desahabilitarControles();
            //  MetodoPara.ocultarTeclado(this, nombre.getRootView());
        }
        /*
          Llenado del formulario
         */
        if (Globales.beanCliente.getCorreo() != null) {
            nombre.setText(Globales.beanCliente.getNombre());
            apellido.setText(Globales.beanCliente.getApellido());
            cedula.setText(Globales.beanCliente.getDocumento());
            System.out.println("beanPromocion.getIdFormaDePago() " + beanPromocion.getIdFormaDePago());
            formaDePago.setSelection(beanPromocion.getIdFormaDePago() - 1);
            correo.setText(Globales.beanCliente.getCorreo());
            domicilio.setText(Globales.beanCliente.getDomicilio());
            strParticipantes = beanPromocion.getParticipantes();
            txtParticipantes.setText(strParticipantes);
        }
    }

    public void onClickGuardar(View view) {

        if (validar() && validaIngresoParticipantes(intCantidad)) {
            Globales.beanCliente.setNombre(nombre.getText().toString());
            Globales.beanCliente.setApellido(apellido.getText().toString());
            Globales.beanCliente.setDocumento(cedula.getText().toString());
            Globales.beanCliente.setDomicilio(domicilio.getText().toString());

            beanPromocion.setParticipantes(txtParticipantes.getText().toString());
            beanPromocion.setIdFormaDePago(formaDePagoSel);

            generacionDeCupon();
            desahabilitarControles();

            MetodoPara.guardarPreferenciaBeanCliente(this);
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Complete el numero de participantes")
                    .setMessage("Colocar " + intCantidad + " nombres separados por coma (,)")
                    .setPositiveButton(" Aceptar ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create()
                    .show();
        }
    }

    public void desahabilitarControles() {
        nombre.setEnabled(false);
        apellido.setEnabled(false);
        cedula.setEnabled(false);
        formaDePago.setEnabled(false);
        correo.setEnabled(false);
        domicilio.setEnabled(false);
        txtParticipantes.setEnabled(false);
        txtCantidad.setEnabled(false);
        Guardar = findViewById(R.id.guardar);
        Guardar.setEnabled(false);
        Cancelar = findViewById(R.id.cancelar);
        Cancelar.setText("Atrás");
    }

    public void onClickCancelar(View view) {
        finish();
    }

    public void generacionDeCupon() {
        //aqui ya coloca en la lista de cupones generados es aqui, es increible
        beanPromocion.setIdEstadoNotificacion(Globales.estado_de_notificacion_generado);

        try {
            res = conexion.generaCuponDescuento(Globales.beanCliente, beanPromocion);
        } catch (ConexionHostExcepcion conexionHostExcepcion) {
            try {
                res = conexion.generaCuponDescuento(Globales.beanCliente, beanPromocion);
            } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                beanPromocion.setIdEstadoNotificacion(Globales.estado_de_notificacion_cancelado);
                conexionHostExcepcion1.printStackTrace();
            }
        }
        Toast.makeText(getApplicationContext(), res.getDescripcionError(), Toast.LENGTH_LONG).show();
    }

    private Boolean validar() {
        boolean resultado = true;
        String temp;

        temp = nombre.getText().toString();
        if (temp.isEmpty()) {
            MetodoPara.alerta(this, "Aviso", getString(R.string.error_nombre));
            nombre.requestFocus();
            return false;
        }

        temp = apellido.getText().toString();
        if (temp.isEmpty()) {
            MetodoPara.alerta(this, "Aviso", getString(R.string.error_apellido));
            apellido.requestFocus();
            return false;
        }

        temp = cedula.getText().toString();
        if (temp.isEmpty()) {
            MetodoPara.alerta(this, "Aviso", getString(R.string.cedula_blanco));
            cedula.requestFocus();
            return false;
        }

        temp = cedula.getText().toString();
        // Validacion usando expresion regular
        if (!Pattern.compile("^[0-9]{0,10}$").matcher(temp).matches()) {
            MetodoPara.alerta(this, "Aviso", getString(R.string.cedula_incorrecta));
            cedula.requestFocus();
            return false;
        }

        temp = domicilio.getText().toString();
        if (temp.isEmpty()) {
            MetodoPara.alerta(this, "Aviso", getString(R.string.error_domicilio));
            domicilio.requestFocus();
            return false;
        }

        temp = txtParticipantes.getText().toString();
        if (temp.isEmpty()) {
            MetodoPara.alerta(this, "Aviso", getString(R.string.error_participantes));
            txtParticipantes.requestFocus();
            return false;
        }

//        int cantidad = validaIngresoParticipantes(temp, beanPromocion.getCantidad()).size();
//        if (cantidad)

        return resultado;
    }

    public boolean validaIngresoParticipantes(Integer cantidad) {

        boolean valido = false;

        strParticipantes = txtParticipantes.getText().toString();

        List <String> parts = Arrays.asList(strParticipantes.split(","));

        System.out.println("validaIngresoParticipantes ->");

        System.out.println("parts.size() " + parts.size());
        System.out.println("cantidad " + cantidad);
        /*int count = 0;
        while (parts.size() > cantidad) {
            System.out.println("Splt: " + parts.get(count));
            System.out.println("count " + count);
            count++;
        }*/
        if (cantidad == parts.size()) {
            valido = true;
        }
        return valido;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Accion de retroceso con el boton Home
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//aca debes colocar el metodo que deseas que retorne
        return true;
    }

    public BeanPromocion obtenerPromocionPorIdPromocion(Integer idPromocion) {

        BeanPromocion beanPromocion = new BeanPromocion();
        for (BeanPromocion promocion : Globales.lstTodosLosCuponesSistema) {
            if (promocion.getIdPromocion() == idPromocion) {
                beanPromocion = promocion;
                break;
            }
        }
        return beanPromocion;
    }

}
