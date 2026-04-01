package co.com.it2ex.it2pay.seguridad.negocio.servicios.usuarios;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 2/5/2023
 */

import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.DesbloqueoMapper;
import co.com.it2ex.it2pay.seguridad.accesodatos.mapper.usuarios.UsuarioMapper;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.DesbloqueoDTO;
import co.com.it2ex.it2pay.seguridad.modelo.usuarios.UsuarioDTO;
import co.com.it2ex.it2pay.util.accesodatos.mapper.configuracion.GenConfiguracionMapper;
import co.com.it2ex.it2pay.util.accesodatos.mapper.intento.IntentoUsuarioMapper;
import co.com.it2ex.it2pay.util.modelo.generico.ParametroConsultaDTO;
import co.com.it2ex.it2pay.util.modelo.generico.RespuestaBaseDTO;
import co.com.it2ex.it2pay.util.modelo.intento.IntentoUsuarioDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.negocio.servicios.parametros.ServicioParametros;
import co.com.it2ex.it2pay.util.otros.components.FechaUtil;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.enums.ComponenteFechaEnum;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiciosDesbloqueoImpl implements ServiciosDesbloqueo {

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;

    @Autowired
    DesbloqueoMapper desbloqueoMapper;

    @Autowired
    ServiciosComun serviciosComun;

    @Autowired
    ServicioParametros serviciosParametros;

    @Autowired
    IntentoUsuarioMapper intentoUsuarioMapper;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    GenConfiguracionMapper configuracionMapper;

    /**
     * Valida si el proceso tiene un parametro asociado para realizar un
     * desbloqueo auto, ademas valida que el tiempo de bloqueo vs el tiempo del paramatro
     * ya se haya cumplido.
     * @param idFuncionalidad
     * @param usuarioDto
     * @param idPersona
     * @return
     */
    public RespuestaBaseDTO desbloqueoAutomatico(Long idFuncionalidad, UsuarioDTO usuarioDto, Long idPersona)throws  Exception{

        RespuestaBaseDTO respuesta = new RespuestaBaseDTO();

        try{

            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            DesbloqueoDTO dto=new DesbloqueoDTO();
            DesbloqueoDTO bloqueoEspecifico;
            List<DesbloqueoDTO> bloqueo;

            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(ConstantesCodigosError.CODIGO_EXITO).getMensaje());
            if(usuarioDto.getIdUsuario() != null
                    && idFuncionalidad!=null){

                //Consulta el id_tipo_bloqueo que pudo generarse
                bloqueo = desbloqueoMapper.consultarTipoBloqueoXFuncionalidadUsuario(idFuncionalidad, usuarioDto.getIdUsuario());

            }else{
                bloqueo = desbloqueoMapper.consultarTipoBloqueoXFuncionalidadPersona(idFuncionalidad, idPersona);
            }
            dto.setEstadoBloqueo("I");
            dto.setUsuarioModificacion("SISTEMA");
            dto.setUsuarioLiberacion("SISTEMA");
            for (DesbloqueoDTO iterador : bloqueo) {
                dto.setIdBloqueo(iterador.getIdBloqueo());
                dto.setIdUsuario(iterador.getIdUsuario());
                bloqueoEspecifico= desbloqueoMapper.consultarBloqueoXId(dto.getIdBloqueo());
                ParametroConsultaDTO tiempoAutomatico = serviciosParametros.obtenerValorParametroPorId(bloqueoEspecifico.getIdParametro());

                if(validarCumplioTiempo(tiempoAutomatico, bloqueoEspecifico.getFechaBloqueo())){
                    respuesta= actualizarDesbloqueoSinDI(dto, bloqueoEspecifico,usuarioDto);

                }else{

                }
            }

        }catch(Exception e){
            respuesta = new RespuestaBaseDTO();
            respuesta.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( respuesta.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);


        }

        return respuesta;
    }

    /**
     * Metodo utilizado para actualizar los desbloqueos cuando
     * NO tiene doble intervencion.
     * @param dto enviado desde la pagina
     * @param bloqueoEspecifico :registro especifico que se desea liberar este se vuelve
     * a obtener para validar que lo enviado desde la pagina corresponda con lo que esta en la base de datos
     * @param usuarioSesion: login sesion que ejecuta la accion
     * @return
     * @throws Exception
     */
    private RespuestaBaseDTO actualizarDesbloqueoSinDI(DesbloqueoDTO dto,DesbloqueoDTO bloqueoEspecifico, UsuarioDTO usuarioSesion ) throws Exception{

        RespuestaBaseDTO respuesta = new RespuestaBaseDTO();

        try{
            if(!bloqueoEspecifico.getEstadoBloqueo().equals("I")){
                if(dto.getUsuarioModificacion()==null){
                    dto.setUsuarioModificacion(dto.getUsuarioLiberacion());
                }

                desbloqueoMapper.actualizarDesbloqueoSinDI(dto);
                if(desbloquear(dto, bloqueoEspecifico, usuarioSesion.getLogin())){
                    respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                    respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(ConstantesCodigosError.CODIGO_MODIFICACION_EXITOSA).getMensaje());

                }else{
                    respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_ACTUALIZA_DESBLOQUEO);
                    respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
                }
            }else{
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_ACTUALIZA_DESBLOQUEO);
                respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            }

        }catch(Exception e){
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_ACTUALIZA_DESBLOQUEO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

        }
        return respuesta;
    }

    /**
     * Realiza el llamado al calculo y realiza la validacion
     * para calcular el tiempo de desbloqueo.
     * @param tiempoAutomatico (parametro)
     * @param fechaBloqueo fecha en la que se realizo el bloqueo - fecha inicial
     * @return
     */
    private boolean validarCumplioTiempo(ParametroConsultaDTO tiempoAutomatico, String fechaBloqueo){
        //Si el tiempo no esta parametrizado se espera a que sea por desbloqueo manual
        String fechaFinal = configuracionMapper.consultarFechaSysdate();
        if(tiempoAutomatico.getValor() != null){
            long minutos = FechaUtil.getInstance().obtenerMinutosTranscurridos(fechaBloqueo, ComponenteFechaEnum.MINUTOS.getCodigo(),"dd-MM-yyyy HH:mm:ss", fechaFinal);
            if(minutos >= Long.parseLong(tiempoAutomatico.getValor())){
                return true;
            }
        }
        return false;
    }
    /**
     * Desbloque tablas de seg_persona_intento, seg_usuario_intento y en caso de que
     * el proceso lo requiera desbloquea el usuairo
     * @param dto
     * @param bloqueoEspecifico
     * @param usuarioModifica usuario que se encuentra realizando el desbloqueo debe ser: sistema (cuando es automatico), un funcionario o un Admin PJ.
     * @return
     * @throws Exception
     */
    @Transactional(readOnly=false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public boolean desbloquear(DesbloqueoDTO dto,DesbloqueoDTO bloqueoEspecifico, String usuarioModifica) throws Exception{
        RespuestaBaseDTO respuesta = new RespuestaBaseDTO();
        try{
            String tipoIntentoBloqueo=desbloqueoMapper.consultarTipoBloqueoXId(dto.getIdBloqueo());
            //Se elimina intento o de persona o de usuario pero no ambos
            if(bloqueoEspecifico.getIdPersona()!= null){
                //Elimina los intetnos de la tabla de intentos persona en caso de existir
                /*IntentoPersonaDTO intentoPersonaDTO=new IntentoPersonaDTO();
                intentoPersonaDTO.setIdUsuario(Long.parseLong(bloqueoEspecifico.getIdPersona()));
                intentoPersonaDTO.setTipoIntento(tipoIntentoBloqueo);
                intentoPersonaMapper.eliminarIntentoPersonaXID(intentoPersonaDTO);*/
            }else{
                //Elimina los intentos de la tabla de intentos usuario en caso de existir
                IntentoUsuarioDTO intentoUsuarioDTO = new IntentoUsuarioDTO();
                intentoUsuarioDTO.setIdUsuario(Long.parseLong(bloqueoEspecifico.getIdUsuario()));
                intentoUsuarioDTO.setTipoIntento(tipoIntentoBloqueo);
                intentoUsuarioMapper.eliminarIntentoXID(intentoUsuarioDTO);

                if(bloqueoEspecifico.getBloqueoDestino().equals("U")){
                    //Se desbloquea el usuario si el tipo de bloqueo lo requiere
                    UsuarioDTO usuarioDTO =new UsuarioDTO();
                    usuarioDTO.setEstado("A");
                    usuarioDTO.setLogin(usuarioModifica);
                    usuarioDTO.setIdUsuario(Long.parseLong(bloqueoEspecifico.getIdUsuario()));
                    usuarioMapper.actualizarUsuarioEstado(usuarioDTO);
                }
                respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
            }

            return true;
        }catch(Exception e){
            respuesta.setCodigoRespuesta(ConstantesCodigosError.CODIGO_ERROR_ACTUALIZA_DESBLOQUEO);
            respuesta.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(respuesta.getCodigoRespuesta()).getMensaje());
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( respuesta.getMensajeRespuesta() ), this.getClass(), e);

            return false;
        }

    }


}