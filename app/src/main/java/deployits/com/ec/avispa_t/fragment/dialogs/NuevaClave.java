package deployits.com.ec.avispa_t.fragment.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanDispositivoPosicion;
import com.ec.deployits.cupones.bean.BeanRespuesta;

import java.util.Date;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

public class NuevaClave extends DialogFragment {

    private BeanMensajeSistema mensaje;
    private Conexion conexion;

    private BeanRespuesta res;

    private BeanCliente beanCliente;

    EditText txtNuevaContrasena, txtRepiteNuevaContrasena;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        conexion = new Conexion();
        mensaje = new BeanMensajeSistema(this.getActivity());
        beanCliente = new BeanCliente();


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.msnIngresoContrasenasNuevas);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View dialogView = inflater.inflate(R.layout.fragment_nueva_clave, null);

        txtNuevaContrasena = dialogView.findViewById(R.id.txtContrasena);
        //txtNuevaContrasena.setFilters(new InputFilter[] { new InputFilter.LengthFilter(3) });

        txtRepiteNuevaContrasena = dialogView.findViewById(R.id.txtRepetirContrasena);
        builder.setView(dialogView)
                // Add the buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    //boton Aceptar
                    public void onClick(DialogInterface dialog, int id) {
// va vacío para luego activar el boton en el onResume
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    //boton Cancelar
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    BeanDispositivoPosicion bDispositivoPosicion = new BeanDispositivoPosicion();

                    String MODEL = android.os.Build.MODEL; // El nombre visible para el usuario final
                    bDispositivoPosicion.setNombreDispositivo(MODEL);

                    String ANDROID = android.os.Build.VERSION.RELEASE; //version del sistema operativo
                    bDispositivoPosicion.setVersionSO(ANDROID);

                    bDispositivoPosicion.setEstado(true);
                    bDispositivoPosicion.setFechaRegistro(new Date());

                    String idDispositivo = Settings.Secure.getString(getContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID); //es el código del dispositivo

                    bDispositivoPosicion.setCodigoDispositivo(idDispositivo);

                    BeanCliente bClienteSerializado = (BeanCliente) getArguments().getSerializable("beanCliente");

                    String password = getArguments().getString("password");
                    String correo = bClienteSerializado.getCorreo();
                    bDispositivoPosicion.setCorreo(correo);

                    String nuevaContrasena = txtNuevaContrasena.getText().toString();
                    String repiteNuevaContrasena = txtRepiteNuevaContrasena.getText().toString();

                    if (nuevaContrasena.equals("") || repiteNuevaContrasena.equals("")) {
                        mensaje.mostrarMensajeToastActivity("Ingrese nueva contraseña");
                        // dismiss();   es para cerrar el DialogFragment
                    } else {
                        if (nuevaContrasena.equals(repiteNuevaContrasena)) {

                            String clave = password + (char) 10 + "" + nuevaContrasena;
                            bClienteSerializado.setCorreo(bDispositivoPosicion.getCorreo());
                            bClienteSerializado.setPassword(clave);
                            try {
                                System.out.println("bClienteSerializado " + bClienteSerializado);
                                System.out.println("bDispositivoPosicion " + bDispositivoPosicion);
                                res = conexion.enrolamientoCliente(bClienteSerializado, bDispositivoPosicion);

                                mensaje.mostrarMensajeToastActivity(res.getDescripcionError());

                                System.out.println("NuevaClave res.getDescripcionError() " + res.getDescripcionError());
                                if (res.getCodigoError() == 0) { //exito
                                    String primerNombre = res.getInformacionAdicionalStr();
                                    System.out.println("NuevaClaveDialogFragmentprimerNombre -> " + primerNombre);

                                    beanCliente.setCorreo(correo);

                                    Globales.beanCliente = conexion.obtenerDatosDeUsuario(beanCliente);

                                    d.dismiss();
                                    getActivity().finish();//finaliza el activity Registro

                                    mensaje.mostrarMensajeToastActivity("¡Usuario registrado exitosamente!");
                                } else if (res.getCodigoError() == 10) { // fracaso

                                } else if (res.getCodigoError() == 11) { // fracaso correo ya registrado anteriormente

                                    d.dismiss();
                                }
                            } catch (ConexionHostExcepcion conexionHostExcepcion) {
                                try {
                                    res = conexion.enrolamientoCliente(bClienteSerializado, bDispositivoPosicion);
                                    mensaje.mostrarMensajeToastActivity(res.getDescripcionError());

                                    if (res.getCodigoError() == 0) { //exito
                                        String primerNombre = res.getInformacionAdicionalStr();

                                        beanCliente.setCorreo(correo);

                                        Globales.beanCliente = conexion.obtenerDatosDeUsuario(beanCliente);

                                        d.dismiss();
                                        getActivity().finish();//finaliza el activity Registro

                                        mensaje.mostrarMensajeToastActivity("¡Usuario registrado exitosamente!");
                                    } else if (res.getCodigoError() == 10) { // fracaso

                                    } else if (res.getCodigoError() == 11) { // fracaso correo ya registrado anteriormente

                                        d.dismiss();

                                    }
                                } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                                    conexionHostExcepcion1.printStackTrace();
                                    mensaje.mostrarMensajeToastActivity(res.getDescripcionError());
                                }
                            }
                        } else {
                            mensaje.mostrarMensajeToastActivity("Las contraseñas no coinciden");
                        }
                    }
                }
            });
            Button negativeButton = (Button) d.getButton(Dialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mensaje.mostrarMensajeToastActivity("No ha registrado contraseña");
                    d.dismiss();
                }
            });
        }
    }
}

