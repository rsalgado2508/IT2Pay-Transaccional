package co.com.it2ex.it2pay.util.accesodatos.mapper.menu;

/*
 * Copyright (c) 2023.
 * @Plataforma: IT2Pay
 * @Sistema: Portal Administrativo
 * @Modulo: Portal Administrativo Frontend
 * @Copyright IT2Ex
 *
 * @Autor: nromero
 * @FechaCreación: 28/4/2023
 */

import co.com.it2ex.it2pay.util.modelo.menu.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface MenuMapper {


	@Cacheable("consultarMenuPorLoginYRol")
	@Select(" select idFuncionalidad, idMenu, nombre, idPadreMenu, orden, url, imagen, 1 tipo " +
			"from (select item.id_funcionalidad idFuncionalidad, item.id_item_menu idMenu, item.nombre nombre, " +
			"item.id_padre_item_menu idPadreMenu, item.orden orden, fun.url url, item.imagen " +
			"from \"SEG_ITEM_MENU\" item " +
			"left join \"SEG_FUNCIONALIDAD\" fun on item.id_funcionalidad = fun.id_funcionalidad and fun.estado = 'A' " +
			"where item.estado = 'A' and " +
			"item.id_padre_item_menu is null and " +
			"item.id_funcionalidad is null " +
			") as consulta1 "
			+ ""
			+ " union all"
			+ ""
			+ " select distinct submenus.idFuncionalidad, submenus.idMenu, submenus.nombre, submenus.idPadreMenu, submenus.orden, submenus.url, submenus.imagen, 2 tipo "
			+ " from "
			+ " ( "
			+ " select idFuncionalidad, idMenu, nombre, idPadreMenu, orden, url, imagen  " +
			"      from (select item.id_funcionalidad idFuncionalidad, item.id_item_menu idMenu, item.nombre nombre, " +
			"      item.id_padre_item_menu idPadreMenu, item.orden orden, fun.url url, item.imagen " +
			"      from \"SEG_ITEM_MENU\" item " +
			"      inner join \"SEG_FUNCIONALIDAD\" fun on item.id_funcionalidad = fun.id_funcionalidad and fun.estado = 'A' " +
			"      inner join \"SEG_FUNCIONALIDAD_ROL\" funrol on funrol.id_funcionalidad = fun.id_funcionalidad and funrol.estado = 'A' " +
			"      inner join \"SEG_USUARIO_ROL\" usurol on usurol.id_rol = funrol.id_rol " +
			"      inner join \"SEG_USUARIO\" usu on usu.id_usuario = usurol.id_usuario " +
			"      where item.estado = 'A' and funrol.estado = 'A' and usurol.id_usuario = usu.id_usuario and " +
			"      usu.login = #{login} and usurol.id_rol = #{idRol} " +
			") as consulta2 "
			+ "	     "
			+ "	     union all "
			+ "      "
			+ "      select idFuncionalidad, idMenu, nombre, idPadreMenu, orden, url, imagen " +
			"      from (select item.id_funcionalidad idFuncionalidad, item.id_item_menu idMenu, item.nombre nombre, " +
			"      item.id_padre_item_menu idPadreMenu, item.orden orden, fun.url url, item.imagen " +
			"      from \"SEG_ITEM_MENU\" item " +
			"      inner join \"SEG_FUNCIONALIDAD\" fun on item.id_funcionalidad = fun.id_funcionalidad and fun.estado = 'A' " +
			"      inner join \"SEG_FUNCIONALIDAD_ROL\" funrol on funrol.id_funcionalidad = fun.id_funcionalidad " +
			"      inner join \"SEG_USUARIO_ROL\" usurol on usurol.id_rol = funrol.id_rol and usurol.estado = 'A' " +
			"      inner join \"SEG_USUARIO\" usu on usu.id_usuario = usurol.id_usuario " +
			"      where item.estado = 'A' and " +
			"      usu.login = #{login} and usurol.id_rol = #{idRol}  " +
			"" +
			"    ) as consulta3"
			+ "      "
			+ "      union all "
			+ "      "
			+ "      select idFuncionalidad, idMenu, nombre, idPadreMenu, orden, url, imagen " +
			"     from (select item2.id_funcionalidad idFuncionalidad, item2.id_item_menu idMenu, item2.nombre nombre," +
			"     item2.id_padre_item_menu idPadreMenu, item2.orden orden, fun2.url url, item2.imagen" +
			"     from \"SEG_ITEM_MENU\" item2" +
			"     left join \"SEG_FUNCIONALIDAD\" fun2 on item2.id_funcionalidad = fun2.id_funcionalidad" +
			"     inner join \"SEG_ITEM_MENU\" item3 on item3.id_padre_item_menu = item2.id_item_menu" +
			"     inner join \"SEG_FUNCIONALIDAD\" fun on item3.id_funcionalidad = fun.id_funcionalidad and fun.estado = 'S'" +
			"     inner join \"SEG_FUNCIONALIDAD_ROL\" funrol on funrol.id_funcionalidad = fun.id_funcionalidad" +
			"     inner join \"SEG_USUARIO_ROL\" usurol on usurol.id_rol = funrol.id_rol and usurol.estado = 'A'" +
			"     inner join \"SEG_USUARIO\" usu on usu.id_usuario = usurol.id_usuario" +
			"     where item3.estado = 'A' and item3.id_funcionalidad is not null" +
			"     and item2.id_padre_item_menu is not null" +
			"     and usu.login = #{login} and usurol.id_rol = #{idRol} " +
			"     group by item2.id_funcionalidad, item2.id_item_menu, item2.nombre, item2.id_padre_item_menu, item2.orden, fun2.url) as consulta4 "
			+ "      "
			+ " ) submenus"
			+ " order by idPadreMenu nulls first, orden ")
	public List<MenuDTO> consultarMenuPorLoginYRol(@Param("login") String login, @Param("idRol") Long idRol);


}