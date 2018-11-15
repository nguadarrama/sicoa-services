package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDtoAux;

public interface LicenciaMedicaService {

	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica();
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia);
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto);
	public LicenciaMedicaDto agregaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto);
	public void eliminaLicenciaMedica(Integer idLicencia);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(BusquedaDto busquedaDto);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(BusquedaDto busquedaDto);
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(BusquedaDto busquedaDto);
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario);
}
