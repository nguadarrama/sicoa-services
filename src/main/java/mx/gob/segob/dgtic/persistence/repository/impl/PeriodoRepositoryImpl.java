package mx.gob.segob.dgtic.persistence.repository.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.gob.segob.dgtic.business.service.constants.ServiceConstants;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.PeriodoRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class PeriodoRepositoryImpl extends RepositoryBase implements PeriodoRepository{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Override
	public List<PeriodoDto> obtenerListaPeriodos() {
		StringBuilder qry = new StringBuilder();
        qry.append(RepositoryConstants.PRL41);
        qry.append(RepositoryConstants.PRL42);
        
        List<Map<String, Object>> periodos = jdbcTemplate.queryForList(qry.toString());
        List<PeriodoDto> listaPeriodo = new ArrayList<>();
        
        for (Map<String, Object> periodo : periodos) {
    		PeriodoDto periodoDto = new PeriodoDto();
    		periodoDto.setIdPeriodo((Integer)periodo.get(RepositoryConstants.ID_PERIODO));
    		periodoDto.setFechaInicio((Date)periodo.get(RepositoryConstants.FECHA_INICIO));
    		periodoDto.setFechaFin((Date)periodo.get(RepositoryConstants.FECHA_FIN));
    		periodoDto.setDescripcion((String) periodo.get(RepositoryConstants.DESCRIPCION));
    		periodoDto.setActivo((Boolean)periodo.get(RepositoryConstants.ACTIVO));
    		listaPeriodo.add(periodoDto);
    	}
        logger.info("listaPeriodo.: [{}] ",listaPeriodo);
     return listaPeriodo;	
	}


	@Override
	public List<PeriodoDto> obtenerListaPeriodosCatalogo() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ");
        qry.append("from r_periodo  ");
        
        List<Map<String, Object>> periodos = jdbcTemplate.queryForList(qry.toString());
        List<PeriodoDto> listaPeriodo = new ArrayList<>();
        
        for (Map<String, Object> periodo : periodos) {
    		PeriodoDto periodoDto = new PeriodoDto();
    		periodoDto.setIdPeriodo((Integer)periodo.get(RepositoryConstants.ID_PERIODO));
    		periodoDto.setFechaInicio((Date)periodo.get(RepositoryConstants.FECHA_INICIO));
    		periodoDto.setFechaFin((Date)periodo.get(RepositoryConstants.FECHA_FIN));
    		periodoDto.setDescripcion((String) periodo.get(RepositoryConstants.DESCRIPCION));
    		periodoDto.setActivo((Boolean)periodo.get(RepositoryConstants.ACTIVO));
    		listaPeriodo.add(periodoDto);
    	}
        logger.info("listaPeriodo: [{}] ",listaPeriodo);
     return listaPeriodo;	
	}
	
	@Override
	public PeriodoDto buscaPeriodo(Integer idPeriodo) {
		
		StringBuilder qry = new StringBuilder();
		qry.append(RepositoryConstants.L90);
        qry.append(RepositoryConstants.L91);
        qry.append(RepositoryConstants.L92);
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_PERIODO2, idPeriodo);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PeriodoDto>(PeriodoDto.class));
	}

	@Override
	public void modificaPeriodo(PeriodoDto periodoDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append(RepositoryConstants.PRL103);
		qry.append(RepositoryConstants.PRL104);
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_PERIODO2, periodoDto.getIdPeriodo());
		parametros.addValue(RepositoryConstants.FECHA_INICIO2, periodoDto.getFechaInicio());
		parametros.addValue(RepositoryConstants.FECHA_FIN2, periodoDto.getFechaFin());
		parametros.addValue(RepositoryConstants.DESCRIPCION, periodoDto.getDescripcion());
		parametros.addValue(RepositoryConstants.ACTIVO, periodoDto.getActivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public PeriodoDto agregaPeriodo(PeriodoDto periodoDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("insert into r_periodo (fecha_inicio, fecha_fin, descripcion, activo) ");
		qry.append("values (:fechaInicio, :fechaFin, :descripcion, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.FECHA_INICIO2, periodoDto.getFechaInicio());
		parametros.addValue(RepositoryConstants.FECHA_FIN2, periodoDto.getFechaFin());
		parametros.addValue(RepositoryConstants.DESCRIPCION, periodoDto.getDescripcion());
		parametros.addValue(RepositoryConstants.ACTIVO, periodoDto.getActivo());

		try {
			Integer exitoso = nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(exitoso == 1) {
				periodoDto.setMensaje("El nuevo periodo vacacional se ha registrado correctamente. ");
			} else {
				periodoDto.setMensaje("Se ha generado un error con la solicitud, verifique la información. ");
			}
		} catch (Exception e) {
			logger.warn("Warn: {} ",e);
			periodoDto.setMensaje("El periodo con fecha de inicio: "+periodoDto.getFechaInicio()+" ya existe. ");
		}
		logger.info("PeriodoRepoImpl-- method--agregaPeriodo: [{}] ",periodoDto);
		return periodoDto;
	}

	@Override
	public void eliminaPeriodo(Integer idPeriodo) {
		StringBuilder qry = new StringBuilder();
		qry.append("delete from r_periodo where idPeriodo = :idPeriodo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_PERIODO2, idPeriodo);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public PeriodoDto buscaPeriodoPorClaveUsuario(String claveUsuario) {
		Date fecha= new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
		String fechaCadena = formatter.format(fecha);
		StringBuilder qry = new StringBuilder();
		qry.append("select periodo.id_periodo, periodo.descripcion, periodo.fecha_inicio, periodo.fecha_fin, periodo.activo ");
		qry.append("from  m_vacacion_periodo vacacion left join r_periodo periodo on vacacion.id_periodo=periodo.id_periodo ");
		qry.append("left join m_usuario usuario on vacacion.id_usuario=usuario.id_usuario ");
		qry.append("where vacacion.activo=true and vacacion.dias>0 and periodo.activo=true and date_add(periodo.fecha_fin, interval 1 year) >= '"+fechaCadena+"' and periodo.fecha_inicio <= '"+fechaCadena+"' and usuario.cve_m_usuario= :claveUsuario order by periodo.fecha_inicio asc limit 1 ");
		
        logger.info("Consulta: {} ",qry);
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveUsuario", claveUsuario);
        try{
        PeriodoDto periodo= nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PeriodoDto>(PeriodoDto.class));
        logger.info("periodo recuperado: {} ",periodo.getIdPeriodo());
        return periodo;
        }catch(Exception e){
        	logger.warn("Wrn: {} ",e);
        	return new PeriodoDto();
        }
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public int generaPeriodoVacacional(String inicio, String fin, String descripcion, boolean activo) {
		StringBuilder qry = new StringBuilder();
		qry.append("insert into r_periodo (fecha_inicio, fecha_fin, descripcion, activo) ");
		qry.append("values (:fechaInicio, :fechaFin, :descripcion, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.FECHA_INICIO2, inicio);
		parametros.addValue(RepositoryConstants.FECHA_FIN2, fin);
		parametros.addValue(RepositoryConstants.DESCRIPCION, descripcion);
		parametros.addValue(RepositoryConstants.ACTIVO, activo);

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public List<PeriodoDto> topPeriodo() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ");
        qry.append("from r_periodo ");
        qry.append("order by id_periodo desc limit 1");
        
        List<Map<String, Object>> periodos = jdbcTemplate.queryForList(qry.toString());
        List<PeriodoDto> topPeriodo = new ArrayList<>();
        
        for (Map<String, Object> periodo : periodos) {
    		PeriodoDto periodoDto = new PeriodoDto();
    		periodoDto.setIdPeriodo((Integer)periodo.get("id_periodo"));
    		periodoDto.setFechaInicio((Date)periodo.get("fecha_inicio"));
    		periodoDto.setFechaFin((Date)periodo.get("fecha_fin"));
    		periodoDto.setDescripcion((String) periodo.get("descripcion"));
    		periodoDto.setActivo((Boolean)periodo.get("activo"));
    		topPeriodo.add(periodoDto);
    	}
        logger.info("listaPeriodo: [{}] ",topPeriodo);
     return topPeriodo;	
	}

	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	@Override
	public PeriodoDto cambioEstatusPeriodo(PeriodoDto periodo) {
		StringBuilder qry = new StringBuilder();
		qry.append(RepositoryConstants.PRL229);
		qry.append(RepositoryConstants.PRL230);
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ACTIVO, periodo.getActivo());
		parametros.addValue(RepositoryConstants.ID_PERIODO2, periodo.getIdPeriodo());
		try {
			Integer exitoso = nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(exitoso == 1) {
				periodo.setMensaje("Se modifico el periodo correctamente. ");
			} else {
				periodo.setMensaje("Error al modificar el horario, verifique la información. ");
			}
		} catch (Exception e) {
			logger.warn("Error: {} ", e);
		}
		logger.info("periodoRepoImpl--method--cambioEstatusPeriodo: [{}] ",periodo);
		return periodo;
	}

	@Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.READ_COMMITTED)
	@Override
	public void modificaEstatustPeridoEmpleados(Integer id, boolean activo) {
		StringBuilder qry = new StringBuilder();
		qry.append("update m_vacacion_periodo set activo = :activo ");
		qry.append("where id_periodo = :idPeriodo");
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_PERIODO2, id);
		parametros.addValue(RepositoryConstants.ACTIVO, activo);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	@Override
	public boolean existePeriodo(String fechaInicio) {
		boolean existe = false;
		StringBuilder qry = new StringBuilder();
		qry.append(" select id_periodo, descripcion, activo ");
		qry.append(" from r_periodo");
		qry.append(" where fecha_inicio =:fechaInicio");
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue(RepositoryConstants.FECHA_INICIO2, fechaInicio);
		SqlRowSet rs = nameParameterJdbcTemplate.queryForRowSet(qry.toString(), param);
		if(rs.next()) {
			existe = true;
		}
		return existe;
	}
	
	@Override
	public Boolean validaPeriodo(Integer idPeriodo) {
		StringBuilder qry = new StringBuilder();
		Date fecha= new Date();
		
		SimpleDateFormat formatter = new SimpleDateFormat(ServiceConstants.YYYY_MM_DD); 
		String fechaCadena = formatter.format(fecha);
		qry.append("select id_periodo ");
        qry.append("from r_periodo ");
        qry.append("where id_periodo = :idPeriodo and date_add(fecha_fin, interval 1 year) >= '"+fechaCadena+"' and fecha_inicio <= '"+fechaCadena+"' ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idPeriodo", idPeriodo);
        PeriodoDto periodo= new PeriodoDto();
        boolean valida=false;
        try{
        periodo=nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<PeriodoDto>(PeriodoDto.class));
        if(periodo.getIdPeriodo()!=null || !periodo.getIdPeriodo().toString().isEmpty()){
        	valida=true;
        }else{
        	qry = new StringBuilder();
    		qry.append("update r_periodo set activo = :activo ");
    		qry.append("where id_periodo = :idPeriodo");
    		
    		parametros = new MapSqlParameterSource();
    		parametros.addValue(RepositoryConstants.ID_PERIODO2, idPeriodo);
    		parametros.addValue(RepositoryConstants.ACTIVO, false);
    		nameParameterJdbcTemplate.update(qry.toString(), parametros);
        	
        }
        }catch(Exception e){
        	logger.warn("Warn -- {} ", e);
        }
        logger.info("Dato recuperado: {} ",periodo.getIdPeriodo());
        
        return valida;
        
	}
}
