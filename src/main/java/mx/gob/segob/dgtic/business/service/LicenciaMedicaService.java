package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDtoAux;

public interface LicenciaMedicaService {

	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica();
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia);
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto);
	public LicenciaMedicaDto agregaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto);
	public void eliminaLicenciaMedica(Integer idLicencia);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(String claveUsuario, String fechaInicio, String fechaFin, String idEstatus);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(String claveUsuario ,String nombre,String apellidoPaterno, String apellidoMaterno, 
			String idEstatus,String idUnidad);
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(String idUnidad,String claveUsuario ,String nombre,String apellidoPaterno, String apellidoMaterno);
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario);
}
