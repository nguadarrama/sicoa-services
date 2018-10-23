package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.UnidadAdministrativaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioUnidadAdministrativaDto;

public interface UnidadAdministrativaService {

	public List<UsuarioUnidadAdministrativaDto> obtenerListaUnidadAdministrativa();
	public void consultaRegistraUsuarioUnidadAdministrativa(UsuarioUnidadAdministrativaDto usuarioUnidadAdministrativaDto);
	public List<UsuarioUnidadAdministrativaDto> consultaResponsable(String claveUsuario);
	public List<UsuarioUnidadAdministrativaDto> obtenerUnidadesAdministrativas();
	public List<UsuarioUnidadAdministrativaDto> consultasoloUnidades();
}
