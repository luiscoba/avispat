package deployits.com.ec.avispa_t.fragment.mapa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;

public class GoogleMapsFragmento extends Fragment {
    // Manejador del fragmento
    private SupportMapFragment mapFragment;
    // Objeto de mapa
    private GoogleMap mMap;
    // Objeto de progreso
    private ProgressDialog progreso;
    // Arreglo con los tipos de mapas
    private static final CharSequence[] MAP_TYPE_ITEM;
    // Lista de lugares
    private List <Lugar> lugares;

    private String nombreEmpresa;
    private Double latitud;
    private Double longitud;

    static {
        MAP_TYPE_ITEM = new CharSequence[]{
                "Road Map", "Satellite", "Terrain", "Hybrid"
        };
    }

    /**
     * El constructor inicializa los lugares en la lista,
     * pero esta opcion puede estar en una base de datos o leida del internet
     */
    public GoogleMapsFragmento() {
        lugares = new ArrayList <>();
        //      lugares.add(new Lugar(-0,2097139, -78,4950827, "Casa cultura"));
        //      lugares.add(new Lugar(-0,2030057, -78,491437, "Plaza Foch"));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MetodoPara.permisoDeEjecucion();

        nombreEmpresa = getArguments().getString("nombreEmpresa");
        latitud = getArguments().getDouble("latitud");
        longitud = getArguments().getDouble("longitud");

        System.out.println("-------------------------");
        System.out.println("nombreEmpresa " + nombreEmpresa + " latitud " + latitud + " longitud " + longitud);
        System.out.println("-------------------------");

        //progreso = new ProgressDialog(activity);
        progreso = new ProgressDialog(getActivity());
        progreso.setTitle(getResources().getString(R.string.cargando_mapa));
        progreso.setMessage(getResources().getString(R.string.espere));
        progreso.setCancelable(false);
        progreso.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = null;

        /*
        Si el fragmento no esta creado, se invoca al manejador de fragmentos del mapa, y se
        lanza en el FrameLayout del layout que ya estaba definido para la clase MainActivity, no es
        necesario hacer otro layout adicional para esto.
         */
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                /**
                 * Este metodo se dispara despues que el mapa ya ha sido cargado
                 * @param googleMap
                 */
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    progreso.dismiss();

                    // Add a marker in Sydney and move the camera
                    LatLng sydney = new LatLng(latitud, longitud);
                    // Adicion de una marca
                    Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(nombreEmpresa));
                    // Se mueve la camara del mapa hacia la ubicacion, y se configura el zoom
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18));
                    // Se establece el tipo de visualizacion
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    // mostramos nombres de edificios
                    mMap.setBuildingsEnabled(true);

                    mMap.setIndoorEnabled(true);

                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.getUiSettings().setCompassEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                    mMap.getUiSettings().setAllGesturesEnabled(true);
                    mMap.getUiSettings().setMapToolbarEnabled(true);
                    // con esto movemos los controles del mapa a la mitad
                    mMap.setPadding(0, 0, 0, getView().getHeight() / 5);
                    mMap.getUiSettings().setIndoorLevelPickerEnabled(true);

                    marker.showInfoWindow();// para mostrar el marker ya seleccionado
                }
            });
        }
        rootView = inflater.inflate(R.layout.content_main, container, false);
        // Para lanzar un fragmento dentro de otro
        getChildFragmentManager().beginTransaction().replace(R.id.contenedor, mapFragment).commit();

        MetodoPara.ponerBotonFlotante(rootView, getActivity());

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Vinculacion del apuntador a la actividad padre
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Intent i = new Intent(getActivity(), MainActivity.class);
        //activity = getActivity();
    }

    /**
     * Desvinculacion del apuntador a la actividad padre
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mapFragment = null;
        //activity = null;
    }

    /**
     * Creacion del menu superior, esta usada la modalidad dinamica
     * pero los titulos estan manejados en los recursos
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.add(R.string.cambiar_tipo_mapa)
                .setIcon(R.mipmap.ic_show_layers)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    /**
     * Manejo de la accion del menu superior
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle() == null) {
            getActivity().onBackPressed();

        } else if (item.getTitle().equals(getResources().getString(R.string.cambiar_tipo_mapa))) {
            cambiarTipoMapa();

        }
        return true;
    }

    /**
     * Cambia el tipo de visualizacion del mapa, escogiendo el mismo desde un
     * dialogo de seleccion simple
     */
    private void cambiarTipoMapa() {
        //System.out.println("activity.getComponentName():"+activity.getComponentName());
        final String fDialogTitle = getString(R.string.seleccione_tipo_mapa);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(fDialogTitle);
        int checkItem = mMap.getMapType() - 1;
        builder.setSingleChoiceItems(
                MAP_TYPE_ITEM,
                checkItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0:
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                break;
                            case 1:
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case 2:
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                            case 3:
                                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                break;
                        }
                        dialog.dismiss();
                    }
                }
        );
        AlertDialog fMapTypeDialog = builder.create();
        fMapTypeDialog.setCanceledOnTouchOutside(true);
        fMapTypeDialog.show();
    }

}
