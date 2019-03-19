package deployits.com.ec.avispa_t.hilos;

import deployits.com.ec.avispa_t.utilidad.MetodoPara;

public class HiloCategorias extends Thread {
    private boolean completado;

    public HiloCategorias(){
        completado = true;
    }

    @Override
    public void run(){
        completado = true;
        MetodoPara.cargarListaCategorias();
        completado= false;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public boolean isCompletado() {
        return completado;
    }

}
