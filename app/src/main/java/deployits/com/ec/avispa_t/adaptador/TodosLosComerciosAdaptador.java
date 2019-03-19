package deployits.com.ec.avispa_t.adaptador;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ec.deployits.cupones.bean.BeanRespuesta;
import com.ec.deployits.cupones.bean.BeanSucursal;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

public class TodosLosComerciosAdaptador extends ArrayAdapter <BeanSucursal> {

    private ViewHolder viewHolder;
    private Conexion conexion;
    private BeanSucursal beanSucursal;
    private BeanRespuesta res;
    private Context mContext;

    public TodosLosComerciosAdaptador(Context context, @NonNull List <BeanSucursal> objects) {
        super(context, R.layout.activity_sucursal_matriz, objects);

        this.mContext = context;
        Globales.lstComerciosPorCategoriaAdaptador = objects;

        this.conexion = new Conexion();
    }

    @NonNull
    @Override
    public View getView(final int posicion, View item, ViewGroup parent) {
        //cuando es null es la primera vez que se crea el item

        if (item == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            item = inflater.inflate(R.layout.activity_sucursal_matriz, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.nomEmp = item.findViewById(R.id.txtvNombreEmp);
            viewHolder.likeButton = item.findViewById(R.id.btnAgregaSucursal);
            viewHolder.imagen = item.findViewById(R.id.imgEstablecimiento);

            Picasso.with(parent.getContext())
                    .load(Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getUbicacion())
                    .into(viewHolder.imagen);

            item.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) item.getTag();
        }

        viewHolder.nomEmp.setText(Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getNombre());
        // muestra el estado de la estrella
        viewHolder.likeButton.setLiked(Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getComercioFavorito());

        if (Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getComercioFavorito())
            viewHolder.likeButton.setLiked(true);

        beanSucursal = new BeanSucursal();
        /* metodo que escucha los eventos del likeButton */
        viewHolder.likeButton.setOnLikeListener(new OnLikeListener() {
            Toast toast;
            String nombreEmpresa = Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getNombre();

            @Override
            public void liked(LikeButton likeButton) {

                if (Globales.beanCliente.getCorreo() == null) {
                    viewHolder.likeButton.setLiked(false);
                    Toast.makeText(mContext, "Inicia sesión para guardar favoritos", Toast.LENGTH_LONG).show();
                } else {

                    Globales.lstComerciosPorCategoriaAdaptador.get(posicion).setComercioFavorito(true);
                    likeButton.setLiked(true);
//
                    beanSucursal.setIdSucursal(Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getIdSucursal());
                    try {
                        res = conexion.registraEntidadFavorita(Globales.beanCliente, beanSucursal);

                        if (res.getCodigoError() == 0) {
                            toast = Toast.makeText(getContext(), nombreEmpresa + " añadido correctamente", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.show();
                        }
                    } catch (ConexionHostExcepcion conexionHostExcepcion) {
                        conexionHostExcepcion.printStackTrace();
                        System.out.println("conexionHostExcepcion " + conexionHostExcepcion);
                    }
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Globales.lstComerciosPorCategoriaAdaptador.get(posicion).setComercioFavorito(false);
                viewHolder.likeButton.setLiked(false);
//
                beanSucursal.setIdSucursal(Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getIdSucursal());
                try {
                    res = conexion.eliminarEntidadFavorita(Globales.beanCliente, beanSucursal);
                    //                   notifyDataSetInvalidated();
                    toast = Toast.makeText(getContext(), nombreEmpresa + " eliminado", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                } catch (ConexionHostExcepcion conexionHostExcepcion) {
                    conexionHostExcepcion.printStackTrace();
                    System.out.println("conexionHostExcepcion " + conexionHostExcepcion);
                }
            }
        });
        // Devolvemos la vista para que se muestre en el GridView.
        return item;
    }

    static class ViewHolder {
        ImageView imagen;
        TextView nomEmp;
        LikeButton likeButton;
    }
}
