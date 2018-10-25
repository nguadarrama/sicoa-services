package mx.gob.segob.dgtic.comun.sicoa.dto;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;
import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumnasInternas;

public class UsuarioUnidadAdministrativaDto {

	/**
	    * La clave perfil.
	    */
	   @MapeaColumna(columna = "id_usuario_unidad") private Integer idUsuarioUnidad;
	   
	   /**
	    * la clave del usuario.
	    */
	   @MapeaColumnasInternas(columnas={"cve_m_usuario", "cve_m_usuario"}) private UsuarioDto claveUsuario;
	   
	   /**
	    * la clave del perfil.
	    */
	   @MapeaColumnasInternas(columnas={"id_unidad", "id_unidad"}) private UnidadAdministrativaDto idUnidad;

	public Integer getIdUsuarioUnidad() {
		return idUsuarioUnidad;
	}

	public void setIdUsuarioUnidad(Integer idUsuarioUnidad) {
		this.idUsuarioUnidad = idUsuarioUnidad;
	}

	public UsuarioDto getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(UsuarioDto claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public UnidadAdministrativaDto getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(UnidadAdministrativaDto idUnidad) {
		this.idUnidad = idUnidad;
	}
}
