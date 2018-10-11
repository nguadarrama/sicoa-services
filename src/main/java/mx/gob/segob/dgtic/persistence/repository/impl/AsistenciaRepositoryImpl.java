package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Repository
public class AsistenciaRepositoryImpl extends RecursoBase implements AsistenciaRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoMes(String claveEmpleado) {
			
		StringBuilder qry = new StringBuilder();
		
		qry.append("SELECT a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre ");
        qry.append("FROM m_asistencia a, c_tipo_dia t ");
        qry.append("WHERE id_usuario = ? ");
        qry.append("and a.id_tipo_dia = t.id_tipo_dia ");
        qry.append("and year(entrada) = year(current_date) ");
        qry.append("and month(entrada) = month(current_date)");
        qry.append("order by entrada");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveEmpleado", claveEmpleado);
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString(), claveEmpleado);
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        UsuarioDto usuario = usuarioRepository.buscaUsuario(claveEmpleado);
        
        for (Map<String, Object> a : asistencias) {
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin) {
			
		StringBuilder qry = new StringBuilder();
		       
        qry.append("SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("WHERE id_usuario = ? ");
        qry.append("and a.id_tipo_dia = t.id_tipo_dia ");
        qry.append("and entrada >= ? ");
        qry.append("and entrada < ? ");
        qry.append("order by entrada");

        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString(), claveEmpleado, fechaInicio, fechaFin);
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        UsuarioDto usuario = usuarioRepository.buscaUsuario(claveEmpleado);
        
        for (Map<String, Object> a : asistencias) {
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get("estatus"));
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get("id_asistencia"));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		asistencia.setIdEstatus(estatus);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		
		StringBuilder qry = new StringBuilder();
        
        qry.append("SELECT a.id_asistencia, a.entrada, a.id_tipo_dia, t.nombre as nombre_tipo, e.estatus, e.id_estatus, ");
        qry.append("i.id_incidencia, j.id_justificacion, j.justificacion, u.nombre as nombre_usuario, u.apellido_paterno, u.apellido_materno, ");
        qry.append("u.fecha_ingreso, cve_m_usuario, p.descripcion ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join m_usuario u on u.cve_m_usuario = a.id_usuario ");
        qry.append("inner join c_perfil p on p.cve_c_perfil = u.cve_c_perfil ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("left join c_justificacion j on j.id_justificacion = i.id_justificacion ");
        qry.append("WHERE a.id_asistencia = :idAsistencia ");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idAsistencia", id);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        
        PerfilDto perfil = new PerfilDto();
        perfil.setDescripcion((String) informacionConsulta.get("descripcion"));
        
        UsuarioDto usuario = new UsuarioDto();
        usuario.setNombre((String) informacionConsulta.get("nombre_usuario"));
        usuario.setApellidoPaterno((String) informacionConsulta.get("apellido_paterno"));
        usuario.setApellidoMaterno((String) informacionConsulta.get("apellido_materno"));
        usuario.setFechaIngreso((Date) informacionConsulta.get("fecha_ingreso"));
        usuario.setClaveUsuario((String) informacionConsulta.get("cve_m_usuario"));
        usuario.setClavePerfil(perfil);
        
        TipoDiaDto tipoDia = new TipoDiaDto();
        tipoDia.setIdTipoDia((Integer) informacionConsulta.get("id_tipo_dia"));
        tipoDia.setNombre((String) informacionConsulta.get("nombre_tipo"));
        
        EstatusDto estatus = new EstatusDto();
        estatus.setIdEstatus((Integer) informacionConsulta.get("id_estatus") );
    	estatus.setEstatus((String) informacionConsulta.get("estatus"));
    	
    	JustificacionDto justificacion = new JustificacionDto();
    	justificacion.setIdJustificacion((Integer) informacionConsulta.get("id_justificacion"));
    	justificacion.setJustificacion((String) informacionConsulta.get("justificacion"));
    	
    	IncidenciaDto incidencia = new IncidenciaDto();
    	incidencia.setIdIncidencia((Integer) informacionConsulta.get("id_incidencia"));
    	incidencia.setJustificacion(justificacion);
    	
    	AsistenciaDto asistencia = new AsistenciaDto();
        asistencia.setIdAsistencia((Integer) informacionConsulta.get("id_asistencia"));
        asistencia.setEntrada((Timestamp) informacionConsulta.get("entrada"));
        asistencia.setIdTipoDia(tipoDia);
        asistencia.setIdEstatus(estatus);
        asistencia.setIncidencia(incidencia);
        asistencia.setUsuarioDto(usuario);

        return asistencia;
	}

	@Override
	public void creaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_incidencia (id_asistencia, id_tipo_dia, id_archivo, id_estatus, id_responsable, descuento, observaciones, id_justificacion) ");
		qry.append("VALUES (:idAsistencia, :idTipoDia, :idArchivo, :idEstatus, null, null, null, :idJustificacion) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idTipoDia", incidencia.getTipoDia().getIdTipoDia());
		parametros.addValue("idEstatus", incidencia.getEstatus().getIdEstatus());
		parametros.addValue("idJustificacion", incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue("idArchivo", incidencia.getIdArchivo().getIdArchivo());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public boolean existeIncidencia(Integer idAsistencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("SELECT id_incidencia ");
        qry.append("FROM m_incidencia ");
        qry.append("WHERE id_asistencia = ? ");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idAsistencia", idAsistencia);
        
        List<Map<String, Object>> asistencia = jdbcTemplate.queryForList(qry.toString(), idAsistencia);
        
        if (asistencia.size() == 0) {
        	return false;
        } else {
        	return true;
        }
	}

	@Override
	public void editaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("update m_incidencia ");
		qry.append("set id_justificacion = :idJustificacion, id_archivo = :idArchivo  ");
        qry.append("WHERE id_asistencia = :idAsistencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idJustificacion", incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue("idArchivo", incidencia.getIdArchivo().getIdArchivo());
		
		try {
			nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al consultar la incidencia con número de asistencia: " + incidencia.getIdAsistencia().getIdAsistencia());
		}
		
	}
	
	@Override
	public void dictaminaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("update m_incidencia ");
		qry.append("set id_estatus = :idEstatus ");
        qry.append("WHERE id_asistencia = :idAsistencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idEstatus", incidencia.getEstatus().getIdEstatus());
		
		try {
			nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al dictaminar la justificación: " + incidencia.getIdAsistencia().getIdAsistencia() + " " + e.getMessage());
		}
		
	}

	@Override
	public void agregaAsistencia(AsistenciaDto asistenciaDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("insert into m_asistencia (id_usuario, id_tipo_dia, id_estatus, entrada, salida ) ");
		qry.append("values (:idUsuario, :idTipoDia, :idEstatus, :entrada, :salida) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", asistenciaDto.getUsuarioDto().getClaveUsuario());
		parametros.addValue("idTipoDia", asistenciaDto.getIdTipoDia().getIdTipoDia());
		parametros.addValue("idEstatus", asistenciaDto.getIdEstatus().getIdEstatus());
		parametros.addValue("entrada", asistenciaDto.getEntrada());
		parametros.addValue("salida", asistenciaDto.getSalida());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

}
