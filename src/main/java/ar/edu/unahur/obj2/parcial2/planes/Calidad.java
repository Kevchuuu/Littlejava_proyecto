package ar.edu.unahur.obj2.parcial2.planes;

public enum Calidad { 
    ESTANDAR(Configuracion.BITRATE_ESTANDAR),
    ALTA(Configuracion.BITRATE_ALTA),
    LOSSLESS(Configuracion.BITRATE_LOSSLESS);

    private final Integer bitrate;

    Calidad(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public Calidad mayor(Calidad otra) {
        if (this.bitrate >= otra.bitrate) {
            return this;
        }
        return otra;
    }
}
