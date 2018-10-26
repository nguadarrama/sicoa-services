package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.NivelOrganizacionalDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;

public interface NivelOrganizacionalService {
	
	public NivelOrganizacionalDto creaNivel(NivelOrganizacionalDto nivelDto);
	public List<NivelOrganizacionalDto> obtenerListaNiveles();
	public NivelOrganizacionalDto buscaNivel(Integer idNivel);
	public NivelOrganizacionalDto modificaNivel(NivelOrganizacionalDto nivelDto);
	public NivelOrganizacionalDto eliminaNivel(Integer idNivel);
	public List<UsuarioDto> nivelesEmpleado();
	public Integer actualizaHorarioEmpleado(Integer idHorario, String nivel);
	public Horario buscaHorario(Integer idHorario);

}
