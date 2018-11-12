package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;

public interface UnidadAdministrativaRepository {

	public List<UsuarioUnidadAdministrativaDto> obtenerListaUnidadAdministrativa();
	public Integer guardaUsuarioUnidadAdministrativa(UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto);
	public UsuarioUnidadAdministrativaDto buscaUsuarioUnidadAdministrativa(String claveUsuario);
	public Integer actualizaUsuarioUnidadAdministrativa(UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto);
	public List<UsuarioUnidadAdministrativaDto> consultaResponsable(String claveUsuario);
	public List<UsuarioUnidadAdministrativaDto> obtenerUnidadesAdministrativas();
	public List<UsuarioUnidadAdministrativaDto> consultasoloUnidades();
	
	public List<UnidadAdministrativaDto> obtenerUnidades();
	
}
