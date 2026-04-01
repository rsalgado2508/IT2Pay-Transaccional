package co.com.it2ex.it2pay.util.otros.constantes.enums;

public enum EstadosAuditoriaEnum {
    INFO("I"),ERROR("E"),TRACE("T"),DEBUG("D"),WARN("W");

    private String codigo;
    EstadosAuditoriaEnum(String codigo){
        this.codigo=codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
