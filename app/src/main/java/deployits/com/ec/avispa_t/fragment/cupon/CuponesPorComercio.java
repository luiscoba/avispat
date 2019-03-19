package deployits.com.ec.avispa_t.fragment.cupon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanSucursal;
import java.util.ArrayList;
import java.util.List;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.adaptador.TodosLosCuponesAdaptador;
import deployits.com.ec.avispa_t.global.Globales;

public class CuponesPorComercio extends Fragment {

    private TodosLosCuponesAdaptador todasLasPromociones;
    private TextView txtNombreEmpresa;
    private GridView lstvTodasLasPromociones;
    private List <BeanPromocion> cuponesPorComercio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStatex) {

        View view = inflater.inflate(R.layout.activity_todas_las_promociones, container, false);

        MetodoPara.ponerBotonFlotante(view, getActivity());

        cuponesPorComercio = new ArrayList <>();

        String nombreSucursal = getArguments().getString("nombre_empresa_seleccionada"); //getIntent().getExtras().getString("nombre_empresa_seleccionada");
        Integer idEmpresa = getArguments().getInt("idEmpresa_seleccionada");//getIntent().getExtras().getInt("idEmpresa_seleccionada");
        txtNombreEmpresa = view.findViewById(R.id.txtNombreEmpresa);
        txtNombreEmpresa.setText(nombreSucursal);

        lstvTodasLasPromociones = view.findViewById(R.id.grdvPromociones);

        for (int i = 0; i < Globales.lstTodosLosCuponesSistema.size(); i++) {
            if (Globales.lstTodosLosCuponesSistema.get(i).getIdEmpresa() == idEmpresa) {
                cuponesPorComercio.add(Globales.lstTodosLosCuponesSistema.get(i));
            }
        }
        todasLasPromociones = new TodosLosCuponesAdaptador(getContext(), cuponesPorComercio);

        lstvTodasLasPromociones.setAdapter(todasLasPromociones);

        lstvTodasLasPromociones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int posicion, long id) {

                BeanSucursal beanSucursal = obtenerSucursalPorIdSucursal(cuponesPorComercio.get(posicion).getIdEmpresa());

                Bundle args = new Bundle();
                args.putInt("idPromocion", cuponesPorComercio.get(posicion).getIdPromocion());
                args.putString("nombreEmpresa", beanSucursal.getNombre());
                args.putDouble("longitud", beanSucursal.getLongitud());
                args.putDouble("latitud", beanSucursal.getLatitud());

                DetalleDeCupon detalleDeCupon = new DetalleDeCupon();
                detalleDeCupon.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenedor, detalleDeCupon, "detalleDeCupon");
                transaction.addToBackStack(null);//con esto se activa el botón 'Atrás'
                transaction.commit();
            }
        });

        return view;
    }

    public BeanSucursal obtenerSucursalPorIdSucursal(Integer idSucursal) {
        BeanSucursal beanSucursal = new BeanSucursal();
        for (BeanSucursal sucursal : Globales.lstTodosLosComerciosSistema) {
            if (sucursal.getIdEmpresa() == idSucursal) {
                beanSucursal = sucursal;
                break;
            }
        }
        return beanSucursal;
    }
}
