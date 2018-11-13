package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;

public interface CargaInicialRepository {

	public List<UsuarioDto> recuperarUsuariosCargaInicial();
	public UsuarioDto obtieneUsuarioPorCveMusuario(String cveMusuario);
}
