package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;

public interface LicenciaMedicaRepository {

	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica();
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia);
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario);
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto);
	public LicenciaMedicaDto agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto);
	public void eliminaLicenciaMedica(Integer idLicencia);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(String claveUsuario, String fechaInicio, String fechaFin, String idEstatus);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(String claveUsuario ,String nombre,String apellidoPaterno, String apellidoMaterno, 
			String idEstatus,String idUnidad);
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(String idUnidad, String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno);
}
