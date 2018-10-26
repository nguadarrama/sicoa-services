package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.NivelOrganizacionalDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;

public interface NivelOrganizacionalRepository {

	public List<NivelOrganizacionalDto> obtenerListaNiveles();
	public NivelOrganizacionalDto buscaNivel(Integer idNivel);
	public NivelOrganizacionalDto modificaNivel(NivelOrganizacionalDto nivelDto);
	public NivelOrganizacionalDto agregaNivel(NivelOrganizacionalDto nivelDto);
	public NivelOrganizacionalDto eliminaNivel(Integer idNivel);
	public boolean existeNivel(Integer idHorario);
	public List<UsuarioDto> nivelesEmpleado();
	public List<UsuarioDto> empleadosNivel (String nivel);
	public Integer actualizaHorarioEmpleado(Integer idHorario, String nivel);
	public Horario buscaHorario(Integer idHorario);
}
