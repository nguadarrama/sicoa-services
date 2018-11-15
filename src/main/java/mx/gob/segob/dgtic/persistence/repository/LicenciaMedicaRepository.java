package mx.gob.segob.dgtic.persistence.repository;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;

public interface LicenciaMedicaRepository {

	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica();
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia);
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario);
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto);
	public LicenciaMedicaDto agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto);
	public void eliminaLicenciaMedica(Integer idLicencia);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(BusquedaDto busquedaDto);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(BusquedaDto busquedaDto);
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(String idUnidad, String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno);
	public List<LicenciaMedicaDto> buscaLicenciaMedicaReporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
	public List<LicenciaMedicaDto> buscaLicenciaMedicaReporteDirector(AsistenciaBusquedaUtil asistenciaBusquedaUtil);
}
