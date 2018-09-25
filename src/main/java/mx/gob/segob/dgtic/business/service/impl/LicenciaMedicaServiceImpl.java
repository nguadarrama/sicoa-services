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
}
