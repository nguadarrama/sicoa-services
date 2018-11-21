package mx.gob.segob.dgtic.business.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.segob.dgtic.business.rules.catalogo.LicenciaMedicaRules;
import mx.gob.segob.dgtic.business.service.LicenciaMedicaService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDtoAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;

@Service
public class LicenciaMedicaServiceImpl extends ServiceBase implements LicenciaMedicaService {

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
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto) {
		LicenciaMedicaDto licencia= new LicenciaMedicaDto();
		EstatusDto estatus= new EstatusDto();
		estatus.setIdEstatus(licenciaMedicaDto.getIdEstatus());
		licencia.setIdEstatus(estatus);
		UsuarioDto usuario= new UsuarioDto(); 
		usuario.setIdUsuario(licenciaMedicaDto.getIdUsuario());
		licencia.setIdUsuario(usuario);
		ArchivoDto archivo= new ArchivoDto();
		archivo.setIdArchivo(licenciaMedicaDto.getIdArchivo());
		licencia.setIdArchivo(archivo);
		licencia.setIdLicencia(licenciaMedicaDto.getIdLicencia());
		return licenciaMedicaRules.modificaLicenciaMedica(licencia);
		
	}

	@Override
	public LicenciaMedicaDto agregaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto) {
		
		LicenciaMedicaDto licencia= new LicenciaMedicaDto();
		Date fechaInicial = new Date();
    	Date fechaFinal = new Date();
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	
    	try {
    		logger.info("fechaInicio: {} ",licenciaMedicaDto.getFechaInicio());
    		fechaInicial = df.parse(licenciaMedicaDto.getFechaInicio());
    		fechaFinal=df.parse(licenciaMedicaDto.getFechaFin());
    		
    		logger.info("fechaInicial: {} ",fechaInicial);
		} catch (ParseException e) {
			logger.error("error: {}",e);
		}
    	licencia.setDias(licenciaMedicaDto.getDias());
    	EstatusDto estatus = new EstatusDto();
    	estatus.setIdEstatus(1);
    	licencia.setIdEstatus(estatus);
    	UsuarioDto usuario= new UsuarioDto();
    	usuario.setIdUsuario(licenciaMedicaDto.getIdUsuario());
    	licencia.setIdUsuario(usuario);
    	licencia.setPadecimiento(licenciaMedicaDto.getPadecimiento());
    	licencia.setFechaFin(fechaFinal);
    	licencia.setFechaInicio(fechaInicial);
    	return licenciaMedicaRules.agregaLicenciaMedica(licencia);
	}

	@Override
	public void eliminaLicenciaMedica(Integer idLicencia) {
		licenciaMedicaRules.eliminaLicenciaMedica(idLicencia);
		
	}

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(BusquedaDto busquedaDto) {
		
		return licenciaMedicaRules.obtenerListaLicenciaMedicaPorFiltros(busquedaDto);
	}

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(BusquedaDto busquedaDto) {
		
		return licenciaMedicaRules.obtenerListaLicenciaMedicaEmpleados(busquedaDto);
	}

	@Override
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(BusquedaDto busquedaDto) {
		return licenciaMedicaRules.obtenerLicenciasPorUnidad(busquedaDto);
	}

	@Override
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario) {
		return licenciaMedicaRules.consultaDiasLicenciaMedica(claveUsuario);
	}
}
