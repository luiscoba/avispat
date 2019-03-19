package deployits.com.ec.avispa_t.activity.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.ec.deployits.cupones.bean.BeanSucursal;
import java.util.ArrayList;
import java.util.List;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.adaptador.TodosLosComerciosAdaptador;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.fragment.cupon.CuponesPorComercio;
import deployits.com.ec.avispa_t.global.Globales;

public class ComerciosFavoritos extends AppCompatActivity {

    private BeanMensajeSistema mensaje;
    private TodosLosComerciosAdaptador todosLosComerciosFavoritos;
    private GridView lstvTodosLosComercios;
    private List <BeanSucursal> lstBeanSucursal = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comercios_favoritos);

        Toolbar toolbar = findViewById(R.id.toolbarManual);
        setSupportActionBar(toolbar);
        //       getSupportActionBar().setIcon(R.drawable.usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Globales.fab = findViewById(R.id.fab);
        Globales.fab.setVisibility(View.GONE);

        mensaje = new BeanMensajeSistema(this);

        lstvTodosLosComercios = findViewById(R.id.grdvComercios);

        for (BeanSucursal sucursal : Globales.lstTodosLosComerciosSistema) {
            //for (BeanSucursal sucursal : Globales.lstTodosLosComerciosUsuario) {
            if (sucursal.getComercioFavorito() == true)
                lstBeanSucursal.add(sucursal);
        }
        if (lstBeanSucursal.isEmpty()) {
            mensaje.mostrarMensajeToastActivity("No tiene comercios favoritos");
            finish();
        } else {
            todosLosComerciosFavoritos = new TodosLosComerciosAdaptador(this, lstBeanSucursal);
            lstvTodosLosComercios.setAdapter(todosLosComerciosFavoritos);

            lstvTodosLosComercios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int posicion, long id) {

                    Integer idSucursal =Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getIdSucursal();
                    Integer idEmpresa = Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getIdEmpresa();
                    String nombreSucursal = Globales.lstComerciosPorCategoriaAdaptador.get(posicion).getNombre();

                    Bundle args = new Bundle();
                    args.putInt("empresa_favorita_seleccionada", idSucursal);
                    args.putInt("idEmpresa_seleccionada", idEmpresa);
                    args.putString("nombre_empresa_seleccionada", nombreSucursal);

                    CuponesPorComercio cuponesPorComercio = new CuponesPorComercio();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    cuponesPorComercio.setArguments(args);
                    transaction.replace(R.id.contenedor, cuponesPorComercio, "cuponesPorComercio");
                    transaction.addToBackStack(null);//con esto se activa el botón 'Atrás'
                    transaction.commit();
                    System.out.println("se agregó cuponesPorComercio desde ComerciosFavoritos");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//aca debes colocar el metodo que deseas que retorne
        return true;
    }

}
