package deployits.com.ec.avispa_t.fragment.mapa;


/**
 * Clase ejemplo de ubicacion que tiene la localizacion geografica y una descripcion
 */
class Lugar {
    private Double latitud;
    private Double longitud;
    private String descripcion;

    public Lugar() {
    }

    public Lugar(Double latitud, Double longitud, String descripcion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}