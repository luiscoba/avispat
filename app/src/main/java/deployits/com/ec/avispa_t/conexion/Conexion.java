package deployits.com.ec.avispa_t.conexion;

import com.ec.deployits.cupones.bean.BeanCategoria;
import com.ec.deployits.cupones.bean.BeanCliente;
import com.ec.deployits.cupones.bean.BeanDispositivoPosicion;
import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanRespuesta;
import com.ec.deployits.cupones.bean.BeanSucursal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;

//@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING) con esto mejora el SOAP
public class Conexion implements IConexion {
    private boolean doNet = false;
    private String nameSpace = "http://webService.deployits.ec.com/";
    //    private String ipHost = "192.168.100.100"; ya no usar esta, es del servidor
    private String ipHost = "186.4.165.101";  // con esta salimos a internet
//    private String ipHost = "192.168.100.31"; //direccion de mi máquina para hacer pruebas
    private String puerto = "8080";
    private String fileUrl = "/AppPromociones/ServicioPromocionWS?wsdl";
    private Integer timeOut;
    private Gson gson;
    private String url;
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

    public Conexion() {
        gson = new Gson();
        url = "http://" + ipHost + ":" + puerto + fileUrl;
        timeOut = 500;
    }

    @Override
    public BeanRespuesta inicioSesionCliente(BeanCliente beanCliente, BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "inicioSesionCliente");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // varriable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanDispositivoPosicion));
        atributo2.setName("beanDispositivoPosicion");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url,
                timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/inicioSesionCliente", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();

            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexion al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public BeanCliente obtenerDatosDeUsuario(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerDatosDeUsuario");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);

        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url,
                timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerDatosDeUsuario", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanCliente.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexion al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public BeanRespuesta enrolamientoCliente(BeanCliente beanCliente, BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "enrolamientoCliente");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // varriable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanDispositivoPosicion));
        atributo2.setName("beanDispositivoPosicion");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url,
                timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/enrolamientoCliente", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexion al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public BeanRespuesta recuperarCuenta(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "recuperarCuenta");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url,
                timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/recuperarCuenta", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexion al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public BeanRespuesta grabarNuevaContrasena(BeanCliente beanCliente, BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "grabarNuevaContrasena");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // varriable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanDispositivoPosicion));
        atributo2.setName("beanDispositivoPosicion");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url,
                timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/grabarNuevaContrasena", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexion al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public List <BeanSucursal> obtenerTodosLosComercios(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerTodosLosComercios");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        List <BeanSucursal> lstBeanSucursal = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerTodosLosComercios", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();

            //System.out.println("llego objStr : " + objStr); // para ver el objeto q llega
            TypeToken<List<BeanSucursal>> token = new TypeToken<List<BeanSucursal>>() {
            };
            lstBeanSucursal = new Gson().fromJson(objStr, token.getType());
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListSucursalesObj = parser.parse(objStr);

            List <BeanSucursal> lstBeanSucursal = new ArrayList <>();
            for (JsonElement e : beanListSucursalesObj.getAsJsonArray()) {
                BeanSucursal obj = new BeanSucursal();
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setNombre(e.getAsJsonObject().get("nombre").getAsString());
                obj.setLatitud(e.getAsJsonObject().get("latitud").getAsDouble());
                obj.setLongitud(e.getAsJsonObject().get("longitud").getAsDouble());
                obj.setDireccion(e.getAsJsonObject().get("direccion").getAsString());
                obj.setTelefono(e.getAsJsonObject().get("telefono").getAsString());
                obj.setCategoria(e.getAsJsonObject().get("categoria").getAsString());
                obj.setComercioFavorito(e.getAsJsonObject().get("comercioFavorito").getAsBoolean());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());

                lstBeanSucursal.add(obj);
            }*/
//            System.out.println("tamaño " + lstBeanSucursal.size());

        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanSucursal;
    }

    @Override
    public List <BeanSucursal> obtenerTodosLosIdsDeLosComerciosFavoritos(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerTodosLosIdsDeLosComerciosFavoritos");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);

        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        List <BeanSucursal> lstBeanSucursal = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerTodosLosIdsDeLosComerciosFavoritos", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("llego objStr : " + objStr); // para ver el objeto q llega
            TypeToken<List<BeanSucursal>> token = new TypeToken<List<BeanSucursal>>() {
            };
            lstBeanSucursal = new Gson().fromJson(objStr, token.getType());
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListSucursalesObj = parser.parse(objStr);

            List <BeanSucursal> lstBeanSucursal = new ArrayList <>();
            for (JsonElement e : beanListSucursalesObj.getAsJsonArray()) {
                BeanSucursal obj = new BeanSucursal();
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setComercioFavorito(e.getAsJsonObject().get("comercioFavorito").getAsBoolean());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());

                lstBeanSucursal.add(obj);
            }*/
//            System.out.println("tamaño " + lstBeanSucursal.size());

        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanSucursal;
    }

    @Override
    public List <BeanSucursal> obtenerTodosLosComerciosFavoritos(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerTodosLosComerciosFavoritos");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
//        // varriable de entrada 2 @WebParam
//        PropertyInfo atributo2 = new PropertyInfo();
//        atributo2.setValue(gson.toJson(beanDispositivoPosicion));
//        atributo2.setName("beanDispositivoPosicion");
//        atributo2.setType("xs:string");
//        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        List <BeanSucursal> lstBeanSucursal = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerTodosLosComerciosFavoritos", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("llego objStr : " + objStr); // para ver el objeto q llega
            TypeToken<List<BeanSucursal>> token = new TypeToken<List<BeanSucursal>>() {
            };
            lstBeanSucursal = new Gson().fromJson(objStr, token.getType());
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListSucursalesObj = parser.parse(objStr);

            List <BeanSucursal> lstBeanSucursal = new ArrayList <>();
            for (JsonElement e : beanListSucursalesObj.getAsJsonArray()) {
                BeanSucursal obj = new BeanSucursal();
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setNombre(e.getAsJsonObject().get("nombre").getAsString());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());
                obj.setLatitud(e.getAsJsonObject().get("latitud").getAsDouble());
                obj.setLongitud(e.getAsJsonObject().get("longitud").getAsDouble());
                obj.setDireccion(e.getAsJsonObject().get("direccion").getAsString());
                obj.setTelefono(e.getAsJsonObject().get("telefono").getAsString());
                obj.setCategoria(e.getAsJsonObject().get("categoria").getAsString());
                obj.setComercioFavorito(e.getAsJsonObject().get("comercioFavorito").getAsBoolean());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());

                lstBeanSucursal.add(obj);
            }*/
//            System.out.println("tamaño " + lstBeanSucursal.size());
            return lstBeanSucursal;
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
    }

    @Override
    public BeanRespuesta registraEntidadFavorita(BeanCliente beanCliente, BeanSucursal beanSucursal) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "registraEntidadFavorita");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // variable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanSucursal));
        atributo2.setName("beanSucursal");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/registraEntidadFavorita", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexion al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public BeanRespuesta eliminarEntidadFavorita(BeanCliente beanCliente, BeanSucursal beanSucursal) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "eliminarEntidadFavorita");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // variable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanSucursal));
        atributo2.setName("beanSucursal");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/eliminarEntidadFavorita", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public List <BeanPromocion> obtenerTodasLasPromociones(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerTodasLasPromociones");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanPromocion> lstBeanPromocion = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerTodasLasPromociones", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("llego objStr : " + objStr);

            // usamos esta clase para obtener la lista de sucursales en formato JSON
            //JsonParser parser = new JsonParser();
            //JsonElement beanListPromocionesObj = parser.parse(objStr);
            TypeToken<List<BeanPromocion>> token = new TypeToken<List<BeanPromocion>>() {            };
            lstBeanPromocion = new Gson().fromJson(objStr, token.getType());
            /*for (JsonElement e : beanListPromocionesObj.getAsJsonArray()) {
                BeanPromocion obj = new BeanPromocion();
                obj.setIdPromocion(e.getAsJsonObject().get("idPromocion").getAsInt());
                obj.setIdEstadoPromocion(e.getAsJsonObject().get("idEstadoPromocion").getAsInt());
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setIdFormaDePago(e.getAsJsonObject().get("idFormaDePago").getAsInt());
                obj.setTitulo(e.getAsJsonObject().get("titulo").getAsString());
                obj.setDetalle(e.getAsJsonObject().get("detalle").getAsString());
                obj.setCantidad(e.getAsJsonObject().get("cantidad").getAsInt());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());
                obj.setFechaInicio(e.getAsJsonObject().get("fechaInicio").getAsString());
                obj.setFechaFin(e.getAsJsonObject().get("fechaFin").getAsString());
                obj.setValorTotal(e.getAsJsonObject().get("valorTotal").getAsDouble());
                obj.setValorDescuento(e.getAsJsonObject().get("valorDescuento").getAsDouble());
                obj.setPorcentajeDescuento(e.getAsJsonObject().get("porcentajeDescuento").getAsDouble());
                obj.setIdEstadoNotificacion(e.getAsJsonObject().get("idEstadoNotificacion").getAsInt());
                obj.setNombreEmpresa(e.getAsJsonObject().get("nombreEmpresa").getAsString());
                obj.setDireccionSucursal(e.getAsJsonObject().get("direccionSucursal").getAsString());
                obj.setCodigoGenerado(e.getAsJsonObject().get("codigoGenerado").getAsString());
                obj.setParticipantes(e.getAsJsonObject().get("participantes").getAsString());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());

                lstBeanPromocion.add(obj);
            }*/
            //System.out.println("tamaño " + lstBeanPromocion.size());
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanPromocion;
    }

    @Override
    public List <BeanPromocion> notificarNuevasPromocionesCercanas(BeanDispositivoPosicion beanDispositivoPosicion) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "notificarNuevasPromocionesCercanas");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanDispositivoPosicion));
        atributo.setName("beanDispositivoPosicion");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanPromocion> lstBeanPromocion = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/notificarNuevasPromocionesCercanas", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();

            TypeToken<List<BeanPromocion>> token = new TypeToken<List<BeanPromocion>>() {            };
            lstBeanPromocion = new Gson().fromJson(objStr, token.getType());

        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanPromocion;
    }

    @Override
    public List <BeanPromocion> obtenerTodosLosIdsDeLasPromocionesGeneradas(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerTodosLosIdsDeLasPromocionesGeneradas");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanPromocion> lstBeanPromocion = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerTodosLosIdsDeLasPromocionesGeneradas", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("llego objStr : " + objStr);
            TypeToken<List<BeanPromocion>> token = new TypeToken<List<BeanPromocion>>() {
            };
            lstBeanPromocion = new Gson().fromJson(objStr, token.getType());
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListPromocionesObj = parser.parse(objStr);

            for (JsonElement e : beanListPromocionesObj.getAsJsonArray()) {
                BeanPromocion obj = new BeanPromocion();

                obj.setIdPromocion(e.getAsJsonObject().get("idPromocion").getAsInt());
                obj.setIdEstadoPromocion(e.getAsJsonObject().get("idEstadoPromocion").getAsInt());
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setIdEstadoNotificacion(e.getAsJsonObject().get("idEstadoNotificacion").getAsInt());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdPromocion(e.getAsJsonObject().get("idCategoria").getAsInt());
                obj.setIdFormaDePago(e.getAsJsonObject().get("idFormaDePago").getAsInt());

                lstBeanPromocion.add(obj);
            }*/
            //System.out.println("tamaño " + lstBeanPromocion.size());
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanPromocion;
    }

    @Override
    public List <BeanPromocion> obtenerTodasLasPromocionesPorComercio(BeanCliente beanCliente, BeanSucursal beanSucursal) throws
            ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerTodasLasPromocionesPorComercio");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // varriable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanSucursal));
        atributo2.setName("beanSucursal");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanPromocion> lstBeanPromocion = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerTodasLasPromocionesPorComercio", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("llego objStr : " + objStr);
            TypeToken<List<BeanPromocion>> token = new TypeToken<List<BeanPromocion>>() {
            };
            lstBeanPromocion = new Gson().fromJson(objStr, token.getType());
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListPromocionesObj = parser.parse(objStr);

            for (JsonElement e : beanListPromocionesObj.getAsJsonArray()) {
                BeanPromocion obj = new BeanPromocion();
                obj.setIdPromocion(e.getAsJsonObject().get("idPromocion").getAsInt());
                obj.setIdEstadoPromocion(e.getAsJsonObject().get("idEstadoPromocion").getAsInt());
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setIdFormaDePago(e.getAsJsonObject().get("idFormaDePago").getAsInt());
                obj.setTitulo(e.getAsJsonObject().get("titulo").getAsString());
                obj.setDetalle(e.getAsJsonObject().get("detalle").getAsString());
                obj.setCantidad(e.getAsJsonObject().get("cantidad").getAsInt());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());
                obj.setFechaInicio(e.getAsJsonObject().get("fechaInicio").getAsString());
                obj.setFechaFin(e.getAsJsonObject().get("fechaFin").getAsString());
                obj.setValorTotal(e.getAsJsonObject().get("valorTotal").getAsDouble());
                obj.setValorDescuento(e.getAsJsonObject().get("valorDescuento").getAsDouble());
                obj.setPorcentajeDescuento(e.getAsJsonObject().get("porcentajeDescuento").getAsDouble());
                obj.setIdEstadoNotificacion(e.getAsJsonObject().get("idEstadoNotificacion").getAsInt());
                obj.setNombreEmpresa(e.getAsJsonObject().get("nombreEmpresa").getAsString());
                obj.setDireccionSucursal(e.getAsJsonObject().get("direccionSucursal").getAsString());
                obj.setCodigoGenerado(e.getAsJsonObject().get("codigoGenerado").getAsString());
                obj.setParticipantes(e.getAsJsonObject().get("participantes").getAsString());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());

                lstBeanPromocion.add(obj);
            }*/
            //System.out.println("tamaño " + lstBeanPromocion.size());
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanPromocion;
    }

    @Override
    public List <BeanPromocion> obtenerPromocionesFavoritasPorComercio(BeanCliente beanCliente) throws
            ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerPromocionesFavoritasPorComercio");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
//        // varriable de entrada 2 @WebParam
//        PropertyInfo atributo2 = new PropertyInfo();
//        atributo2.setValue(gson.toJson(beanDispositivoPosicion));
//        atributo2.setName("beanDispositivoPosicion");
//        atributo2.setType("xs:string");
//        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanPromocion> lstBeanPromocion = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerPromocionesFavoritasPorComercio", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("llego objStr : " + objStr);
            TypeToken<List<BeanPromocion>> token = new TypeToken<List<BeanPromocion>>() {
            };
            lstBeanPromocion = new Gson().fromJson(objStr, token.getType());
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListPromocionesObj = parser.parse(objStr);


            for (JsonElement e : beanListPromocionesObj.getAsJsonArray()) {
                BeanPromocion obj = new BeanPromocion();
                obj.setIdPromocion(e.getAsJsonObject().get("idPromocion").getAsInt());
                obj.setIdEstadoPromocion(e.getAsJsonObject().get("idEstadoPromocion").getAsInt());
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setIdFormaDePago(e.getAsJsonObject().get("idFormaDePago").getAsInt());
                obj.setTitulo(e.getAsJsonObject().get("titulo").getAsString());
                obj.setDetalle(e.getAsJsonObject().get("detalle").getAsString());
                obj.setCantidad(e.getAsJsonObject().get("cantidad").getAsInt());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());
                obj.setFechaInicio(e.getAsJsonObject().get("fechaInicio").getAsString());
                obj.setFechaFin(e.getAsJsonObject().get("fechaFin").getAsString());
                obj.setValorTotal(e.getAsJsonObject().get("valorTotal").getAsDouble());
                obj.setValorDescuento(e.getAsJsonObject().get("valorDescuento").getAsDouble());
                obj.setPorcentajeDescuento(e.getAsJsonObject().get("porcentajeDescuento").getAsDouble());
                obj.setIdEstadoNotificacion(e.getAsJsonObject().get("idEstadoNotificacion").getAsInt());
                obj.setNombreEmpresa(e.getAsJsonObject().get("nombreEmpresa").getAsString());
                obj.setDireccionSucursal(e.getAsJsonObject().get("direccionSucursal").getAsString());
                obj.setCodigoGenerado(e.getAsJsonObject().get("codigoGenerado").getAsString());
                obj.setParticipantes(e.getAsJsonObject().get("participantes").getAsString());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());

                lstBeanPromocion.add(obj);
            }*/
            //System.out.println("tamaño " + lstBeanPromocion.size());

        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanPromocion;
    }

    @Override
    public BeanRespuesta generaCuponDescuento(BeanCliente beanCliente, BeanPromocion beanPromocion) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "generaCuponDescuento");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // variable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanPromocion));
        atributo2.setName("beanPromocion");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/generaCuponDescuento", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public BeanRespuesta eliminaCuponDescuento(BeanCliente beanCliente, BeanPromocion beanPromocion) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "eliminaCuponDescuento");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // variable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanPromocion));
        atributo2.setName("beanPromocion");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/eliminaCuponDescuento", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public List <BeanPromocion> promocionesDeComerciosFavoritos(BeanDispositivoPosicion
                                                                        beanDispositivoPosicion) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "promocionesDeComerciosFavoritos");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanDispositivoPosicion));
        atributo.setName("beanDispositivoPosicion");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanPromocion> lstBeanPromocion = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/promocionesDeComerciosFavoritos", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            TypeToken<List<BeanPromocion>> token = new TypeToken<List<BeanPromocion>>() {
            };
            lstBeanPromocion = new Gson().fromJson(objStr, token.getType());
            //System.out.println("llego objStr : " + objStr);
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListPromocionesObj = parser.parse(objStr);

            for (JsonElement e : beanListPromocionesObj.getAsJsonArray()) {
                BeanPromocion obj = new BeanPromocion();
                obj.setIdPromocion(e.getAsJsonObject().get("idPromocion").getAsInt());
                obj.setIdEstadoPromocion(e.getAsJsonObject().get("idEstadoPromocion").getAsInt());
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setIdFormaDePago(e.getAsJsonObject().get("idFormaDePago").getAsInt());
                obj.setTitulo(e.getAsJsonObject().get("titulo").getAsString());
                obj.setDetalle(e.getAsJsonObject().get("detalle").getAsString());
                obj.setCantidad(e.getAsJsonObject().get("cantidad").getAsInt());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());
                obj.setFechaInicio(e.getAsJsonObject().get("fechaInicio").getAsString());
                obj.setFechaFin(e.getAsJsonObject().get("fechaFin").getAsString());
                obj.setValorTotal(e.getAsJsonObject().get("valorTotal").getAsDouble());
                obj.setValorDescuento(e.getAsJsonObject().get("valorDescuento").getAsDouble());
                obj.setPorcentajeDescuento(e.getAsJsonObject().get("porcentajeDescuento").getAsDouble());
                obj.setIdEstadoNotificacion(e.getAsJsonObject().get("idEstadoNotificacion").getAsInt());
                obj.setNombreEmpresa(e.getAsJsonObject().get("nombreEmpresa").getAsString());
                obj.setDireccionSucursal(e.getAsJsonObject().get("direccionSucursal").getAsString());
                obj.setCodigoGenerado(e.getAsJsonObject().get("codigoGenerado").getAsString());
                obj.setParticipantes(e.getAsJsonObject().get("participantes").getAsString());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());

                lstBeanPromocion.add(obj);
            }*/

        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanPromocion;
    }

    @Override
    public List <BeanPromocion> obtenerCuponesGeneradosSinConsumir(BeanCliente beanCliente) throws ConexionHostExcepcion {
// inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerCuponesGeneradosSinConsumir");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);

        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanPromocion> lstBeanPromocion = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerCuponesGeneradosSinConsumir", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            TypeToken<List<BeanPromocion>> token = new TypeToken<List<BeanPromocion>>() {
            };
            lstBeanPromocion = new Gson().fromJson(objStr, token.getType());
            //System.out.println("llego objStr : " + objStr);
            // usamos esta clase para obtener la lista de promociones en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListPromocionesObj = parser.parse(objStr);

            for (JsonElement e : beanListPromocionesObj.getAsJsonArray()) {
                BeanPromocion obj = new BeanPromocion();
                obj.setIdPromocion(e.getAsJsonObject().get("idPromocion").getAsInt());
                obj.setIdEstadoPromocion(e.getAsJsonObject().get("idEstadoPromocion").getAsInt());
                obj.setIdSucursal(e.getAsJsonObject().get("idSucursal").getAsInt());
                obj.setIdEmpresa(e.getAsJsonObject().get("idEmpresa").getAsInt());
                obj.setIdFormaDePago(e.getAsJsonObject().get("idFormaDePago").getAsInt());
                obj.setTitulo(e.getAsJsonObject().get("titulo").getAsString());
                obj.setDetalle(e.getAsJsonObject().get("detalle").getAsString());
                obj.setCantidad(e.getAsJsonObject().get("cantidad").getAsInt());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());
                obj.setFechaInicio(e.getAsJsonObject().get("fechaInicio").getAsString());
                obj.setFechaFin(e.getAsJsonObject().get("fechaFin").getAsString());
                obj.setValorTotal(e.getAsJsonObject().get("valorTotal").getAsDouble());
                obj.setValorDescuento(e.getAsJsonObject().get("valorDescuento").getAsDouble());
                obj.setPorcentajeDescuento(e.getAsJsonObject().get("porcentajeDescuento").getAsDouble());
                obj.setIdEstadoNotificacion(e.getAsJsonObject().get("idEstadoNotificacion").getAsInt());
                obj.setNombreEmpresa(e.getAsJsonObject().get("nombreEmpresa").getAsString());
                obj.setDireccionSucursal(e.getAsJsonObject().get("direccionSucursal").getAsString());
                obj.setCodigoGenerado(e.getAsJsonObject().get("codigoGenerado").getAsString());
                obj.setParticipantes(e.getAsJsonObject().get("participantes").getAsString());
                obj.setCategoriaFavorita(e.getAsJsonObject().get("categoriaFavorita").getAsBoolean());
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());

                lstBeanPromocion.add(obj);
            }*/
            //System.out.println("tamaño " + lstBeanPromocion.size());
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanPromocion;
    }

    @Override
    public List <BeanCategoria> obtenerTodasLasCategorias(BeanCliente beanCliente) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "obtenerTodasLasCategorias");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);

        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);

        List <BeanCategoria> lstBeanCategoria = new ArrayList <>();
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/obtenerTodasLasCategorias", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("llego objStr : " + objStr);
            TypeToken<List<BeanCategoria>> token = new TypeToken<List<BeanCategoria>>() {
            };
            lstBeanCategoria = new Gson().fromJson(objStr, token.getType());
            // usamos esta clase para obtener la lista de sucursales en formato JSON
            /*JsonParser parser = new JsonParser();
            JsonElement beanListCategoriaObj = parser.parse(objStr);

            for (JsonElement e : beanListCategoriaObj.getAsJsonArray()) {
                BeanCategoria obj = new BeanCategoria();
                obj.setIdCategoria(e.getAsJsonObject().get("idCategoria").getAsInt());
                obj.setNombre(e.getAsJsonObject().get("nombre").getAsString());
                obj.setDescripcion(e.getAsJsonObject().get("descripcion").getAsString());
                obj.setFavorito(e.getAsJsonObject().get("favorito").getAsBoolean());
                obj.setImagen(e.getAsJsonObject().get("imagen").getAsString());

                lstBeanCategoria.add(obj);
            }*/
            //System.out.println("tamaño " + lstBeanPromocion.size());

        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            throw new ConexionHostExcepcion("Error en conexión al servidor central");
        }
        return lstBeanCategoria;
    }

    @Override
    public BeanRespuesta registrarCategoriaFavorita(BeanCliente beanCliente, BeanCategoria beanCategoria) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "registrarCategoriaFavorita");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // variable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanCategoria));
        atributo2.setName("beanCategoria");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/registrarCategoriaFavorita", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }

    @Override
    public BeanRespuesta eliminarCategoriaFavorita(BeanCliente beanCliente, BeanCategoria beanCategoria) throws ConexionHostExcepcion {
        // inicioSesionCliente es el nombre de mi servicio web @WebMethod
        SoapObject requerimiento = new SoapObject(nameSpace, "eliminarCategoriaFavorita");
        // variables de entrada @WebParam
        PropertyInfo atributo = new PropertyInfo();
        atributo.setValue(gson.toJson(beanCliente));
        atributo.setName("beanCliente");
        atributo.setType("xs:string");
        requerimiento.addProperty(atributo);
        // variable de entrada 2 @WebParam
        PropertyInfo atributo2 = new PropertyInfo();
        atributo2.setValue(gson.toJson(beanCategoria));
        atributo2.setName("beanCategoria");
        atributo2.setType("xs:string");
        requerimiento.addProperty(atributo2);
        SoapSerializationEnvelope conexion = new SoapSerializationEnvelope(
                SoapEnvelope.VER10);
        conexion.dotNet = doNet;//si el servicio esta hecho en .net
        conexion.setOutputSoapObject(requerimiento);
        HttpTransportSE ws = new HttpTransportSE(url, timeOut * 1000);
        try {
            // soapAction es el valor 'action'
            ws.call("http://com.ec.deployITs.servicioWeb/eliminarCategoriaFavorita", conexion);
            SoapPrimitive resultado = (SoapPrimitive) conexion
                    .getResponse();
            String objStr = resultado.toString();
            //System.out.println("objStr : " + objStr);
            return gson.fromJson(objStr, BeanRespuesta.class);
        } catch (IOException e) {
            System.out.println("IOException " + url);
            e.printStackTrace();
            throw new ConexionHostExcepcion(
                    "Error de conexión al servidor central");
        } catch (XmlPullParserException e) {
            throw new ConexionHostExcepcion("Error en conexion al servidor central");
        }
    }
}
