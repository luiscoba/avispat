package deployits.com.ec.avispa_t.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ec.deployits.cupones.bean.BeanPromocion;
import com.ec.deployits.cupones.bean.BeanSucursal;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.fragment.cupon.DetalleDeCupon;
import deployits.com.ec.avispa_t.adaptador.TodosLosCuponesAdaptador;
import deployits.com.ec.avispa_t.conexion.Conexion;
import deployits.com.ec.avispa_t.global.Globales;

public class TodosLosCuponesFragment extends Fragment {

    private TodosLosCuponesAdaptador cuponesAdaptador;
    private Conexion conexion;
    private GridView lstvTodosLosCupones;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStatex) {

        View view = inflater.inflate(R.layout.fragment_tab_todos_los_cupones, container, false);

        conexion = new Conexion();
        lstvTodosLosCupones = view.findViewById(R.id.grdvCupones);
        cuponesAdaptador = new TodosLosCuponesAdaptador(getActivity(), Globales.lstCuponesPorCategoriaAdaptador);
        lstvTodosLosCupones.setAdapter(cuponesAdaptador);
        lstvTodosLosCupones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int posicion, long id) {

                System.out.println("en TodosLosCuponesFragment");
                for (BeanPromocion cupon : Globales.lstCuponesPorCategoriaAdaptador) {
                    System.out.println("empresa "+cupon.getNombreEmpresa()+ "  idPromocion "+cupon.getIdPromocion() );
                }

                BeanSucursal beanSucursal = obtenerSucursalPorIdPromocion(Globales.lstCuponesPorCategoriaAdaptador.get(posicion).getIdEmpresa());

                Globales.tabLayout.setVisibility(View.GONE);
                Globales.spinner.setVisibility(View.GONE); // oculta el Spinner

                Bundle args = new Bundle();
                args.putInt("idPromocion", Globales.lstCuponesPorCategoriaAdaptador.get(posicion).getIdPromocion());
                args.putString("nombreEmpresa", beanSucursal.getNombre());
                args.putDouble("longitud", beanSucursal.getLongitud());
                args.putDouble("latitud", beanSucursal.getLatitud());

                DetalleDeCupon detalleDeCupon = new DetalleDeCupon();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                detalleDeCupon.setArguments(args);
                transaction.replace(R.id.contenedor, detalleDeCupon, "detalleDeCupon");
                transaction.addToBackStack(null);//con esto se activa el botón 'Atrás'
                transaction.commit();
                System.out.println("se agregó DetalleDeCupon desde TodosLosCuponesFragment");

            }
        });
        return view;
    }

    public BeanSucursal obtenerSucursalPorIdPromocion(Integer idSucursal) {

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