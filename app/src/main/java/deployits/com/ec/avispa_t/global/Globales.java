package deployits.com.ec.avispa_t.global;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.widget.Spinner;

import com.ec.deployits.cupones.bean.BeanCategoria;
import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanSucursal;

import java.util.List;

public class Globales {

    public static final int version_base_datos = 1;
    public static final String nombre_base_datos = "xxx.db";

    public static Integer genero_hombre = 1;
    public static Integer genero_mujer = 2;
    public static Integer estado_de_notificacion_generado = 2;
    public static Integer estado_de_notificacion_cancelado = 4;

    public static BeanCliente beanCliente;

    public static Integer año_inicial_datePicker = 1990;
    public static Integer mes_inicial_datePicker = 0; //los meses empiezan en cero
    public static Integer dia_inicial_datePicker = 1;

    public static List <BeanCategoria> lstCategorias;
    public static List <String> lstCategoriasStrSpinner;// lista de categorias que se muestra en el Spinner

    public static List <BeanPromocion> lstTodosLosCuponesSistema;//tiene todos lo cupones del sistema
    public static List <BeanSucursal> lstTodosLosComerciosSistema;//tiene todos los comercios del sistema

    public static List <BeanPromocion> lstCuponesPorCategoriaAdaptador;
    public static List <BeanSucursal> lstComerciosPorCategoriaAdaptador;

    public static List <BeanPromocion> lstIdsCuponesUsuario; //solo contiene el idPromocion
    public static List <BeanSucursal> lstIdsComerciosUsuario; //solo contiene el idSucursal

    public static String item_Spinner_todas_las_categorias = "Todas las categorias";

    public static TabLayout tabLayout;
    public static Spinner spinner;
    public static FloatingActionButton fab;

    public static boolean regresoMainActivityBtnFlotante;
    public  static boolean regresoDesdeSinConexionActivity;

    public static  boolean seActivoLogin;

}
