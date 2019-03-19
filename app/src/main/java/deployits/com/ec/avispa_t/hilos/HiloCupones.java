package deployits.com.ec.avispa_t.hilos;

import deployits.com.ec.avispa_t.utilidad.MetodoPara;

public class HiloCupones extends Thread {
    private boolean completado;
    public HiloCupones(){
        completado = true;
    }

    @Override
    public void run(){
        completado = true;
        MetodoPara.cargarListaCupones();
        completado = false;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
