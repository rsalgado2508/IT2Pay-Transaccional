package co.com.it2ex.it2pay.util.negocio.servicios.menu;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 4/5/2023
 */

import co.com.it2ex.it2pay.util.accesodatos.mapper.menu.MenuMapper;
import co.com.it2ex.it2pay.util.modelo.generico.DatosBasicosUsuarioDTO;
import co.com.it2ex.it2pay.util.modelo.generico.ListaDTO;
import co.com.it2ex.it2pay.util.modelo.menu.MenuDTO;
import co.com.it2ex.it2pay.util.modelo.menu.MenuSalidaDTO;
import co.com.it2ex.it2pay.util.negocio.servicios.comun.ServiciosComun;
import co.com.it2ex.it2pay.util.negocio.servicios.log.LoggerAuditoriasComponent;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosError;
import co.com.it2ex.it2pay.util.otros.constantes.config.ConstantesCodigosLogger;
import co.com.it2ex.it2pay.util.otros.constantes.text.CaracteresUtil;
import co.com.it2ex.it2pay.util.otros.exception.IT2PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioMenuImpl implements ServicioMenu {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private ServiciosComun serviciosComun;

    @Autowired
    LoggerAuditoriasComponent loggerAuditoriasComponent;


    @Override
    public ListaDTO consultarMenuPorLoginPorRol(DatosBasicosUsuarioDTO usuarioSesion) throws Exception {

        ListaDTO lista = new ListaDTO();

        try {
            loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.INFO, CaracteresUtil.neutralizeMessage(ConstantesCodigosError.MENSAJE_INVOCACION_SERVICIO_METODO + new IT2PayException().getStackTrace()[0].getMethodName()), this.getClass());

            List<MenuDTO> menusList = null;

            if(usuarioSesion.getIdRol() != null && !usuarioSesion.getIdRol().equals(new Long(0))) {
                menusList = menuMapper.consultarMenuPorLoginYRol(usuarioSesion.getLogin(), usuarioSesion.getIdRol());
            }

            List<MenuSalidaDTO> menusPrincipales = new ArrayList<>();

            Long idPadreMenu = null;
            int posicionArreglo = 0;
            int posicionArregloHijo = 0;

            // Construccion del DTO de menu para el envio de JSON
            if (menusList != null) {

                List<MenuSalidaDTO> auxList;
                for (MenuDTO dto : menusList) {
                    if(dto != null){

                        MenuSalidaDTO menuSalidaDTO = new MenuSalidaDTO(dto);

                        if (menuSalidaDTO != null) {
                            if (menuSalidaDTO.getIdPadreMenu() == null) {
                                menusPrincipales.add(menuSalidaDTO);
                            } else {

                                if (idPadreMenu != null && idPadreMenu.equals(menuSalidaDTO.getIdPadreMenu()) && dto.getIdFuncionalidad() == null) {

                                    auxList = new ArrayList<MenuSalidaDTO>();
                                    if(posicionArreglo < menusPrincipales.size()){
                                        auxList.addAll(menusPrincipales.get(posicionArreglo).getSubMenu());
                                        auxList.add(menuSalidaDTO);
                                        menusPrincipales.get(posicionArreglo).setSubMenu(auxList);
                                    }
                                } else if (idPadreMenu != null && idPadreMenu.equals(menuSalidaDTO.getIdPadreMenu()) && dto.getIdFuncionalidad() != null){
                                    posicionArreglo = 0;
                                    for (MenuSalidaDTO dtoJSON : menusPrincipales) {
                                        if (dtoJSON.getIdMenu() != null
                                                && dtoJSON.getIdMenu().equals(menuSalidaDTO.getIdPadreMenu())) {
                                            idPadreMenu = dtoJSON.getIdMenu();
                                            auxList = new ArrayList<MenuSalidaDTO>();
                                            if(posicionArreglo < menusPrincipales.size()){
                                                auxList.addAll(menusPrincipales.get(posicionArreglo).getSubMenu());
                                                auxList.add(menuSalidaDTO);
                                                menusPrincipales.get(posicionArreglo).setSubMenu(auxList);
                                            }
                                            break;
                                        } else {
                                            posicionArreglo++;
                                        }
                                    }

                                } else {

                                    posicionArreglo = 0;

                                    for (MenuSalidaDTO dtoJSON : menusPrincipales) {
                                        if (dtoJSON.getIdMenu() != null
                                                && dtoJSON.getIdMenu().equals(menuSalidaDTO.getIdPadreMenu())) {

                                            idPadreMenu = dtoJSON.getIdMenu();
                                            auxList = new ArrayList<MenuSalidaDTO>();
                                            if(posicionArreglo < menusPrincipales.size()){
                                                auxList.addAll(menusPrincipales.get(posicionArreglo).getSubMenu());
                                                auxList.add(menuSalidaDTO);
                                                menusPrincipales.get(posicionArreglo).setSubMenu(auxList);
                                            }
                                            break;
                                        } else {


                                            posicionArregloHijo = 0;

                                            for (MenuSalidaDTO dtoJSONHijo : dtoJSON.getSubMenu()) {
                                                if(dtoJSONHijo.getIdMenu() != null
                                                        && dtoJSONHijo.getIdMenu().equals(menuSalidaDTO.getIdPadreMenu())){
                                                    auxList = new ArrayList<MenuSalidaDTO>();
                                                    if(posicionArregloHijo < dtoJSON.getSubMenu().size()){
                                                        auxList.addAll(dtoJSON.getSubMenu().get(posicionArregloHijo).getSubMenu());
                                                        auxList.add(menuSalidaDTO);
                                                        dtoJSON.getSubMenu().get(posicionArregloHijo).setSubMenu(auxList);
                                                    }
                                                    break;
                                                } else {
                                                    posicionArregloHijo++;
                                                }
                                            }
                                            posicionArreglo++;
                                        }
                                    }
                                }

                            }

                        }
                    }
                }
            }


            List<MenuSalidaDTO> listado = new ArrayList<MenuSalidaDTO>();

            for (MenuSalidaDTO dtoJSON : menusPrincipales) {
                if (dtoJSON != null) {

                    if (dtoJSON.getIdPadreMenu() == null && dtoJSON.getSubMenu() != null && dtoJSON.getSubMenu().size() > 0) {

                        listado.add(dtoJSON);
                    }
                }
            }

            if (listado.isEmpty()) {
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(ConstantesCodigosError.CODIGO_DATOS_NO_ENCONTRADOS)
                        .getMensaje());

                loggerAuditoriasComponent.registrarLogger( ConstantesCodigosLogger.ERROR, CaracteresUtil.neutralizeMessage(CaracteresUtil.neutralizeMessage( lista.getMensajeRespuesta() )), this.getClass());

                return lista;
            } else {
                lista.setLista(listado);
                lista.setCodigoRespuesta(ConstantesCodigosError.CODIGO_EXITO);
                lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma(lista.getCodigoRespuesta()).getMensaje());
                return lista;
            }

        } catch (Exception e) {
            lista = new ListaDTO();
            lista.setCodigoRespuesta( ConstantesCodigosError.CODIGO_ERROR_NO_CONTROLADO );
            lista.setMensajeRespuesta(serviciosComun.consultarMensajePorIdioma( lista.getCodigoRespuesta()).getMensaje() );
            loggerAuditoriasComponent.registrarLoggerError( CaracteresUtil.neutralizeMessage( lista.getMensajeRespuesta() ), this.getClass(), e);
            return lista;
        }
    }


}