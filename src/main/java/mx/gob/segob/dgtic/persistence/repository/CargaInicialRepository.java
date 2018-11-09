package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;

public interface CargaInicialRepository {

	public List<UsuarioDto> recuperarUsuariosCargaInicial();
	public UsuarioDto obtieneUsuarioPorCve_m_usuario(String cve_m_usuario);
}
