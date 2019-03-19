package deployits.com.ec.avispa_t.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ec.deployits.cupones.bean.BeanCliente;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import deployits.com.ec.avispa_t.excepcion.ParametroSistemaNoEncontradoExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

public class InfoUsuario extends SQLiteOpenHelper {
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");


    public InfoUsuario(Context context) {
        super(context, Globales.nombre_base_datos, null,
                Globales.version_base_datos);
    }

    @Override
    public void onCreate(SQLiteDatabase em) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE Cliente(");
        sb.append("documento TEXT PRIMARY KEY ");
        sb.append(",nombre TEXT ");
        sb.append(",apellido TEXT ");
        sb.append(",correo TEXT ");
        sb.append(",telefono TEXT ");
        sb.append(",password TEXT ");
        sb.append(",fechaNacimiento TEXT ");
        sb.append(",idGenero INTEGER ");
        sb.append(",domicilio TEXT )");
        em.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertar(BeanCliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("documento", cliente.getDocumento());
            valores.put("nombre", cliente.getNombre());
            valores.put("apellido", cliente.getApellido());
            valores.put("correo", cliente.getCorreo());
            valores.put("telefono", cliente.getTelefono());
            valores.put("password", cliente.getPassword());
            valores.put("fechaNacimiento", formato.format(cliente.getFechaNacimiento()));
            valores.put("idGenero", cliente.getIdGenero());
            valores.put("domicilio", cliente.getDomicilio());
            db.insert("Cliente", null, valores);
            db.close();
        }
    }

    public void actualizar(BeanCliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues valores = new ContentValues();
            valores.put("documento", cliente.getDocumento());
            valores.put("nombre", cliente.getNombre());
            valores.put("apellido", cliente.getApellido());
            valores.put("correo", cliente.getCorreo());
            valores.put("telefono", cliente.getTelefono());
            valores.put("password", cliente.getPassword());
            valores.put("fechaNacimiento", formato.format(cliente.getFechaNacimiento())); //pasa de Date a String
            valores.put("idGenero", cliente.getIdGenero());
            valores.put("domicilio", cliente.getDomicilio());
            db.update("Cliente", valores, "documento = "
                    + cliente.getDocumento(), null);
            db.close();
        }
    }

    public void eliminar(BeanCliente cliente) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = new String[]{cliente.getDocumento() + ""};
        System.out.println("REG ELIMINADOS :"
                + db.delete("Cliente", "documento = ? ", args));
        db.close();
    }

    public BeanCliente obtener(String documento) throws ParametroSistemaNoEncontradoExcepcion {
        SQLiteDatabase db = getReadableDatabase();
        String[] valores_recuperar = {"documento", "nombre", "apellido", "correo", "telefono", "password", "fechaNacimiento", "idGenero", "domicilio"};
        String[] condicion = {documento};
        Cursor c = db.query("Cliente", valores_recuperar,
                "documento = ? ", condicion, null, null,
                null, null);
        BeanCliente cliente = null;
        try {
            cliente = new BeanCliente();
            c.moveToFirst();
            do {
                cliente.setDocumento(c.getString(0));
                cliente.setNombre(c.getString(1));
                cliente.setApellido(c.getString(2));
                cliente.setCorreo(c.getString(3));
                cliente.setTelefono(c.getString(4));
                cliente.setPassword(c.getString(5));
                cliente.setFechaNacimiento(formato.parse(c.getString(6)));//pasa de String a Date
                cliente.setIdGenero(c.getInt(7));
                cliente.setDomicilio(c.getString(8));
            } while (c.moveToNext());
        } catch (CursorIndexOutOfBoundsException e) {
            throw new ParametroSistemaNoEncontradoExcepcion("documento "
                    + documento + " no encontrado");
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            db.close(); //se cierra la cadena de coenxion
            c.close();
        }
        return cliente;
    }
}
