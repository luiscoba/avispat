package deployits.com.ec.avispa_t.adaptador;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ec.deployits.cupones.bean.BeanPromocion;
import com.squareup.picasso.Picasso;

import java.util.List;

import deployits.com.ec.avispa_t.R;

public class TodosLosCuponesAdaptador extends ArrayAdapter <BeanPromocion> {

    private ViewHolder viewHolder;
    private List <BeanPromocion> lstCupones;
    private Context mContext;

    public TodosLosCuponesAdaptador(Context context, @NonNull List <BeanPromocion> objects) {
        super(context, R.layout.activity_promocion, objects);

        this.lstCupones = objects;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(final int posicion, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            // inflamos cada uno de los items del listView con una promocion
            convertView = inflater.inflate(R.layout.activity_promocion, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imagen = convertView.findViewById(R.id.imgPromocion);
            viewHolder.nombreSucursal = convertView.findViewById(R.id.txtvSucursal);
            viewHolder.direccion = convertView.findViewById(R.id.txtvDireccion);
            viewHolder.cantidad = convertView.findViewById(R.id.txtvCantidad);
            viewHolder.valorTotal = convertView.findViewById(R.id.txtvValorTotal);
            viewHolder.valorTotal.setPaintFlags(viewHolder.valorTotal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.valorDescuento = convertView.findViewById(R.id.txtvValorDescuento);

            Picasso.with(parent.getContext())
                    .load(lstCupones.get(posicion).getUbicacion())
                    .into(viewHolder.imagen);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (TodosLosCuponesAdaptador.ViewHolder) convertView.getTag();
        }

        viewHolder.nombreSucursal.setText(lstCupones.get(posicion).getNombreEmpresa());
        viewHolder.direccion.setText(lstCupones.get(posicion).getDireccionSucursal());
        viewHolder.cantidad.setText(String.valueOf(lstCupones.get(posicion).getCantidad()));
        viewHolder.valorTotal.setText(String.valueOf(lstCupones.get(posicion).getValorTotal()));
        viewHolder.valorDescuento.setText(String.valueOf(lstCupones.get(posicion).getValorDescuento()));

        return convertView;
    }

    static class ViewHolder {
        ImageView imagen;
        TextView nombreSucursal;
        TextView direccion;
        TextView cantidad;
        TextView valorTotal;
        TextView valorDescuento;
    }
}
