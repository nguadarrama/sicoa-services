package mx.gob.segob.dgtic.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.LicenciaMedicaRules;
import mx.gob.segob.dgtic.business.service.LicenciaMedicaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;

@Service
public class LicenciaMedicaServiceImpl implements LicenciaMedicaService {

	@Autowired
	private LicenciaMedicaRules licenciaMedicaRules;

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica() {
		return licenciaMedicaRules.obtenerListaLicenciaMedica();
	}

	@Override
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia) {
		
		return licenciaMedicaRules.buscaLicenciaMedica(idLicencia);
	}

	@Override
	public void modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto) {
		licenciaMedicaRules.modificaLicenciaMedica(licenciaMedicaDto);
		
	}

	@Override
	public void agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto) {
		licenciaMedicaRules.agregaLicenciaMedica(licenciaMedicaDto);
		
	}

	@Override
	public void eliminaLicenciaMedica(Integer idLicencia) {
		licenciaMedicaRules.eliminaLicenciaMedica(idLicencia);
		
	}

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(String claveUsuario, String fechaInicio,
			String fechaFin, String idEstatus) {
		
		return licenciaMedicaRules.obtenerListaLicenciaMedicaPorFiltros(claveUsuario, fechaInicio, fechaFin, idEstatus);
	}

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno, String idEstatus, String idUnidad) {
		
		return licenciaMedicaRules.obtenerListaLicenciaMedicaEmpleados(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idEstatus, idUnidad);
	}
}
