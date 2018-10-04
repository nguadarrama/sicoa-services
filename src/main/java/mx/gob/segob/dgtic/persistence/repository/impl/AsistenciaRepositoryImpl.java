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
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;

@Repository
public class AsistenciaRepositoryImpl implements AsistenciaRepository {
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
		
		qry.append("SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre ");
        qry.append("FROM m_asistencia a, c_tipo_dia t ");
        qry.append("WHERE id_usuario = ? ");
        qry.append("and a.id_tipo_dia = t.id_tipo_dia ");
        qry.append("and entrada >= ? ");
        qry.append("and entrada < ? ");
        qry.append("order by entrada");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveEmpleado", claveEmpleado);
        parametros.addValue("fechaInicio", fechaInicio);
        parametros.addValue("fechaFin", fechaFin);
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString(), claveEmpleado, fechaInicio, fechaFin);
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        UsuarioDto usuario = usuarioRepository.buscaUsuario(claveEmpleado);
        
        for (Map<String, Object> a : asistencias) {
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get("id_tipo_dia"));
        	tipoDia.setNombre((String) a.get("nombre"));
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get("id_asistencia"));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get("entrada"));
    		asistencia.setSalida((Timestamp) a.get("salida"));
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT a.id_asistencia, a.entrada, a.id_tipo_dia, t.nombre ");
        qry.append("FROM m_asistencia a, c_tipo_dia t ");
        qry.append("WHERE id_asistencia = :idAsistencia ");
        qry.append("and a.id_tipo_dia = t.id_tipo_dia");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idAsistencia", id);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        
        TipoDiaDto tipoDia = new TipoDiaDto();
        tipoDia.setIdTipoDia((Integer) informacionConsulta.get("id_tipo_dia"));
//        tipoDia.setNombre((String) informacionConsulta.get("nombre"));
        
        AsistenciaDto asistencia = new AsistenciaDto();
        asistencia.setIdAsistencia((Integer) informacionConsulta.get("id_asistencia"));
        asistencia.setEntrada((Timestamp) informacionConsulta.get("entrada"));
        asistencia.setIdTipoDia(tipoDia);

        return asistencia;
	}
		


}
