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
import mx.gob.segob.dgtic.comun.sicoa.dto.BusquedaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;
import mx.gob.segob.dgtic.persistence.repository.LicenciaMedicaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;

@Component
public class LicenciaMedicaRules extends RepositoryBase{

	@Autowired
	private LicenciaMedicaRepository licenciaMedicaRepository;
	
	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	@Autowired 
	private AsistenciaRepository asistenciaRepository;
	
	@Autowired 
	private DiaFestivoRepository diaFestivoRepository;
	
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica() {
		return licenciaMedicaRepository.obtenerListaLicenciaMedica();
	}
	
	public LicenciaMedicaDto buscaLicenciaMedica (Integer idLicencia){
		return licenciaMedicaRepository.buscaLicenciaMedica(idLicencia);
	}
	
	public LicenciaMedicaDto modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto){
		LicenciaMedicaDto licenciaAux;
		licenciaAux=buscaLicenciaMedica(licenciaMedicaDto.getIdLicencia());
		if(licenciaMedicaDto.getIdEstatus().getIdEstatus()==1){
			licenciaAux=licenciaMedicaRepository.modificaLicenciaMedica(licenciaMedicaDto);
		}
		
		if(licenciaMedicaDto.getIdEstatus().getIdEstatus()==2){
			List<DiaFestivoDto> listaDias;
			listaDias=diaFestivoRepository.obtenerDiasFestivosActivos();
			licenciaMedicaDto.setFechaInicio(licenciaAux.getFechaInicio());
			licenciaMedicaDto.setFechaFin(licenciaAux.getFechaFin());
			Date fechaInicio=licenciaMedicaDto.getFechaInicio();
			Date fechaFin=licenciaMedicaDto.getFechaFin();
			Calendar c1 = Calendar.getInstance();
			logger.info("Fechas fecha inicial: {} ",licenciaMedicaDto.getFechaInicio()+" fecha final "+licenciaMedicaDto.getFechaFin());
		    c1.setTime(fechaInicio);
		    Calendar c2 = Calendar.getInstance();
		    c2.setTime(fechaFin);
		    List<Date> listaFechas = new ArrayList<>();
		    while (!c1.after(c2)) {
		    	if((c1.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) || (c1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)){
		        	logger.info("Datos dentro de la comparación ");
		        	
		        	c1.add(Calendar.DAY_OF_MONTH, 1);
		        }else{
		        	listaFechas.add(c1.getTime());
		        	c1.add(Calendar.DAY_OF_MONTH, 1);
		        }
		    }
		    
		    for(DiaFestivoDto diaFestivo : listaDias){
	        	for(Date lista: listaFechas){
		        	if(diaFestivo.getFecha().equals(lista)){
		        		listaFechas.remove(lista);
		        	}
	        	}
	        	
	        }
		    
		    EstatusDto estatusDto = new EstatusDto();
		    estatusDto.setIdEstatus(2);
		    TipoDiaDto tipoDiaDto = new TipoDiaDto();
		    tipoDiaDto.setIdTipoDia(6);
		    UsuarioDto usuarioDto;
		    usuarioDto=usuarioRepository.buscaUsuarioPorId(licenciaMedicaDto.getIdUsuario().getIdUsuario());
		    for (Iterator<Date> it = listaFechas.iterator(); it.hasNext();) {
		        Date date = it.next();
		        AsistenciaDto asistenciaDto = new AsistenciaDto(); 
		        asistenciaDto.setEntrada(new Timestamp(date.getTime()));
		        asistenciaDto.setSalida(new Timestamp(date.getTime()));
		        logger.info("detalleVacacionDto.getIdUsuario().getIdUsuario(): {} ",licenciaMedicaDto.getIdUsuario().getIdUsuario());
		        asistenciaDto.setUsuarioDto(usuarioDto);
		        asistenciaDto.setIdEstatus(estatusDto);
		        asistenciaDto.setIdTipoDia(tipoDiaDto);
		        asistenciaRepository.agregaAsistencia(asistenciaDto);
		        logger.info("Fecha: {} ",date);  
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
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaPorFiltros(BusquedaDto busquedaDto) {
		System.out.println("feh "+busquedaDto.getFechaFin());
		return licenciaMedicaRepository.obtenerListaLicenciaMedicaPorFiltros(busquedaDto);
		
	}
	
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedicaEmpleados(BusquedaDto busquedaDto){
		return licenciaMedicaRepository.obtenerListaLicenciaMedicaEmpleados(busquedaDto);
	}
	
	public List<LicenciaMedicaDto> obtenerLicenciasPorUnidad(BusquedaDto busquedaDto) {
		return licenciaMedicaRepository.obtenerLicenciasPorUnidad(busquedaDto.getIdUnidad(), busquedaDto.getClaveUsuario(),busquedaDto.getNombre(), 
				busquedaDto.getApellidoPaterno(), busquedaDto.getApellidoPaterno());
	}
	
	public LicenciaMedicaDto consultaDiasLicenciaMedica(String claveUsuario){
		return licenciaMedicaRepository.consultaDiasLicenciaMedica(claveUsuario);
	}
}
