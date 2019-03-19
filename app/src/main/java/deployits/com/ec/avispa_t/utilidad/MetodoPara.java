package deployits.com.ec.avispa_t.utilidad;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ec.deployits.cupones.bean.BeanCategoria;
import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanDispositivoPosicion;
import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanSucursal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

import static android.content.Context.MODE_PRIVATE;


public class MetodoPara {/* - - Deployits Julio 2018 - - */

    static Conexion conexion = new Conexion();

    /* MetodoPara que concede acceso a las politicas */
    public static void permisoDeEjecucion() {
        //permisos para la ejecucion de hilos
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public static void ocultarTeclado(Activity activity, View view) {

        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /* MetodoParas para guardar y leer valores que son Preferencias, para que no se logue nuevamente */
    private static String PREFS_KEY = "mispreferencias";

    public static void guardarValor(Context context, String keyPref, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(keyPref, valor);
        editor.commit();
    }

    public static String leerValor(Context context, String keyPref) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return preferences.getString(keyPref, "");
    }

    public static void guardarValorBool(Context context, String keyPref, boolean valor) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor;
        editor = prefs.edit();
        editor.putBoolean(keyPref, valor);
        editor.commit();
    }

    @NonNull
    public static boolean leerValorBool(Context context, String keyPref) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(keyPref, false);
        //       Boolean yourLocked = prefs.getBoolean("locked", false);
    }

    public static BeanCliente traerPreferenciaBeanCliente(Context context) {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> TRAER PREFERENCIAS BEANCLIENTE ");
        System.out.println("______________________________________________________________________________________");

        BeanCliente beanCliente = new BeanCliente();
        //si trae el idUsuario significa que se el usuario se logueo alguna vez
        String idUsuarioStr = leerValor(context, "idUsuario");

        if (idUsuarioStr == "")

            return new BeanCliente();//el usuario no se a logueado por eso se retorna null en beanCliente

        else {
            Integer idUsuario = Integer.parseInt(idUsuarioStr);
            String documento = leerValor(context, "documento");
            String nombre = leerValor(context, "nombre");
            String apellido = leerValor(context, "apellido");
            String correo = leerValor(context, "correo");
            String telefono = leerValor(context, "telefono");
            String password = leerValor(context, "password");
            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String fechaNacimientoStr = leerValor(context, "fechaNacimiento");
            Date fechaNacimiento = null;
            try {
                fechaNacimiento = sdf.parse(fechaNacimientoStr);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }*/
            String idGeneroStr = leerValor(context, "idGenero");
            Integer idGenero = Integer.parseInt(idGeneroStr);
            String domicilio = leerValor(context, "domicilio");

            beanCliente.setIdUsuario(idUsuario);
            beanCliente.setDocumento(documento);
            beanCliente.setNombre(nombre);
            beanCliente.setApellido(apellido);
            beanCliente.setCorreo(correo);
            beanCliente.setTelefono(telefono);
            beanCliente.setPassword(password);
//           beanCliente.setFechaNacimiento(fechaNacimiento);
            beanCliente.setIdGenero(idGenero);
            beanCliente.setDomicilio(domicilio);

            return beanCliente;
        }
    }

    public static void guardarPreferenciaBeanCliente(Context context) {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> GUARDAR PREFERENCIAS BEANCLIENTE ");
        System.out.println("______________________________________________________________________________________");

        guardarValor(context, "idUsuario", String.valueOf(Globales.beanCliente.getIdUsuario()));
        System.out.println("Globales.beanCliente.getIdUsuario() " + Globales.beanCliente.getIdUsuario());
        guardarValor(context, "documento", Globales.beanCliente.getDocumento());
        System.out.println("Globales.beanCliente.getDocumento() " + Globales.beanCliente.getDocumento());
        guardarValor(context, "nombre", Globales.beanCliente.getNombre());
        System.out.println("Globales.beanCliente.getNombre() " + Globales.beanCliente.getNombre());
        guardarValor(context, "apellido", Globales.beanCliente.getApellido());
        System.out.println("Globales.beanCliente.getApellido() " + Globales.beanCliente.getApellido());
        guardarValor(context, "correo", Globales.beanCliente.getCorreo());
        System.out.println("Globales.beanCliente.getCorreo() " + Globales.beanCliente.getCorreo());
        guardarValor(context, "telefono", Globales.beanCliente.getTelefono());
        System.out.println("Globales.beanCliente.getTelefono() " + Globales.beanCliente.getTelefono());
        guardarValor(context, "password", Globales.beanCliente.getPassword());
        System.out.println("Globales.beanCliente.getPassword() " + Globales.beanCliente.getPassword());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Globales.beanCliente.getFechaNacimiento() " + Globales.beanCliente.getFechaNacimiento());
//        String fechaNacimientoStr = sdf.format(Globales.beanCliente.getFechaNacimiento());
//        guardarValor(context, "fechaNacimiento", fechaNacimientoStr);
//        System.out.println("Globales.beanCliente.getFechaNacimiento() " + Globales.beanCliente.getFechaNacimiento());
        guardarValor(context, "idGenero", String.valueOf(Globales.beanCliente.getIdGenero()));
        System.out.println("Globales.beanCliente.getIdGenero() " + Globales.beanCliente.getIdGenero());
        guardarValor(context, "domicilio", Globales.beanCliente.getDomicilio());
        System.out.println("Globales.beanCliente.getDomicilio() " + Globales.beanCliente.getDomicilio());
    }
    // ----------->  -----------> -----------> -----------> ----------->

    /**
     * Dialogo basico de alerta que solo tiene un boton y que se le puede pasar el titulo y el contenido
     *
     * @param context Contexto en el que se va a mostrar
     * @param titulo  Titulo del dialogo
     * @param cadena  Contenido del dialogo
     */
    public static void alerta(Context context, String titulo, String cadena) {
        new AlertDialog.Builder(context)
                .setMessage(cadena)
                .setCancelable(true).setTitle(titulo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    /*-->-->-->-->-->-->-->-->-->-->-->- CARGA DE LISTAS DEL SISTEMA ---<--<--<--<--<--<--<--<--<--<*/

    /*  se carga:
            - la lista de categorias
            - lista de categoriasStr para el Spinner
            - lista de comercios
            - lista de cupones
            - listas de adaptadores
    */
    public static void cargarListasAlSistema(Context context) {

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
    }

    public static void cargarListaComercios() {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> CARGA LISTA DE COMERCIOS ");
        System.out.println("______________________________________________________________________________________");

        Globales.lstComerciosPorCategoriaAdaptador = new ArrayList <>();

        try {
            Globales.lstComerciosPorCategoriaAdaptador = conexion.obtenerTodosLosComercios(Globales.beanCliente);
        } catch (ConexionHostExcepcion conexionHostExcepcion) {
            try {
                Globales.lstComerciosPorCategoriaAdaptador = conexion.obtenerTodosLosComercios(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                conexionHostExcepcion1.printStackTrace();
            }
        }
        // se asigna lstTodosLosComercios a lstComerciosPorCategoriaAdaptador
        Globales.lstTodosLosComerciosSistema = new ArrayList <>();
        for (BeanSucursal sucursal : Globales.lstComerciosPorCategoriaAdaptador) {
            Globales.lstTodosLosComerciosSistema.add(sucursal);
        }

    }

    public static void cargarListaCupones() {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> CARGA LISTA DE CUPONES ");
        System.out.println("______________________________________________________________________________________");

        Globales.lstCuponesPorCategoriaAdaptador = new ArrayList <>();
        // carga de la lista de cupones
        try {
            Globales.lstCuponesPorCategoriaAdaptador = conexion.obtenerTodasLasPromociones(Globales.beanCliente);
        } catch (ConexionHostExcepcion conexionHostExcepcion) {
            try {
                Globales.lstCuponesPorCategoriaAdaptador = conexion.obtenerTodasLasPromociones(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                conexionHostExcepcion1.printStackTrace();
            }
        }
        Globales.lstTodosLosCuponesSistema = new ArrayList <>();
        // se asigna lstTodosLosCupones a lstCuponesPorCategoriaAdaptador
        Globales.lstTodosLosCuponesSistema = new ArrayList <>();
        for (BeanPromocion promocion : Globales.lstCuponesPorCategoriaAdaptador) {
            Globales.lstTodosLosCuponesSistema.add(promocion);
        }
        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> FIN CARGA LISTA DE CUPONES ");
        System.out.println("______________________________________________________________________________________");

    }

    public static void cargarListaCategorias() {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> CARGA LISTA DE CATEGORIAS ");
        System.out.println("______________________________________________________________________________________");

        Conexion conexion = new Conexion();
        Globales.lstCategorias = new ArrayList <>();
        try {
            Globales.lstCategorias = conexion.obtenerTodasLasCategorias(Globales.beanCliente);
        } catch (ConexionHostExcepcion conexionHostExcepcion) {
            try {
                Globales.lstCategorias = conexion.obtenerTodasLasCategorias(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion1) {
                conexionHostExcepcion1.printStackTrace();
            }
        }

        for (BeanCategoria categoria : Globales.lstCategorias) {
            System.out.println("categoria.getNombre() " + categoria.getNombre() + " categoria.getIdCategoria() " + categoria.getIdCategoria() + " categoria.getNombre() " + categoria.getFavorito());
        }

        Globales.lstCategoriasStrSpinner = new ArrayList <>();
        // carga de la lista de categoriasStr que sirve para cargar el Spinner de categorias
        for (BeanCategoria categoria : Globales.lstCategorias) {
            Globales.lstCategoriasStrSpinner.add(categoria.getNombre());
        }// agregamos en la posicion 0, la palabra "todas las categorias"

        Globales.lstCategoriasStrSpinner.add(0, Globales.item_Spinner_todas_las_categorias); //se inserta en la primera posicion
        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> FIN CARGA LISTA DE CATEGORIAS ");
        System.out.println("______________________________________________________________________________________");

    }

    public static void ordenarCuponesComerciosPorCategorias() {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> ORDENA LISTA DE CATEGORIAS ");
        System.out.println("______________________________________________________________________________________");

        System.out.println("ANTES");
        for (BeanSucursal sucursal : Globales.lstComerciosPorCategoriaAdaptador) {
            System.out.println("idSucursal " + sucursal.getIdSucursal() + " favorita " +sucursal.getCategoriaFavorita());
        }

        //se realiza la ORDENACION de lstComerciosPorCategoriaAdaptador
        Comparator <BeanSucursal> c2 = new Comparator <BeanSucursal>() {
            public int compare(BeanSucursal p1, BeanSucursal p2) {//se multiplica por -1 para orden inverso
                return (-1) * p1.getCategoriaFavorita().compareTo(p2.getCategoriaFavorita());
            }
        };
        Collections.sort(Globales.lstComerciosPorCategoriaAdaptador, c2);

        System.out.println("DESPUES");
        for (BeanSucursal sucursal : Globales.lstComerciosPorCategoriaAdaptador) {
            System.out.println("idSucursal " + sucursal.getIdSucursal() + " favorita " +sucursal.getCategoriaFavorita());
        }


        //ordenamos las Promociones
        Comparator <BeanPromocion> c1 = new Comparator <BeanPromocion>() {
            public int compare(BeanPromocion p1, BeanPromocion p2) {
                return (-1) * p1.getCategoriaFavorita().compareTo(p2.getCategoriaFavorita());
            }
        };
        Collections.sort(Globales.lstCuponesPorCategoriaAdaptador, c1);
    }

    public static void cargarValoresDelSistema() {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> CARGA COMERCIOS Y CUPONES AL SISTEMA ");
        System.out.println("______________________________________________________________________________________");

        for (int i = 0; i < Globales.lstTodosLosCuponesSistema.size(); i++) {
            System.out.println("Globales.lstTodosLosCuponesSistema " + Globales.lstTodosLosCuponesSistema);
            Globales.lstTodosLosCuponesSistema.get(i).setIdEstadoNotificacion(Globales.estado_de_notificacion_cancelado);
            Globales.lstTodosLosCuponesSistema.get(i).setCodigoGenerado("");
            Globales.lstTodosLosCuponesSistema.get(i).setParticipantes("");
            Globales.lstTodosLosCuponesSistema.get(i).setCategoriaFavorita(false);
//            Globales.lstTodosLosCuponesSistema.get(i).setIdCategoria(Globales.lstIdsCuponesUsuario.get(i).getIdCategoria());
        }

        for (int i = 0; i < Globales.lstTodosLosComerciosSistema.size(); i++) {
            Globales.lstTodosLosComerciosSistema.get(i).setComercioFavorito(false);
            Globales.lstTodosLosComerciosSistema.get(i).setCategoriaFavorita(false);
//            Globales.lstTodosLosComerciosSistema.get(i).setIdCategoria(Globales.lstIdsComerciosUsuario.get(i).getIdCategoria());
        }

        cargarLstAdaptadores();

        for (int i = 0; i < Globales.lstCategorias.size(); i++) {
            Globales.lstCategorias.get(i).setFavorito(false);
        }
    }

    public static void cargarLstAdaptadores() {

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
    }


    public static void ordenarLstCuponesComerciosPorUsuario() {

        System.out.println("______________________________________________________________________________________");
        System.out.println("-> ");
        System.out.println("-> ORDENAR LISTA DE CUPONES Y COMERCIOS DE USUARIO ");
        System.out.println("______________________________________________________________________________________");

        //este comparator es muy útil
        Comparator <BeanPromocion> c1 = new Comparator <BeanPromocion>() {
            public int compare(BeanPromocion p1, BeanPromocion p2) {
                return p1.getIdPromocion().compareTo(p2.getIdPromocion());
            }
        };
        //primero se ordena la lista
        Collections.sort(Globales.lstIdsCuponesUsuario, c1);
        Collections.sort(Globales.lstTodosLosCuponesSistema, c1);

        //este comparator es muy útil
        Comparator <BeanSucursal> c2 = new Comparator <BeanSucursal>() {
            public int compare(BeanSucursal p1, BeanSucursal p2) {
                return p1.getIdSucursal().compareTo(p2.getIdSucursal());
            }
        };
        Collections.sort(Globales.lstIdsComerciosUsuario, c2);
        Collections.sort(Globales.lstTodosLosComerciosSistema, c2);
    }

    public static void ponerBotonFlotante(View view, final FragmentActivity activity) {

        Globales.regresoMainActivityBtnFlotante = true;

        Globales.fab = view.findViewById(R.id.fab);
        Globales.fab.setVisibility(View.VISIBLE);

        System.out.println("public static void ponerBotonFlotante");
        for (BeanPromocion cupon : Globales.lstCuponesPorCategoriaAdaptador) {
            System.out.println("empresa " + cupon.getNombreEmpresa() + "  idPromocion " + cupon.getIdPromocion());
        }

        Globales.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Inicio de la app", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //metodo para vaciar los fragments, es decir eliminar los fragments
                FragmentManager fm = activity.getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }

                Globales.tabLayout.setVisibility(View.VISIBLE);
                Globales.spinner.setVisibility(View.VISIBLE);
            }
        });
    }

    /*
        public static void cargarValoresDeUsuario() {

            System.out.println("______________________________________________________________________________________");
            System.out.println("-> ");
            System.out.println("-> CARGAR VALORES DE USUARIO ");
            System.out.println("______________________________________________________________________________________");

            Conexion conexion = new Conexion();

            Globales.lstIdsCuponesUsuario = new ArrayList <>();
            Globales.lstIdsComerciosUsuario = new ArrayList <>();

            try {
                Globales.lstIdsCuponesUsuario = conexion.obtenerTodosLosIdsDeLasPromocionesGeneradas(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion) {
                conexionHostExcepcion.printStackTrace();
            }
            //este comparator es muy útil
            Comparator <BeanPromocion> c1 = new Comparator <BeanPromocion>() {
                public int compare(BeanPromocion p1, BeanPromocion p2) {
                    return p1.getIdPromocion().compareTo(p2.getIdPromocion());
                }
            };
            //primero se ordena la lista
            Collections.sort(Globales.lstIdsCuponesUsuario, c1);
            Collections.sort(Globales.lstTodosLosCuponesSistema, c1);

            for (int i = 0; i < Globales.lstIdsCuponesUsuario.size(); i++) {
                System.out.println("Globales.lstTodosLosCuponesSistema " + Globales.lstTodosLosCuponesSistema);
                System.out.println("Globales.lstIdsCuponesUsuario " + Globales.lstIdsCuponesUsuario);
                Globales.lstTodosLosCuponesSistema.get(i).setIdEstadoNotificacion(Globales.lstIdsCuponesUsuario.get(i).getIdEstadoNotificacion());
                Globales.lstTodosLosCuponesSistema.get(i).setCodigoGenerado(Globales.lstIdsCuponesUsuario.get(i).getCodigoGenerado());
                Globales.lstTodosLosCuponesSistema.get(i).setParticipantes(Globales.lstIdsCuponesUsuario.get(i).getParticipantes());
                Globales.lstTodosLosCuponesSistema.get(i).setCategoriaFavorita(Globales.lstIdsCuponesUsuario.get(i).getCategoriaFavorita());
                Globales.lstTodosLosCuponesSistema.get(i).setIdCategoria(Globales.lstIdsCuponesUsuario.get(i).getIdCategoria());
            }

            try {
                Globales.lstIdsComerciosUsuario = conexion.obtenerTodosLosIdsDeLosComerciosFavoritos(Globales.beanCliente);
            } catch (ConexionHostExcepcion conexionHostExcepcion) {
                conexionHostExcepcion.printStackTrace();
            }
            //este comparator es muy útil
            Comparator <BeanSucursal> c2 = new Comparator <BeanSucursal>() {
                public int compare(BeanSucursal p1, BeanSucursal p2) {
                    return p1.getIdSucursal().compareTo(p2.getIdSucursal());
                }
            };
            Collections.sort(Globales.lstIdsComerciosUsuario, c2);
            Collections.sort(Globales.lstTodosLosComerciosSistema, c2);

            for (int i = 0; i < Globales.lstIdsComerciosUsuario.size(); i++) {
                System.out.println("Globales.lstTodosLosComerciosSistema " + Globales.lstTodosLosComerciosSistema);
                System.out.println("Globales.lstIdsComerciosUsuario " + Globales.lstIdsComerciosUsuario);
                Globales.lstTodosLosComerciosSistema.get(i).setComercioFavorito(Globales.lstIdsComerciosUsuario.get(i).getComercioFavorito());
                Globales.lstTodosLosComerciosSistema.get(i).setCategoriaFavorita(Globales.lstIdsComerciosUsuario.get(i).getCategoriaFavorita());
                Globales.lstTodosLosComerciosSistema.get(i).setIdCategoria(Globales.lstIdsComerciosUsuario.get(i).getIdCategoria());
            }
        }
*/

    public static BeanDispositivoPosicion localizarPosicionDispositivo(Context context, Activity activity) {

        BeanDispositivoPosicion beanDispositivoPosicion = new BeanDispositivoPosicion();
        LocalizacionDispositivo localizacionDispositivo = new LocalizacionDispositivo(context, activity);

        double latitud;
        double longitud;
        localizacionDispositivo.calcularLocalizacion();
        if (localizacionDispositivo.getLocalizacion() != null) {
            latitud = localizacionDispositivo.getLocalizacion().getLatitude();
            longitud = localizacionDispositivo.getLocalizacion().getLongitude();
            System.out.println("lat " + latitud);
            System.out.println("lon " + longitud);
        } else {
            latitud = 0.0;
            longitud = 0.0;
        }
        beanDispositivoPosicion.setLatitud(latitud);
        beanDispositivoPosicion.setLongitud(longitud);

        return beanDispositivoPosicion;
    }

    //me permite saber si hay una conexión a Internet disponible o no
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    //I check for both Wi-fi and Mobile internet as follows
    public static boolean haveNetworkConnection(Activity activity) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
