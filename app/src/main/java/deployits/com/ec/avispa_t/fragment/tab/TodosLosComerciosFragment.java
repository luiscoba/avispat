package deployits.com.ec.avispa_t.fragment.tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.fragment.cupon.CuponesPorComercio;
import deployits.com.ec.avispa_t.adaptador.TodosLosComerciosAdaptador;
import deployits.com.ec.avispa_t.global.Globales;

//aqui se muestran todos los comercios afiliados
public class TodosLosComerciosFragment extends Fragment {

    private TodosLosComerciosAdaptador comerciosAdaptador;
    private GridView lstvTodosLosComercios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStatex) {

        View view = inflater.inflate(R.layout.activity_todos_los_comercios, container, false);

        lstvTodosLosComercios = view.findViewById(R.id.grdvComercios);
        comerciosAdaptador = new TodosLosComerciosAdaptador(this.getContext(), Globales.lstComerciosPorCategoriaAdaptador);
        lstvTodosLosComercios.setAdapter(comerciosAdaptador);
        lstvTodosLosComercios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int posicion, long id) {

                Globales.tabLayout.setVisibility(View.GONE);
                Globales.spinner.setVisibility(View.GONE);//oculta el Spinner

                Integer idSucursal = Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getIdSucursal();
                Integer idEmpresa = Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getIdEmpresa();
                String nombreSucursal = Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getNombre();

                Bundle args = new Bundle();
                args.putInt("empresa_favorita_seleccionada", idSucursal);
                args.putInt("idEmpresa_seleccionada", idEmpresa);
                args.putString("nombre_empresa_seleccionada", nombreSucursal);

                CuponesPorComercio cuponesPorComercio = new CuponesPorComercio();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                cuponesPorComercio.setArguments(args);
                transaction.replace(R.id.contenedor, cuponesPorComercio, "cuponesPorComercio");
                transaction.addToBackStack(null);//con esto se activa el botón 'Atrás'
                transaction.commit();
                System.out.println("se agregó cuponesPorComercio desde TodosLosComerciosFragment");

            }
        });

        return view;
    }

}
