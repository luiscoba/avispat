package deployits.com.ec.avispa_t.fragment.cupon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ec.deployits.cupones.bean.BeanPromocion;
import com.squareup.picasso.Picasso;
import deployits.com.ec.avispa_t.R;
import deployits.com.ec.avispa_t.utilidad.MetodoPara;
import deployits.com.ec.avispa_t.activity.formulario.Formulario;
import deployits.com.ec.avispa_t.fragment.dialogs.DeseaIniciarSesion;
import deployits.com.ec.avispa_t.fragment.mapa.GoogleMapsFragmento;
import deployits.com.ec.avispa_t.global.Globales;

public class DetalleDeCupon extends Fragment {

    private BeanPromocion beanPromocionBuscado;
    private Button btnGenerarCupon;
    private boolean estadoDelCupon;
    private boolean listaDeCuponesGeneradosBool;
    private Integer posicion;
    private String nombreEmpresa;
    private double latitud, longitud;

    //create a new progress bar for each image to be loaded
    private ProgressBar progressBar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceStatex) {

        View view = inflater.inflate(R.layout.fragment_detalle_de_cupon, container, false);

        Globales.seActivoLogin = false;//no se ha llamado a LoginDialog, nos sirve para activar el splash y de ese modo se refresque el fragment

        MetodoPara.ponerBotonFlotante(view, getActivity());

        TextView txtvTituloCupon, txtvValorTotalCupon, txtvValorDescuentoCupon, txtvValidoHastaCupon, txtvPorcentajeDescuentoCupon, txtvDetalleCupon, txtDireccion;
        String latitudStr, longitudStr;
        ImageButton imageButton;

        if (view != null) {
            progressBar = view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        nombreEmpresa = getArguments().getString("nombreEmpresa");

        latitud = getArguments().getDouble("latitud");
        longitud = getArguments().getDouble("longitud");

        latitudStr = String.valueOf(latitud);
        longitudStr = String.valueOf(longitud);

        String urlStr = "http://maps.google.com/maps/api/staticmap?center="
                + latitudStr + "," + longitudStr
                + "&zoom=15&size=200x200&sensor=false&markers=color:red%7Clabel:%7C"
                + latitudStr + "," + longitudStr;

        imageButton = view.findViewById(R.id.imageButton);
        //load the image url with a callback to a callback method/class
        Picasso.with(getContext())
                .load(urlStr)
                .placeholder(R.transition.progress_animation)//utilizamos la animacion mientras se carga el mapa
            //        .error(R.mipmap.ic_launcher)//aqui va la imagen de error
                .fit() //rellena el imageview con el mapa
                .into(imageButton, new ImageLoadedCallback(progressBar) {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        final Integer idPromocion = getArguments().getInt("idPromocion");
        beanPromocionBuscado = obtenerPromocionPorIdPromocion(idPromocion);

        posicion = getArguments().getInt("posicion");
        //para identificar a los cupones generados cuando hace click en "Cupones generados"
        listaDeCuponesGeneradosBool = false;//getIntent().getBooleanExtra("listaDeCuponesGenerados", false);

        // Recogemos el TextView para mostrar el nombre de la empresa
        final TextView nomEmp = view.findViewById(R.id.txtNombreEmpresa);
        nomEmp.setText(beanPromocionBuscado.getNombreEmpresa());

        ImageView imagen = view.findViewById(R.id.imgPromocion);

        Picasso.with(getContext()).load(beanPromocionBuscado.getUbicacion()).into(imagen);

        txtvTituloCupon = view.findViewById(R.id.txtvTitulo);
        String titulo = beanPromocionBuscado.getTitulo();
        txtvTituloCupon.setText(titulo);

        txtvPorcentajeDescuentoCupon = view.findViewById(R.id.txtPorcentajeDescuento);
        double porcentajeDescuento = beanPromocionBuscado.getPorcentajeDescuento();
        int porcentaDescuentoInt = (int) porcentajeDescuento;
        txtvPorcentajeDescuentoCupon.setText(String.valueOf(porcentaDescuentoInt));

        txtvDetalleCupon = view.findViewById(R.id.txtvDetalle);
        String detalle = beanPromocionBuscado.getDetalle();
        txtvDetalleCupon.setText(detalle);

        txtvValidoHastaCupon = view.findViewById(R.id.txtValidoHasta);
        String validoHasta = beanPromocionBuscado.getFechaFin();
        txtvValidoHastaCupon.setText(validoHasta);

        txtvValorDescuentoCupon = view.findViewById(R.id.txtvValorDescuento);
        Double valorDescuento = beanPromocionBuscado.getValorDescuento();
        txtvValorDescuentoCupon.setText(valorDescuento.toString());

        txtvValorTotalCupon = view.findViewById(R.id.txtvValorTotal);
        Double valorTotal = beanPromocionBuscado.getValorTotal();
        txtvValorTotalCupon.setText(valorTotal.toString());

        txtDireccion = view.findViewById(R.id.txtDireccion);
        String direccion = beanPromocionBuscado.getDireccionSucursal();
        txtDireccion.setText(direccion.toString());

        // programamos el estado de la notificación, es decir generar el cupon de descuento, que es la notificación
        btnGenerarCupon = view.findViewById(R.id.btnGeneraCuponNotificacion);

        metodoVerficaGeneracionDeCupon();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment();
            }
        });

        System.out.println("DetalleDeCupon --> imagebuton");
        btnGenerarCupon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Globales.beanCliente.getCorreo() == null) {//si su correo es null
                    DialogFragment deseaIniciarSesionDialogFragment = new DeseaIniciarSesion();
                    deseaIniciarSesionDialogFragment.setCancelable(false);
                    deseaIniciarSesionDialogFragment.show(getFragmentManager(), "dialogoParaPreguntarSiIniciaSesion");

                } else {

                    Intent irAformulario = new Intent(getContext(), Formulario.class);
                    irAformulario.putExtra("estadoDelCupon", estadoDelCupon);
                    System.out.println("seoOnClick posicion " + posicion);
                    irAformulario.putExtra("posicion", posicion);
                    irAformulario.putExtra("idPromocionIntent", beanPromocionBuscado.getIdPromocion());
                    irAformulario.putExtra("latitud", latitud);
                    irAformulario.putExtra("longitud", longitud);
                    irAformulario.putExtra("nombreEmpresa", nombreEmpresa);

                    startActivity(irAformulario);
                }
            }
        });

        return view;
    }

    public void metodoVerficaGeneracionDeCupon() {

        if (beanPromocionBuscado.getIdEstadoNotificacion().compareTo(Globales.estado_de_notificacion_generado) == 0 || listaDeCuponesGeneradosBool == true) {
            btnGenerarCupon.setText("Ver datos de factura");
            btnGenerarCupon.setTextColor(Color.BLUE);
            btnGenerarCupon.setBackgroundColor(Color.CYAN);
            estadoDelCupon = true;
        } else {
            btnGenerarCupon.setText("Generar Cupón");
            btnGenerarCupon.setTextColor(Color.parseColor("#E6EE9C"));
            btnGenerarCupon.setBackgroundColor(Color.parseColor("#AFAEAD"));
            estadoDelCupon = false;
        }
    }

    private void showFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("nombreEmpresa", nombreEmpresa);
        bundle.putDouble("latitud", latitud);
        bundle.putDouble("longitud", longitud);

        GoogleMapsFragmento googleMapsFragmento = new GoogleMapsFragmento();
        googleMapsFragmento.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenedor, googleMapsFragmento, "googleMapsFragmento");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public BeanPromocion obtenerPromocionPorIdPromocion(Integer idPromocion) {

        BeanPromocion beanPromocion = new BeanPromocion();
        for (BeanPromocion promocion : Globales.lstTodosLosCuponesSistema) {
            if (promocion.getIdPromocion() == idPromocion) {
                beanPromocion = promocion;

                break;
            }
        }

        return beanPromocion;
    }

    @Override
    public void onResume() {
        super.onResume();

        Integer idPromocion = getArguments().getInt("idPromocion");
        beanPromocionBuscado = obtenerPromocionPorIdPromocion(idPromocion);

        metodoVerficaGeneracionDeCupon();
    }
}
