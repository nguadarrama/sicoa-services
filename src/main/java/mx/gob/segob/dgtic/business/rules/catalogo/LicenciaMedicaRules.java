package mx.gob.segob.dgtic.business.rules.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.persistence.repository.LicenciaMedicaRepository;

@Component
public class LicenciaMedicaRules {

	@Autowired
	private LicenciaMedicaRepository licenciaMedicaRepository;
	
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica() {
		return licenciaMedicaRepository.obtenerListaLicenciaMedica();
	}
	
	public LicenciaMedicaDto buscaLicenciaMedica (Integer idLicencia){
		return licenciaMedicaRepository.buscaLicenciaMedica(idLicencia);
	}
	
	public void modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto){
		licenciaMedicaRepository.modificaLicenciaMedica(licenciaMedicaDto);
	}
	
	public void agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto){
		licenciaMedicaRepository.agregaLicenciaMedica(licenciaMedicaDto);
	}
	
	public void eliminaLicenciaMedica(Integer idLicencia){
		licenciaMedicaRepository.eliminaLicenciaMedica(idLicencia);
	}
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(String claveUsuario, String fechaInicio, String fechaFin, String idEstatus) {
		return licenciaMedicaRepository.obtenerListaLicenciaMedicaPorFiltros(claveUsuario, fechaInicio, fechaFin, idEstatus);
	}
	
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(String claveUsuario ,String nombre,String apellidoPaterno, String apellidoMaterno, 
			String idEstatus,String idUnidad){
		return licenciaMedicaRepository.obtenerListaLicenciaMedicaEmpleados(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idEstatus, idUnidad);
	}
}
