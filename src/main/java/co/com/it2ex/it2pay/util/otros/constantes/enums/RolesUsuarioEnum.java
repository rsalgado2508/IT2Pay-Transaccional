package co.com.it2ex.it2pay.util.otros.constantes.enums;

public enum RolesUsuarioEnum {
    COLOCADOR( 2L );

    private Long codigo;
    RolesUsuarioEnum(Long codigo){
        this.codigo=codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

}
