/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class UsuarioPerfilDto {

	/**
    * El id usuario perfil.
    */
   @MapeaColumna(columna = "id_usuario_perfil") private Integer idUsuarioPerfil;
   
   /**
    * la clave del usuario.
    */
   @MapeaColumnasInternas(columnas={"cve_m_usuario", "cve_m_usuario"}) private UsuarioDto claveUsuario;
   
   /**
    * la clave del perfil.
    */
   @MapeaColumnasInternas(columnas={"cve_c_perfil", "cve_c_perfil"}) private PerfilDto clavePerfil;

public Integer getIdUsuarioPerfil() {
	return idUsuarioPerfil;
}

public void setIdUsuarioPerfil(Integer idUsuarioPerfil) {
	this.idUsuarioPerfil = idUsuarioPerfil;
}

public UsuarioDto getClaveUsuario() {
	return claveUsuario;
}

public void setClaveUsuario(UsuarioDto claveUsuario) {
	this.claveUsuario = claveUsuario;
}

public PerfilDto getClavePerfil() {
	return clavePerfil;
}

public void setClavePerfil(PerfilDto clavePerfil) {
	this.clavePerfil = clavePerfil;
}
   
}
