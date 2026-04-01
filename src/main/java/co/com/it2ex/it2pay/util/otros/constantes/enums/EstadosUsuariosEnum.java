package co.com.it2ex.it2pay.util.otros.constantes.enums;

public enum EstadosUsuariosEnum {
    REGISTRADO("R"),INACTIVO("I"),BLOQUEADO("B"),ACTIVO("A");

    private String codigo;
    EstadosUsuariosEnum(String codigo){
        this.codigo=codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
