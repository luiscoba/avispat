package deployits.com.ec.avispa_t.hilos;

import deployits.com.ec.avispa_t.utilidad.MetodoPara;

public class HiloComercios extends Thread {
    private boolean completado;

    public HiloComercios(){
        completado = true;
    }

    @Override
    public void run(){
        completado = true;
        MetodoPara.cargarListaComercios();
        completado = false;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}
