package deployits.com.ec.avispa_t.activity.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.like.LikeButton;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.adaptador.CategoriasAdaptador;
import deployits.com.ec.avispa_t.global.Globales;

public class Categorias extends AppCompatActivity {

    private GridView grdvTodosLasCategorias;
    private CategoriasAdaptador categoriasAdaptador;
    private LikeButton likeButton;

/*   public void setListenerCategorias(ListenerCategorias listenerCategorias) {
        this.mListenerCategorias = listenerCategorias;
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todas_las_categorias);

        MetodoPara.permisoDeEjecucion();

        likeButton = findViewById(R.id.btnAgregaCategoria);

        grdvTodosLasCategorias = findViewById(R.id.grdvCategorias);
        categoriasAdaptador = new CategoriasAdaptador(getApplicationContext(), Globales.lstCategorias);

        categoriasAdaptador.setNotifyOnChange(true);
        grdvTodosLasCategorias.setAdapter(categoriasAdaptador);

        grdvTodosLasCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int posicion, long id) {
                boolean estadoEstrella = Globales.lstCategorias.get(posicion).getFavorito();
                if (estadoEstrella)
                    estadoEstrella = false;
                else
                    estadoEstrella = true;

          //      if(Categorias.this.mListenerCategorias!=null){

          //      }

                System.out.println("Categorias estadoEstrella " + estadoEstrella);
                //    Globales.lstCategorias.get(posicion).setFavorito(estadoEstrella);
                //      likeButton.setLiked(estadoEstrella);
            }
        });

      /*  categoriasAdaptador.setListenerCategorias(new ListenerCategorias() {
            @Override
            public void verificarEstadoEstrella() {

                System.out.println("sobreescritura en Categorias");
            }
        });*/

    }

    public void onClickListo(View view) {

        MetodoPara.ordenarCuponesComerciosPorCategorias();

        categoriasAdaptador.notifyDataSetChanged();
        finish();
        // esta es una animacion que desvanece el activity actual para dar paso al siguiente
        overridePendingTransition(R.transition.fade_in, R.transition.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
