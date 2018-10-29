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
import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDtoAux;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;

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
	public void modificaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto) {
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
		licenciaMedicaRules.modificaLicenciaMedica(licencia);
		
	}

	@Override
	public void agregaLicenciaMedica(LicenciaMedicaDtoAux licenciaMedicaDto) {
		
		LicenciaMedicaDto licencia= new LicenciaMedicaDto();
		Date fechaInicial = new Date();
    	Date fechaFinal = new Date();
    	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    	
    	try {
    		fechaInicial = df.parse(licenciaMedicaDto.getFechaInicioAux());
    		fechaFinal=df.parse(licenciaMedicaDto.getFechaFinAux());
			System.out.println("fechaInicio "+fechaInicial+" fechaInicial "+fechaInicial);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    	licenciaMedicaRules.agregaLicenciaMedica(licencia);
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

	@Override
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(String idUnidad, String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno) {
		return licenciaMedicaRules.obtenerLicenciasPorUnidad(idUnidad, claveUsuario, nombre, apellidoPaterno, apellidoMaterno);
	}
}
