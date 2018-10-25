package mx.gob.segob.dgtic.business.service;

import java.util.List;

import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;

public interface LicenciaMedicaService {

	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica();
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia);
	public void modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto);
	public void agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto);
	public void eliminaLicenciaMedica(Integer idLicencia);
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(String claveUsuario, String fechaInicio, String fechaFin, String idEstatus);
}
