package mx.gob.segob.dgtic.persistence.repository.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.dgtic.business.service.constants.ServiceConstants;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.persistence.repository.PeriodoRepository;
import mx.gob.segob.dgtic.persistence.repository.VacacionPeriodoRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class VacacionPeriodoRepositoryImpl extends RepositoryBase implements VacacionPeriodoRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Autowired PeriodoRepository periodoRepository;
	
	@Override
	public List<VacacionPeriodoDto> obtenerListaVacacionPeriodo() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_vacacion, id_usuario, id_periodo, id_estatus, fecha_inicio, dias, activo ");
        qry.append("FROM m_vacacion_periodo ");
        
        List<Map<String, Object>> vacacionesPeriodos = jdbcTemplate.queryForList(qry.toString());
        List<VacacionPeriodoDto> listaVacacionPeriodo = new ArrayList<>();
        
        for (Map<String, Object> vacacionPeriodo : vacacionesPeriodos) {
        	VacacionPeriodoDto vacacionPeriodoDto = new VacacionPeriodoDto();
        	vacacionPeriodoDto.setIdVacacion((Integer)vacacionPeriodo.get(RepositoryConstants.ID_VACACION));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)vacacionPeriodo.get(RepositoryConstants.ID_USUARIO));
        	vacacionPeriodoDto.setIdUsuario(usuarioDto);
        	PeriodoDto periodoDto = new PeriodoDto();
        	periodoDto.setIdPeriodo((Integer)vacacionPeriodo.get(RepositoryConstants.ID_PERIODO));
        	vacacionPeriodoDto.setIdPeriodo(periodoDto);
        	EstatusDto estatusDto= new EstatusDto();
    		estatusDto.setIdEstatus((Integer)vacacionPeriodo.get(RepositoryConstants.ID_ESTATUS));
    		vacacionPeriodoDto.setIdEstatus(estatusDto);
    		vacacionPeriodoDto.setFechaInicio((Date)vacacionPeriodo.get(RepositoryConstants.FECHA_INICIO));
    		vacacionPeriodoDto.setDias((Integer)vacacionPeriodo.get(RepositoryConstants.DIAS));
    		vacacionPeriodoDto.setActivo((Boolean) vacacionPeriodo.get(RepositoryConstants.ACTIVO));
    		listaVacacionPeriodo.add(vacacionPeriodoDto);
    	}
     return listaVacacionPeriodo;
	}

	@Override
	public VacacionPeriodoDto buscaVacacionPeriodo(Integer idVacacion) {
		logger.info("idVacacion para la busqueda: {} ",idVacacion);
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT vacacion.id_vacacion, vacacion.id_usuario, vacacion.id_periodo, vacacion.id_estatus, vacacion.fecha_inicio, vacacion.dias, vacacion.activo, periodo.id_periodo, periodo.descripcion ");
        qry.append("FROM m_vacacion_periodo vacacion, r_periodo periodo ");
        qry.append("WHERE vacacion.id_periodo=periodo.id_periodo and id_vacacion = :idVacacion");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_VACACION2, idVacacion);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        VacacionPeriodoDto vacacion=new VacacionPeriodoDto();
        PeriodoDto periodo= new PeriodoDto();
        periodo.setIdPeriodo((Integer)informacionConsulta.get(RepositoryConstants.ID_PERIODO));
        periodo.setDescripcion((String)informacionConsulta.get(RepositoryConstants.DESCRIPCION));
        vacacion.setIdPeriodo(periodo);
        vacacion.setIdVacacion((Integer)informacionConsulta.get(RepositoryConstants.ID_VACACION));
        UsuarioDto usuario= new UsuarioDto();
        usuario.setIdUsuario((Integer)informacionConsulta.get(RepositoryConstants.ID_USUARIO));
        EstatusDto estatus = new EstatusDto();
        estatus.setIdEstatus((Integer)informacionConsulta.get(RepositoryConstants.ID_ESTATUS));
        vacacion.setIdUsuario(usuario);
        vacacion.setFechaInicio((Date)informacionConsulta.get(RepositoryConstants.FECHA_INICIO));
        vacacion.setDias((Integer)informacionConsulta.get(RepositoryConstants.DIAS));
        vacacion.setActivo((Boolean)informacionConsulta.get(RepositoryConstants.ACTIVO));
        return vacacion;
	}

	@Override
	public void modificaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_vacacion_periodo SET id_estatus = :idEstatus, fecha_inicio = :fechaInicio, dias = :dias, activo = :activo ");
		qry.append("WHERE id_vacacion = :idVacacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_ESTATUS2, 1);
		parametros.addValue(RepositoryConstants.ID_VACACION2, vacacionPeriodoDto.getIdVacacion());
		parametros.addValue(RepositoryConstants.FECHA_INICIO2, vacacionPeriodoDto.getFechaInicio());
		parametros.addValue(RepositoryConstants.DIAS, vacacionPeriodoDto.getDias());
		parametros.addValue(RepositoryConstants.ACTIVO, vacacionPeriodoDto.getActivo());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaVacacionPeriodo(VacacionPeriodoDto vacacionPeriodoDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_vacacion_periodo (id_usuario, id_periodo, id_estatus, fecha_inicio, dias, activo ) ");
		qry.append("VALUES (:idUsuario, :idPeriodo, :idEstatus, :fechaInicio, :dias, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_USUARIO2, vacacionPeriodoDto.getIdUsuario().getIdUsuario());
		parametros.addValue(RepositoryConstants.ID_PERIODO2, vacacionPeriodoDto.getIdPeriodo().getIdPeriodo());
		parametros.addValue(RepositoryConstants.ID_ESTATUS2, vacacionPeriodoDto.getIdEstatus().getIdEstatus());
		parametros.addValue(RepositoryConstants.FECHA_INICIO2, vacacionPeriodoDto.getFechaInicio());
		parametros.addValue(RepositoryConstants.DIAS, vacacionPeriodoDto.getDias());
		parametros.addValue(RepositoryConstants.ACTIVO, vacacionPeriodoDto.getActivo());
		

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaVacacionPeriodo(Integer idVacacion) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM m_vacacion_periodo WHERE id_vacacion = :idVacacion");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idVacacion", idVacacion);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
	
	@Override
	public VacacionPeriodoDto consultaVacacionPeriodoPorClaveUsuarioYPeriodo(Integer idPeriodo, String claveUsuario){
		Date fecha= new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
		String fechaActualCadena = formatter.format(fecha);
		 Calendar fechaA = Calendar.getInstance();
		 fechaA.setTime(fecha);
		 fechaA.add(Calendar.MONTH, -6);
		 fecha=fechaA.getTime();
		String fechaCadena = formatter.format(fecha);
		
		logger.info("Fecha actual con formato: {} ",fechaCadena);
		StringBuilder qry = new StringBuilder();
		Boolean bandera=periodoRepository.validaPeriodo(idPeriodo);
		VacacionPeriodoDto vacacion= new VacacionPeriodoDto();
		if(bandera==true){
			logger.info("Dato claveUsuario: {} ",claveUsuario+" idPeriodo "+idPeriodo+ "estatus del periodo "+bandera);
			qry.append("select vacacion.id_vacacion, vacacion.dias, usuario.cve_m_usuario ");
	        qry.append("from r_periodo periodo, m_vacacion_periodo vacacion , m_usuario usuario ");
	        qry.append("where vacacion.activo=true and vacacion.id_usuario=usuario.id_usuario and vacacion.dias>0 and periodo.activo=true and usuario.cve_m_usuario = :claveUsuario and usuario.fecha_ingreso <= '"+fechaCadena+"' and date_add(periodo.fecha_fin, interval 1 year) >= '"+fechaActualCadena+"' and periodo.fecha_inicio <= '"+fechaActualCadena+"' and periodo.id_periodo = :idPeriodo and vacacion.id_periodo=periodo.id_periodo order by vacacion.fecha_inicio asc limit 1 ");
	        
	        MapSqlParameterSource parametros = new MapSqlParameterSource();
	        parametros.addValue("claveUsuario", claveUsuario);
	        parametros.addValue("idPeriodo", idPeriodo);
	        
	        Map<String, Object> informacionConsulta=null;
	        
	        try{
	         informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
	         if( informacionConsulta!=null || !informacionConsulta.isEmpty()){
	         	UsuarioDto usuario= new UsuarioDto();
	     		vacacion.setIdVacacion((Integer)informacionConsulta.get("id_vacacion"));
	     		vacacion.setDias((Integer)informacionConsulta.get("dias"));
	     		usuario.setClaveUsuario((String)informacionConsulta.get("cve_m_usuario"));
	     		vacacion.setIdUsuario(usuario);
	         }
	        }catch(Exception e){
	        	
	        }
		}
        return vacacion;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public void generarVacacionPeriodotodos(int idUsuario, int idPeriodo, int estatus, String inicio, int dias, boolean activo) {
		logger.info("valores recibidos vacacionesPeriodoRepoImpl: "
				, " idUsusario: "+idUsuario+" idPeriodo: "+idPeriodo+" estatus: "+estatus+" fechaInicio: "+inicio+" dias: "+dias+" activo: "+activo);
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_vacacion_periodo (id_usuario, id_periodo, id_estatus, fecha_inicio, dias, activo ) ");
		qry.append("VALUES (:idUsuario, :idPeriodo, :idEstatus, '");
		qry.append(inicio);
		qry.append("', :dias, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", idUsuario);
		parametros.addValue("idPeriodo", idPeriodo);
		parametros.addValue("idEstatus", estatus);
		parametros.addValue("dias", dias);
		parametros.addValue("activo", activo);
		nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}


	@Override
	public List<VacacionPeriodoDto> obtenerUsuariosConVacacionesPorFiltros(String claveUsuario, String nombre,
			String apellidoPaterno, String apellidoMaterno, String idUnidad) {
		logger.info("Valores: {} ",claveUsuario+" "+nombre+ " "+apellidoPaterno+" "+apellidoMaterno+" "+idUnidad);
		String query="";
		Date fecha= new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
		String fechaCadena = formatter.format(fecha);
		Calendar fechaA = Calendar.getInstance();
		 fechaA.setTime(fecha);
		 fechaA.add(Calendar.MONTH, -6);
		 fecha=fechaA.getTime();
		String fechaCadena1 = formatter.format(fecha);
		 
		query+="select distinct (usuario.id_usuario) id_usuario ,usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, vacacionPeriodo.dias, periodo.descripcion, periodo.id_periodo, vacacionPeriodo.id_vacacion ";
        query+="from m_usuario usuario, m_vacacion_periodo vacacionPeriodo, r_periodo periodo, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ";
        query+="where periodo.id_periodo=vacacionPeriodo.id_periodo and usuario.id_usuario=vacacionPeriodo.id_usuario and vacacionPeriodo.dias>0 "
        		+ "and date_add(periodo.fecha_fin, interval 1 year) >= '"+fechaCadena+"' and periodo.fecha_inicio <= '"+fechaCadena+"' and usuario.fecha_ingreso <= '"+fechaCadena1+"' and unidad.id_unidad = relacion.id_unidad ";
        if(claveUsuario!=null && !claveUsuario.isEmpty()){
        	query+="and usuario.cve_m_usuario like '%"+claveUsuario+"%' ";
        }
        if(nombre!=null && !nombre.isEmpty()){
        	query+="and usuario.nombre like '%"+nombre+"%' ";
        }
        if(apellidoPaterno!=null && !apellidoPaterno.isEmpty()){
        	query+="and usuario.apellido_paterno like '%"+apellidoPaterno+"%' ";
        }
        if(apellidoMaterno!=null && !apellidoMaterno.isEmpty()){
        	query+="and usuario.apellido_materno like '%"+apellidoMaterno+"%' ";
        }
        if(idUnidad!=null && !idUnidad.isEmpty()){
        	query+="and unidad.id_unidad ='"+idUnidad+"' ";
        }
        query+="order by periodo.fecha_inicio asc ";
       logger.info("Query: {} ",query);
		List<Map<String, Object>> detalleVacaciones = jdbcTemplate.queryForList(query);
		List<VacacionPeriodoDto> listaVacacionPeriodo=new  ArrayList<>();
		for (Map<String, Object> detalleVacacion : detalleVacaciones) {
			VacacionPeriodoDto vacacionPeriodoDto= new VacacionPeriodoDto();
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setIdUsuario((Integer)detalleVacacion.get("id_usuario"));
			usuarioDto.setClaveUsuario((String)detalleVacacion.get("cve_m_usuario"));
			usuarioDto.setNombre((String)detalleVacacion.get("nombre"));
			usuarioDto.setApellidoPaterno((String)detalleVacacion.get("apellido_paterno"));
			usuarioDto.setApellidoMaterno((String)detalleVacacion.get("apellido_materno"));
			vacacionPeriodoDto.setIdUsuario(usuarioDto);
			PeriodoDto periodoDto = new PeriodoDto();
			periodoDto.setIdPeriodo((Integer)detalleVacacion.get("id_periodo"));
			periodoDto.setDescripcion((String)detalleVacacion.get("descripcion"));
			vacacionPeriodoDto.setIdPeriodo(periodoDto);
			vacacionPeriodoDto.setDias((Integer)detalleVacacion.get("dias"));
			vacacionPeriodoDto.setIdVacacion((Integer)detalleVacacion.get("id_vacacion"));
			listaVacacionPeriodo.add(vacacionPeriodoDto);
		}
		return listaVacacionPeriodo;
			
	}

}
