package deployits.com.ec.avispa_t.activity.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.ec.deployits.cupones.bean.BeanPromocion;
import java.util.ArrayList;
import java.util.List;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.adaptador.TodosLosCuponesAdaptador;
import deployits.com.ec.avispa_t.bean.BeanMensajeSistema;
import deployits.com.ec.avispa_t.fragment.cupon.DetalleDeCupon;
import deployits.com.ec.avispa_t.global.Globales;

public class ListaDeCuponesGenerados extends AppCompatActivity {

    private BeanMensajeSistema mensaje;
    private TodosLosCuponesAdaptador todosLosCuponesGenerados;
    private GridView lstvTodosLosCuponesGenerados;
    private List <BeanPromocion> lstBeanPromocion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item_cupones_generados);

        Toolbar toolbar = findViewById(R.id.toolbarManual);
        setSupportActionBar(toolbar);
        //       getSupportActionBar().setIcon(R.drawable.usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Globales.fab = findViewById(R.id.fab);
        Globales.fab.setVisibility(View.GONE);

        mensaje = new BeanMensajeSistema(this);

        lstvTodosLosCuponesGenerados = findViewById(R.id.grdvCuponesSeleccionados);

        lstBeanPromocion = new ArrayList <>();
        for (int i = 0; i < Globales.lstTodosLosCuponesSistema.size(); i++) {
            System.out.println("idEstadoNotificacion " + Globales.lstTodosLosCuponesSistema.get(i).getIdEstadoNotificacion() + " CuponesSistema.get(i) " + Globales.lstTodosLosCuponesSistema.get(i));
            if (Globales.lstTodosLosCuponesSistema.get(i).getIdEstadoNotificacion() == Globales.estado_de_notificacion_generado) {
                lstBeanPromocion.add(Globales.lstTodosLosCuponesSistema.get(i));
            }
        }

        if (lstBeanPromocion.isEmpty()) {
            mensaje.mostrarMensajeToastActivity("No tiene cupones generados");
            finish();
        } else {
            todosLosCuponesGenerados = new TodosLosCuponesAdaptador(this, lstBeanPromocion);
            lstvTodosLosCuponesGenerados.setAdapter(todosLosCuponesGenerados);

            lstvTodosLosCuponesGenerados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {

                    Bundle args = new Bundle();
                    args.putBoolean("listaDeCuponesGenerados", true);
                    args.putInt("idPromocion", lstBeanPromocion.get(position).getIdPromocion());

                    DetalleDeCupon detalleDeCupon = new DetalleDeCupon();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();//usas esto cuando lanzas un fragment desde un activity
                    detalleDeCupon.setArguments(args);
                    transaction.replace(R.id.contenedor, detalleDeCupon, "detalleDeCupon");
                    transaction.addToBackStack(null);//con esto se activa el botón 'Atrás'
                    transaction.commit();
                    System.out.println("se agregó detalleDeCupon desde ListaDeCuponesGenerados");

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