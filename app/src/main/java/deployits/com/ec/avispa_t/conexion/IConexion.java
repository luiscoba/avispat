package deployits.com.ec.avispa_t.conexion;

import com.ec.deployits.cupones.bean.BeanCategoria;
import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanDispositivoPosicion;
import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanRespuesta;
import com.ec.deployits.cupones.bean.BeanSucursal;

import java.util.List;

import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;

public interface IConexion {
    BeanRespuesta inicioSesionCliente(BeanCliente beanCliente, BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion;

    BeanCliente obtenerDatosDeUsuario(BeanCliente beanCliente) throws ConexionHostExcepcion;

    BeanRespuesta enrolamientoCliente(BeanCliente beanCliente, BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion;

    BeanRespuesta recuperarCuenta(BeanCliente beanCliente) throws ConexionHostExcepcion;

    BeanRespuesta grabarNuevaContrasena(BeanCliente beanCliente, BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion;

    List <BeanSucursal> obtenerTodosLosComercios(BeanCliente beanCliente) throws ConexionHostExcepcion;

    List <BeanSucursal> obtenerTodosLosIdsDeLosComerciosFavoritos(BeanCliente beanCliente) throws ConexionHostExcepcion;

    List <BeanSucursal> obtenerTodosLosComerciosFavoritos(BeanCliente beanCliente) throws ConexionHostExcepcion;

    BeanRespuesta registraEntidadFavorita(BeanCliente beanCliente, BeanSucursal beanSucursal) throws ConexionHostExcepcion;

    BeanRespuesta eliminarEntidadFavorita(BeanCliente beanCliente, BeanSucursal beanSucursal) throws ConexionHostExcepcion;

    List <BeanPromocion> obtenerTodasLasPromociones(BeanCliente beanCliente) throws ConexionHostExcepcion;

    List <BeanPromocion> notificarNuevasPromocionesCercanas(BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion;

    List <BeanPromocion> obtenerTodosLosIdsDeLasPromocionesGeneradas(BeanCliente beanCliente) throws ConexionHostExcepcion;

    List <BeanPromocion> obtenerTodasLasPromocionesPorComercio(BeanCliente beanCliente, BeanSucursal beanSucursal) throws ConexionHostExcepcion;

    List <BeanPromocion> obtenerPromocionesFavoritasPorComercio(BeanCliente beanCliente) throws ConexionHostExcepcion;

    BeanRespuesta generaCuponDescuento(BeanCliente beanCliente, BeanPromocion beanPromocion) throws ConexionHostExcepcion;

    BeanRespuesta eliminaCuponDescuento(BeanCliente beanCliente, BeanPromocion beanPromocion) throws ConexionHostExcepcion;

    List <BeanPromocion> promocionesDeComerciosFavoritos(BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion;

    //los cupones generados son los cupones que seleccionó en la app
    List <BeanPromocion> obtenerCuponesGeneradosSinConsumir(BeanCliente beanCliente) throws ConexionHostExcepcion;

    List <BeanCategoria> obtenerTodasLasCategorias(BeanCliente beanCliente) throws ConexionHostExcepcion;

    BeanRespuesta registrarCategoriaFavorita(BeanCliente beanCliente, BeanCategoria beanCategoria) throws ConexionHostExcepcion;

    BeanRespuesta eliminarCategoriaFavorita(BeanCliente beanCliente, BeanCategoria beanCategoria) throws ConexionHostExcepcion;

}
