package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
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
		       
        qry.append("SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ");
        qry.append("i.id_estatus, i.descuento ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("WHERE id_usuario = " + claveEmpleado);
        qry.append(" and a.id_tipo_dia = t.id_tipo_dia");
        
        if (fechaInicio != null && fechaFin != null) {
        	qry.append(" and entrada >= '" + fechaInicio + "'");
        	qry.append(" and entrada < '" + fechaFin + "'");
        }
        
        qry.append(" order by entrada");

        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        UsuarioDto usuario = usuarioRepository.buscaUsuario(claveEmpleado);
        
        for (Map<String, Object> a : asistencias) {
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get("estatus"));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get("descuento") != null) {
        		incidencia.setDescuento((Boolean) a.get("descuento"));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get("id_asistencia"));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa, Integer idUnidadCoordinador) {
			
		StringBuilder qry = new StringBuilder();
	       
        qry.append("SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, e.id_estatus, ua.nombre as nombre_unidad, ");
        qry.append("i.id_estatus, i.descuento ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("inner join m_usuario u on u.cve_m_usuario = a.id_usuario ");
        qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
        qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
        qry.append("where uua.id_unidad = " + idUnidadCoordinador);
        
        if (fechaInicial != null && fechaFinal != null) {
        	qry.append(" and entrada >= '" + fechaInicial + "'");
        	qry.append(" and entrada < '" + fechaFinal  + "'");
        }
        
        if (!cve_m_usuario.isEmpty()) {
        	qry.append(" and a.id_usuario = " + cve_m_usuario);
        }
        
        if (!nombre.isEmpty()) {
        	qry.append(" and u.nombre like '%" + nombre + "%' ");
        }
        
        if (!paterno.isEmpty()) {
        	qry.append(" and u.apellido_paterno like '%" + paterno + "%' ");
        }
        
        if (!materno.isEmpty()) {
        	qry.append(" and u.apellido_materno like '%" + materno + "%' ");
        }
        
        if (!unidadAdministrativa.isEmpty()) {
        	qry.append(" and ua.nombre like '%" + unidadAdministrativa + "%' ");
        }
        
        if (!nivel.isEmpty()) {
        	qry.append(" and u.nivel like '%" + nivel + "%' ");
        }
        
        if (!tipo.isEmpty()) {
        	qry.append(" and t.nombre like '%" + tipo + "%' ");
        }
        
        if (!estado.isEmpty()) {
        	qry.append(" and e.estatus like '%" + estado + "%' ");
        }
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        for (Map<String, Object> a : asistencias) {
        	UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get("id_usuario"));
        	
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get("estatus"));
        	estatus.setIdEstatus((Integer) a.get("id_estatus"));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get("descuento") != null) {
        		incidencia.setDescuento((Boolean) a.get("descuento"));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get("id_asistencia"));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(String cve_m_usuario, String nombre, String paterno,
			String materno, String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal,
			String unidadAdministrativa) {
			
		StringBuilder qry = new StringBuilder();
		Boolean usuarioFueAgregadoAQuery = false;
	       
        qry.append("SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ");
        qry.append("i.id_estatus, i.descuento ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("inner join m_usuario u on u.cve_m_usuario = a.id_usuario ");
        qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
        qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
        
        if (fechaInicial != null && fechaFinal != null) {
        	qry.append("where entrada >= '" + fechaInicial + "'");
        	qry.append(" and entrada < '" + fechaFinal  + "'");
        } else {
        	if (!cve_m_usuario.isEmpty()) {
            	qry.append("where a.id_usuario = " + cve_m_usuario);
            	usuarioFueAgregadoAQuery = true;
            }
        }
        
        if (!usuarioFueAgregadoAQuery) { //si usuario ya fue agregado a la query, entonces ya no agrega esta sección
        	if (!cve_m_usuario.isEmpty()) {
        		qry.append(" and a.id_usuario = " + cve_m_usuario);
        	}
        }
        
        if (!nombre.isEmpty()) {
        	qry.append(" and u.nombre like '%" + nombre + "%' ");
        }
        
        if (!paterno.isEmpty()) {
        	qry.append(" and u.apellido_paterno like '%" + paterno + "%' ");
        }
        
        if (!materno.isEmpty()) {
        	qry.append(" and u.apellido_materno like '%" + materno + "%' ");
        }
        
        if (!unidadAdministrativa.isEmpty()) {
        	qry.append(" and ua.nombre like '%" + unidadAdministrativa + "%' ");
        }
        
        if (!nivel.isEmpty()) {
        	qry.append(" and u.nivel like '%" + nivel + "%' ");
        }
        
        if (!tipo.isEmpty()) {
        	qry.append(" and t.nombre like '%" + tipo + "%' ");
        }
        
        if (!estado.isEmpty()) {
        	qry.append(" and e.estatus like '%" + estado + "%' ");
        }
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        for (Map<String, Object> a : asistencias) {
        	UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get("id_usuario"));
        	
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get("estatus"));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get("descuento") != null) {
        		incidencia.setDescuento((Boolean) a.get("descuento"));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get("id_asistencia"));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		
		StringBuilder qry = new StringBuilder();
        
        qry.append("SELECT a.id_asistencia, a.entrada, a.id_tipo_dia, t.nombre as nombre_tipo, e.estatus, e.id_estatus, ");
        qry.append("i.id_incidencia, j.id_justificacion, j.justificacion, u.nombre as nombre_usuario, u.apellido_paterno, u.apellido_materno, ");
        qry.append("u.fecha_ingreso, u.cve_m_usuario, u.nombre_jefe, p.descripcion, u.id_puesto, u.rfc, u.nivel, ua.nombre as nombre_unidad, i.id_archivo, ch.url, ch.nombre as nombre_archivo ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join m_usuario u on u.cve_m_usuario = a.id_usuario ");
        qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
        qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
        qry.append("inner join c_perfil p on p.cve_c_perfil = u.cve_c_perfil ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("left join c_justificacion j on j.id_justificacion = i.id_justificacion ");
        qry.append("left join m_archivo ch on ch.id_archivo = i.id_archivo ");
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
        usuario.setIdPuesto((String) informacionConsulta.get("puesto"));
        usuario.setRfc((String) informacionConsulta.get("rfc")) ;
        usuario.setNivel((String) informacionConsulta.get("nivel"));
        usuario.setIdPuesto((String) informacionConsulta.get("id_puesto"));
        usuario.setNombreUnidad((String) informacionConsulta.get("nombre_unidad"));
        usuario.setNombreJefe((String) informacionConsulta.get("nombre_jefe"));
        
        TipoDiaDto tipoDia = new TipoDiaDto();
        tipoDia.setIdTipoDia((Integer) informacionConsulta.get("id_tipo_dia"));
        tipoDia.setNombre((String) informacionConsulta.get("nombre_tipo"));
        
        EstatusDto estatus = new EstatusDto();
        estatus.setIdEstatus((Integer) informacionConsulta.get("id_estatus") );
    	estatus.setEstatus((String) informacionConsulta.get("estatus"));
    	
    	JustificacionDto justificacion = new JustificacionDto();
    	justificacion.setIdJustificacion((Integer) informacionConsulta.get("id_justificacion"));
    	justificacion.setJustificacion((String) informacionConsulta.get("justificacion"));
    	
    	ArchivoDto archivo = new ArchivoDto();
    	archivo.setIdArchivo((Integer) informacionConsulta.get("id_archivo"));
    	archivo.setUrl((String) informacionConsulta.get("url"));
    	archivo.setNombre((String) informacionConsulta.get("nombre_archivo"));
    	
    	IncidenciaDto incidencia = new IncidenciaDto();
    	incidencia.setIdIncidencia((Integer) informacionConsulta.get("id_incidencia"));
    	incidencia.setJustificacion(justificacion);
    	incidencia.setIdArchivo(archivo);
    	
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
	public Integer creaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_incidencia (id_asistencia, id_tipo_dia, id_archivo, id_estatus, id_responsable, descuento, observaciones, id_justificacion, nombre_autorizador) ");
		qry.append("VALUES (:idAsistencia, :idTipoDia, :idArchivo, :idEstatus, null, null, null, :idJustificacion, :nombreAutorizador) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idTipoDia", incidencia.getTipoDia().getIdTipoDia());
		parametros.addValue("idEstatus", incidencia.getEstatus().getIdEstatus());
		parametros.addValue("idJustificacion", incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue("idArchivo", incidencia.getIdArchivo().getIdArchivo());
		parametros.addValue("nombreAutorizador", incidencia.getNombreAutorizador());

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public Integer creaDescuento(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_incidencia (id_asistencia, id_tipo_dia, id_archivo, id_estatus, id_responsable, descuento, observaciones, id_justificacion, nombre_autorizador) ");
		qry.append("VALUES (:idAsistencia, :idTipoDia, :idArchivo, :idEstatus, null, :descuento, null, :idJustificacion, :nombreAutorizador) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idTipoDia", incidencia.getTipoDia().getIdTipoDia());
		parametros.addValue("idEstatus", incidencia.getEstatus().getIdEstatus());
		parametros.addValue("descuento", incidencia.getDescuento());
		parametros.addValue("idJustificacion", incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue("idArchivo", incidencia.getIdArchivo().getIdArchivo());
		parametros.addValue("nombreAutorizador", incidencia.getNombreAutorizador());

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
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
	public boolean existeDescuento(Integer idAsistencia) {
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
	public Integer editaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("update m_incidencia ");
		qry.append("set id_justificacion = :idJustificacion, id_archivo = :idArchivo, nombre_autorizador = :nombreAutorizador, descuento = :descuento ");
        qry.append("WHERE id_asistencia = :idAsistencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idJustificacion", incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue("idArchivo", incidencia.getIdArchivo().getIdArchivo());
		parametros.addValue("nombreAutorizador", incidencia.getNombreAutorizador());
		parametros.addValue("descuento", incidencia.getDescuento());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al consultar la incidencia con número de asistencia: " + incidencia.getIdAsistencia().getIdAsistencia() + ". " + e.getMessage());
		}
		
		return 0;
	}
	
	@Override
	public Integer editaDescuento(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("update m_incidencia ");
		qry.append("set id_justificacion = :idJustificacion, nombre_autorizador = :nombreAutorizador, id_archivo = :idArchivo, descuento = :descuento ");
        qry.append("WHERE id_asistencia = :idAsistencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idJustificacion", incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue("nombreAutorizador", incidencia.getNombreAutorizador());
		parametros.addValue("descuento", incidencia.getDescuento());
		parametros.addValue("idArchivo", incidencia.getIdArchivo().getIdArchivo());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al consultar la incidencia con número de asistencia: " + incidencia.getIdAsistencia().getIdAsistencia() + ". " + e.getMessage());
		}
		
		return 0;
	}
	
	@Override
	public Integer dictaminaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("update m_incidencia ");
		qry.append("set id_estatus = :idEstatus ");
        qry.append("WHERE id_asistencia = :idAsistencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idEstatus", incidencia.getEstatus().getIdEstatus());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al dictaminar la justificación, en la indicencia: " + incidencia.getIdAsistencia().getIdAsistencia() + " " + e.getMessage());
		}
		
		return 0;
	}
	
	@Override
	public Integer aplicaDescuento(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("update m_incidencia ");
		qry.append("set id_estatus = :idEstatus, set descuento = :descuento "); 
        qry.append("WHERE id_asistencia = :idAsistencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue("idEstatus", incidencia.getEstatus().getIdEstatus());
		parametros.addValue("descuento", incidencia.getDescuento());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al aplicar descuento en la incidencia: " + incidencia.getIdAsistencia().getIdAsistencia() + " " + e.getMessage());
		}
		
		return 0;
	}

	@Override
	public Integer agregaAsistencia(AsistenciaDto asistenciaDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("insert into m_asistencia (id_usuario, id_tipo_dia, id_estatus, entrada, salida ) ");
		qry.append("values (:idUsuario, :idTipoDia, :idEstatus, :entrada, :salida) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", asistenciaDto.getUsuarioDto().getClaveUsuario());
		parametros.addValue("idTipoDia", asistenciaDto.getIdTipoDia().getIdTipoDia());
		parametros.addValue("idEstatus", asistenciaDto.getIdEstatus().getIdEstatus());
		parametros.addValue("entrada", asistenciaDto.getEntrada());
		parametros.addValue("salida", asistenciaDto.getSalida());

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public List<String> obtieneListaEmpleadosDeVacacionesHoy() {
		StringBuilder qry = new StringBuilder();
	       
        qry.append("SELECT id_usuario ");
        qry.append("FROM m_asistencia ");
        qry.append("where entrada >= date_Add(curdate(), interval -1 day) "); //interesa el día de ayer
        qry.append("and entrada < curdate() ");
        qry.append("and id_tipo_dia = 5"); //vacación

        List<Map<String, Object>> empleados = jdbcTemplate.queryForList(qry.toString());
        List<String> listaEmpleados = new ArrayList<>();
        
        for (Map<String, Object> a : empleados) {
    		listaEmpleados.add((String) a.get("id_usuario"));
    	}
        
        return listaEmpleados;
	}
	
	@Override
	public List<String> obtieneListaEmpleadosDeComisionHoy() {
		StringBuilder qry = new StringBuilder();
	       
        qry.append("SELECT id_usuario ");
        qry.append("FROM m_asistencia ");
        qry.append("where entrada >= date_Add(curdate(), interval -1 day) "); //interesa el día de ayer
        qry.append("and entrada < curdate() ");
        qry.append("and id_tipo_dia = 7"); //vac

        List<Map<String, Object>> empleados = jdbcTemplate.queryForList(qry.toString());
        List<String> listaEmpleados = new ArrayList<>();
        
        for (Map<String, Object> a : empleados) {
    		listaEmpleados.add((String) a.get("id_usuario"));
    	}
        
        return listaEmpleados;
	}
	
	@Override
	public List<String> obtieneListaEmpleadosDeLicenciaHoy() {
		StringBuilder qry = new StringBuilder();
	       
        qry.append("SELECT id_usuario ");
        qry.append("FROM m_asistencia ");
        qry.append("where entrada >= date_Add(curdate(), interval -1 day) "); //interesa el día de ayer
        qry.append("and entrada < curdate() ");
        qry.append("and id_tipo_dia = 8"); //vacación

        List<Map<String, Object>> empleados = jdbcTemplate.queryForList(qry.toString());
        List<String> listaEmpleados = new ArrayList<>();
        
        for (Map<String, Object> a : empleados) {
    		listaEmpleados.add((String) a.get("id_usuario"));
    	}
        
        return listaEmpleados;
	}

	@Override
	public List<AsistenciaDto> reporteDireccion(String cve_m_usuario, String nombre, String paterno, String materno,
			String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa,
			String p) {

		StringBuilder qry = new StringBuilder();
		Boolean usuarioFueAgregadoAQuery = false;
	       
        qry.append("SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ");
        qry.append("i.id_estatus, i.descuento ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("inner join m_usuario u on u.cve_m_usuario = a.id_usuario ");
        qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
        qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
        
        if (fechaInicial != null && fechaFinal != null) {
        	qry.append("where entrada >= '" + fechaInicial + "'");
        	qry.append(" and entrada < '" + fechaFinal  + "'");
        } else {
        	if (!cve_m_usuario.isEmpty()) {
            	qry.append("where a.id_usuario = " + cve_m_usuario);
            	usuarioFueAgregadoAQuery = true;
            }
        }
        
        if (!usuarioFueAgregadoAQuery) { //si usuario ya fue agregado a la query, entonces ya no agrega esta sección
        	if (!cve_m_usuario.isEmpty()) {
        		qry.append(" and a.id_usuario = " + cve_m_usuario);
        	}
        }
        
        if (!nombre.isEmpty()) {
        	qry.append(" and u.nombre like '%" + nombre + "%' ");
        }
        
        if (!paterno.isEmpty()) {
        	qry.append(" and u.apellido_paterno like '%" + paterno + "%' ");
        }
        
        if (!materno.isEmpty()) {
        	qry.append(" and u.apellido_materno like '%" + materno + "%' ");
        }
        
        if (!unidadAdministrativa.isEmpty()) {
        	qry.append(" and ua.nombre like '%" + unidadAdministrativa + "%' ");
        }
        
        if (!nivel.isEmpty()) {
        	qry.append(" and u.nivel like '%" + nivel + "%' ");
        }
        
        if (!tipo.isEmpty()) {
        	qry.append(" and t.nombre like '%" + tipo + "%' ");
        }
        
        if (!estado.isEmpty()) {
        	qry.append(" and e.estatus like '%" + estado + "%' ");
        }
        
        //reglas para condiciones de permisos 
        if (p != null && !p.isEmpty()) {
        	String[] arrayPermisos = p.split(",");
            List<String> listaPermisos = new ArrayList<String>(Arrays.asList(arrayPermisos));
            String condicionVacacion = "or";
            String condicionComision = "or";
            String condicionLicencia = "or";
            String condicionDescuento = "or";
            
            //verificando que permisos hay para definir que el primero no llevará 'or' y los demás sí
            for (String permiso : listaPermisos) {
	        	if (permiso.contains("vacacion")) {
	        		condicionVacacion = "";
	        		break;
	        	} 
	        	
	        	if (permiso.contains("comision")) {
	        		condicionComision = "";
	        		break;
	        	}
	        	
	        	if (permiso.contains("licencia")) {
	        		condicionLicencia = "";
	        		break;
	        	} 
	        	
	        	if (permiso.contains("descuento")) {
	        		condicionDescuento = "";
	        		break;
	        	} 
        	}
        	
        	//se arma la sentencia de la consulta
        	qry.append( " and (");
        	
    		if (listaPermisos.contains("vacacion")) {
    			qry.append(condicionVacacion + " a.id_tipo_dia = 5 ");
    		}
    		
    		if (listaPermisos.contains("comision")) {
    			qry.append(condicionComision + " a.id_tipo_dia = 7 ");
    		}
    		
    		if (listaPermisos.contains("licencia")) {
    			qry.append(condicionLicencia + " a.id_tipo_dia = 8 ");
    		}
    		
    		//descuento: validada y la bandera descuento
    		if (listaPermisos.contains("descuento")) {
    			qry.append(condicionDescuento + " (e.id_estatus = 2 and i.descuento = 1)");
    		}
        	
        	qry.append(")");
        }
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        for (Map<String, Object> a : asistencias) {
        	UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get("id_usuario"));
        	
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get("estatus"));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get("descuento") != null) {
        		incidencia.setDescuento((Boolean) a.get("descuento"));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get("id_asistencia"));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}

	@Override
	public List<AsistenciaDto> reporteCoordinador(String cve_m_usuario, String nombre, String paterno, String materno,
			String nivel, String tipo, String estado, Date fechaInicial, Date fechaFinal, String unidadAdministrativa, Integer idUnidadCoordinador,
			String p) {

		StringBuilder qry = new StringBuilder();
	       
        qry.append("SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ");
        qry.append("i.id_estatus, i.descuento ");
        qry.append("FROM m_asistencia a ");
        qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
        qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
        qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
        qry.append("inner join m_usuario u on u.cve_m_usuario = a.id_usuario ");
        qry.append("inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
        qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");
        qry.append("where uua.id_unidad = " + idUnidadCoordinador);
        
        if (fechaInicial != null && fechaFinal != null) {
        	qry.append(" and entrada >= '" + fechaInicial + "'");
        	qry.append(" and entrada < '" + fechaFinal  + "'");
        } 
        
    	if (!cve_m_usuario.isEmpty()) {
    		qry.append(" and a.id_usuario = " + cve_m_usuario);
    	}
        
        if (!nombre.isEmpty()) {
        	qry.append(" and u.nombre like '%" + nombre + "%' ");
        }
        
        if (!paterno.isEmpty()) {
        	qry.append(" and u.apellido_paterno like '%" + paterno + "%' ");
        }
        
        if (!materno.isEmpty()) {
        	qry.append(" and u.apellido_materno like '%" + materno + "%' ");
        }
        
        if (!unidadAdministrativa.isEmpty()) {
        	qry.append(" and ua.nombre like '%" + unidadAdministrativa + "%' ");
        }
        
        if (!nivel.isEmpty()) {
        	qry.append(" and u.nivel like '%" + nivel + "%' ");
        }
        
        if (!tipo.isEmpty()) {
        	qry.append(" and t.nombre like '%" + tipo + "%' ");
        }
        
        if (!estado.isEmpty()) {
        	qry.append(" and e.estatus like '%" + estado + "%' ");
        }
        
        //reglas para condiciones de permisos 
        if (p != null && !p.isEmpty()) {
        	String[] arrayPermisos = p.split(",");
            List<String> listaPermisos = new ArrayList<String>(Arrays.asList(arrayPermisos));
            String condicionVacacion = "or";
            String condicionComision = "or";
            String condicionLicencia = "or";
            String condicionDescuento = "or";
            
            //verificando que permisos hay para definir que el primero no llevará 'or' y los demás sí
            for (String permiso : listaPermisos) {
	        	if (permiso.contains("vacacion")) {
	        		condicionVacacion = "";
	        		break;
	        	} 
	        	
	        	if (permiso.contains("comision")) {
	        		condicionComision = "";
	        		break;
	        	}
	        	
	        	if (permiso.contains("licencia")) {
	        		condicionLicencia = "";
	        		break;
	        	} 
	        	
	        	if (permiso.contains("descuento")) {
	        		condicionDescuento = "";
	        		break;
	        	} 
        	}
        	
        	//se arma la sentencia de la consulta
        	qry.append( " and (");
        	
    		if (listaPermisos.contains("vacacion")) {
    			qry.append(condicionVacacion + " a.id_tipo_dia = 5 ");
    		}
    		
    		if (listaPermisos.contains("comision")) {
    			qry.append(condicionComision + " a.id_tipo_dia = 7 ");
    		}
    		
    		if (listaPermisos.contains("licencia")) {
    			qry.append(condicionLicencia + " a.id_tipo_dia = 8 ");
    		}
    		
    		//descuento: validada y la bandera descuento
    		if (listaPermisos.contains("descuento")) {
    			qry.append(condicionDescuento + " (e.id_estatus = 2 and i.descuento = 1)");
    		}
        	
        	qry.append(")");
        }
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        for (Map<String, Object> a : asistencias) {
        	UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get("id_usuario"));
        	
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get("estatus"));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get("descuento") != null) {
        		incidencia.setDescuento((Boolean) a.get("descuento"));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get("id_asistencia"));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}

}
