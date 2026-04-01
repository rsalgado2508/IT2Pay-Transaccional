package co.com.it2ex.it2pay.util.otros.constantes.enums;

public enum EstadosFuncionariosEnum {
    REGISTRADO("R"),INACTIVO("I"),BLOQUEADO("B"),ACTIVO("A"),RETIRADO("T");

    private String codigo;
    EstadosFuncionariosEnum(String codigo){
        this.codigo=codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
