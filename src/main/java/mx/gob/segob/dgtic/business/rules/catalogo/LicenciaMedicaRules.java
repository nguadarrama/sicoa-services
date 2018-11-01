package mx.gob.segob.dgtic.business.rules.catalogo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.LicenciaMedicaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Component
public class LicenciaMedicaRules {

	@Autowired
	private LicenciaMedicaRepository licenciaMedicaRepository;
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired AsistenciaRepository asistenciaRepository;
	
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica() {
		return licenciaMedicaRepository.obtenerListaLicenciaMedica();
	}
	
	public LicenciaMedicaDto buscaLicenciaMedica (Integer idLicencia){
		return licenciaMedicaRepository.buscaLicenciaMedica(idLicencia);
	}
	
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto){
		LicenciaMedicaDto licenciaAux= new LicenciaMedicaDto();
		licenciaAux=buscaLicenciaMedica(licenciaMedicaDto.getIdLicencia());
		
		if(licenciaMedicaDto.getIdEstatus().getIdEstatus()==2){
			licenciaMedicaDto.setFechaInicio(licenciaAux.getFechaInicio());
			licenciaMedicaDto.setFechaFin(licenciaAux.getFechaFin());
			Date fechaInicio=licenciaMedicaDto.getFechaInicio();
			Date fechaFin=licenciaMedicaDto.getFechaFin();
			Calendar c1 = Calendar.getInstance();
			System.out.println("Fechas fecha inicial "+licenciaMedicaDto.getFechaInicio()+" fecha final "+licenciaMedicaDto.getFechaFin());
		    c1.setTime(fechaInicio);
		    Calendar c2 = Calendar.getInstance();
		    c2.setTime(fechaFin);
		    List<Date> listaFechas = new ArrayList<Date>();
		    while (!c1.after(c2)) {
		        listaFechas.add(c1.getTime());
		        c1.add(Calendar.DAY_OF_MONTH, 1);
		    }
		    EstatusDto estatusDto = new EstatusDto();
		    estatusDto.setIdEstatus(2);
		    TipoDiaDto tipoDiaDto = new TipoDiaDto();
		    tipoDiaDto.setIdTipoDia(6);
		    UsuarioDto usuarioDto= new UsuarioDto();
		    usuarioDto=usuarioRepository.buscaUsuarioPorId(licenciaMedicaDto.getIdUsuario().getIdUsuario());
		    for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
		        Date date = it.next();
		        AsistenciaDto asistenciaDto = new AsistenciaDto(); 
		        asistenciaDto.setEntrada(new Timestamp(date.getTime()));
		        asistenciaDto.setSalida(new Timestamp(date.getTime()));
		        System.out.println("detalleVacacionDto.getIdUsuario().getIdUsuario() "+licenciaMedicaDto.getIdUsuario().getIdUsuario());
		        asistenciaDto.setUsuarioDto(usuarioDto);
		        asistenciaDto.setIdEstatus(estatusDto);
		        asistenciaDto.setIdTipoDia(tipoDiaDto);
		        asistenciaRepository.agregaAsistencia(asistenciaDto);
		        System.out.println("Fecha "+date+" registro insertado");  
		       licenciaAux=licenciaMedicaRepository.modificaLicenciaMedica(licenciaMedicaDto);
		    }
			
		}else if(licenciaMedicaDto.getIdEstatus().getIdEstatus()==3){
			licenciaAux=licenciaMedicaRepository.modificaLicenciaMedica(licenciaMedicaDto);
		}
		return licenciaAux;
	}
	
	public LicenciaMedicaDto agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto){
		return licenciaMedicaRepository.agregaLicenciaMedica(licenciaMedicaDto);
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
	
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(String idUnidad,String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno) {
		return licenciaMedicaRepository.obtenerLicenciasPorUnidad(idUnidad, claveUsuario, nombre, apellidoPaterno, apellidoMaterno);
	}
	
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario){
		return licenciaMedicaRepository.consultaDiasLicenciaMedica(claveUsuario);
	}
}
