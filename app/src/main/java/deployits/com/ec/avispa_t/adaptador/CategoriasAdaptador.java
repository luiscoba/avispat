package deployits.com.ec.avispa_t.adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec.deployits.cupones.bean.BeanCategoria;
import com.ec.deployits.cupones.bean.BeanRespuesta;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.excepcion.ConexionHostExcepcion;
import deployits.com.ec.avispa_t.global.Globales;

public class CategoriasAdaptador extends ArrayAdapter <BeanCategoria> {

    private Conexion conexion;

    private BeanCategoria beanCategoria;
    private BeanRespuesta res;
    private Context context;

    private LikeButton likeButton;
    private BeanMensajeSistema mensaje;


    public CategoriasAdaptador(Context context, @NonNull List <BeanCategoria> lstCategorias) {
        super(context, R.layout.todas_las_categorias, lstCategorias);
        this.context = context;

        Globales.lstCategorias = lstCategorias;

        beanCategoria = new BeanCategoria();

        conexion = new Conexion();

    }

    @Override
    public View getView(final int posicion, View item, ViewGroup parent) {

        mensaje = new BeanMensajeSistema(this.context);

        LayoutInflater inflater = LayoutInflater.from(context);
        item = inflater.inflate(R.layout.activity_item_categoria, null);

        TextView nombreCategoria = item.findViewById(R.id.txtvNombreCategoria);
        nombreCategoria.setText(Globales.lstCategorias.get(posicion).getNombre());

        ImageView imagen = item.findViewById(R.id.imgCategoria);
//        byte[] image = Base64.decode(Globales.lstCategorias.get(posicion).getImagen(), Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
//        imagen.setImageBitmap(bitmap);
        Picasso.with(getContext())
                .load(Globales.lstCategorias.get(posicion).getUbicacion())
                .into(imagen);

        likeButton = item.findViewById(R.id.btnAgregaCategoria);

        if (Globales.lstCategorias.get(posicion).getFavorito() == true)
            likeButton.setLiked(true);
        else
            likeButton.setLiked(false);

         /* metodo que escucha los eventos del likeButton */
        likeButton.setOnLikeListener(new OnLikeListener() {

            @Override
            public void liked(LikeButton likeButton) {
                try {
                    System.out.println("idCategoria " + beanCategoria.getIdCategoria()+ " nombre " +beanCategoria.getNombre());
                    beanCategoria.setIdCategoria(Globales.lstCategorias.get(posicion).getIdCategoria());
                    System.out.println("idCategoria " + beanCategoria.getIdCategoria()+ " nombre " +beanCategoria.getNombre());
                    res = conexion.registrarCategoriaFavorita(Globales.beanCliente, beanCategoria);

                    if (res.getCodigoError() == 0) {
                        colocarEstadoEnListas(posicion, true);
                        Globales.lstCategorias.get(posicion).setFavorito(true);
                        likeButton.setLiked(true);
                    }
                } catch (ConexionHostExcepcion conexionHostExcepcion) {
                    conexionHostExcepcion.printStackTrace();
                    System.out.println("conexionHostExcepcion " + conexionHostExcepcion);
                }
                mensaje.mostrarMensajeToastContext(res.getDescripcionError());
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                try {

                    beanCategoria.setIdCategoria(Globales.lstCategorias.get(posicion).getIdCategoria());
                    res = conexion.eliminarCategoriaFavorita(Globales.beanCliente, beanCategoria);

                    if (res.getCodigoError() == 0) {
                        colocarEstadoEnListas(posicion, false);
                        Globales.lstCategorias.get(posicion).setFavorito(false);
                        likeButton.setLiked(false);
                    }

                } catch (ConexionHostExcepcion conexionHostExcepcion) {
                    conexionHostExcepcion.printStackTrace();
                    System.out.println("conexionHostExcepcion " + conexionHostExcepcion);
                }
                mensaje.mostrarMensajeToastContext(res.getDescripcionError());
            }
        });
        return item;
    }

    public void colocarEstadoEnListas(int posicion, boolean estado) {
        System.out.println("colocarEstadoEnListas");
        for (int i = 0; i < Globales.lstComerciosPorCategoriaAdaptador.size(); i++) {
            if (Globales.lstComerciosPorCategoriaAdaptador.get(i).getIdCategoria() == Globales.lstCategorias.get(posicion).getIdCategoria()) {
                Globales.lstComerciosPorCategoriaAdaptador.get(i).setCategoriaFavorita(estado);
            }
            System.out.println("isFavorita " + Globales.lstComerciosPorCategoriaAdaptador.get(i).getCategoriaFavorita() + " nombre " + Globales.lstComerciosPorCategoriaAdaptador.get(i).getNombre() + "idSucursal " + Globales.lstComerciosPorCategoriaAdaptador.get(i).getIdSucursal() + " idCategoria " + Globales.lstComerciosPorCategoriaAdaptador.get(i).getIdCategoria());
        }

        for (int i = 0; i < Globales.lstCuponesPorCategoriaAdaptador.size(); i++) {
            if (Globales.lstCuponesPorCategoriaAdaptador.get(i).getIdCategoria() == Globales.lstCategorias.get(posicion).getIdCategoria()) {
                Globales.lstCuponesPorCategoriaAdaptador.get(i).setCategoriaFavorita(estado);
            }
        }
    }
}

