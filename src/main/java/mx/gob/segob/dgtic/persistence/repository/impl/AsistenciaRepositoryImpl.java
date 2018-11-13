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
import mx.gob.segob.dgtic.comun.util.AsistenciaBusquedaUtil;
import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;
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
        	tipoDia.setIdTipoDia((Integer) a.get(RepositoryConstants.ID_TIPO_DIA));
        	tipoDia.setNombre((String) a.get(RepositoryConstants.NOMBRE));
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get(RepositoryConstants.ENTRADA));
    		asistencia.setSalida((Timestamp) a.get(RepositoryConstants.SALIDA));
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRango(String claveEmpleado, Date fechaInicio, Date fechaFin) {
			
		StringBuilder qry = new StringBuilder();
		       
        qry.append(RepositoryConstants.ARIL84);
        qry.append(RepositoryConstants.ARIL85);
        qry.append(RepositoryConstants.ARIL86);
        qry.append(RepositoryConstants.ARIL87);
        qry.append(RepositoryConstants.ARIL88);
        qry.append(RepositoryConstants.ARIL89);
        qry.append(RepositoryConstants.ARIL90 + claveEmpleado);
        qry.append(RepositoryConstants.ARIL91);
        
        if (fechaInicio != null && fechaFin != null) {
        	qry.append(RepositoryConstants.ARIL94 + fechaInicio + "'");
        	qry.append(RepositoryConstants.ARIL95 + fechaFin + "'");
        }
        
        qry.append(" order by entrada");

        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        UsuarioDto usuario = usuarioRepository.buscaUsuario(claveEmpleado);
        
        for (Map<String, Object> a : asistencias) {
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get(RepositoryConstants.ID_TIPO_DIA));
        	tipoDia.setNombre((String) a.get(RepositoryConstants.NOMBRE));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get(RepositoryConstants.ESTATUS));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get(RepositoryConstants.DESCUENTO) != null) {
        		incidencia.setDescuento((Boolean) a.get(RepositoryConstants.DESCUENTO));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get(RepositoryConstants.ID_ASISTENCIA));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get(RepositoryConstants.ENTRADA));
    		asistencia.setSalida((Timestamp) a.get(RepositoryConstants.SALIDA));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
			
		StringBuilder qry = new StringBuilder();
	       
        qry.append(RepositoryConstants.ARIL142);
        qry.append(RepositoryConstants.ARIL143);
        qry.append(RepositoryConstants.ARIL144);
        qry.append(RepositoryConstants.ARIL145);
        qry.append(RepositoryConstants.ARIL146);
        qry.append(RepositoryConstants.ARIL147);
        qry.append(RepositoryConstants.ARIL148);
        qry.append(RepositoryConstants.ARIL149);
        qry.append(RepositoryConstants.ARIL150);
        qry.append(RepositoryConstants.ARIL151 + asistenciaBusquedaUtil.getIdUnidadCoordinador());
        
        if (asistenciaBusquedaUtil.getFechaInicialDate() != null && asistenciaBusquedaUtil.getFechaFinalDate() != null) {
        	qry.append(RepositoryConstants.ARIL154 + asistenciaBusquedaUtil.getFechaInicialDate() + "'");
        	qry.append(RepositoryConstants.ARIL155 + asistenciaBusquedaUtil.getFechaFinalDate()  + "'");
        }
        
        if (!asistenciaBusquedaUtil.getCveMusuario().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL159 + asistenciaBusquedaUtil.getCveMusuario());
        }
        
        if (!asistenciaBusquedaUtil.getNombre().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL163 + asistenciaBusquedaUtil.getNombre() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getPaterno().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL167 + asistenciaBusquedaUtil.getPaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getMaterno().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL171 + asistenciaBusquedaUtil.getMaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getUnidadAdministrativa().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL175);
        }
        
        if (!asistenciaBusquedaUtil.getNivel().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL179 + asistenciaBusquedaUtil.getNivel() + "%' ");
        }
        
        if (asistenciaBusquedaUtil.getTipo() != null) {
        	qry.append(RepositoryConstants.ARIL183 + asistenciaBusquedaUtil.getTipo());
        }
        
        if (asistenciaBusquedaUtil.getEstado() != null) {
        	qry.append(RepositoryConstants.ARIL187 + asistenciaBusquedaUtil.getEstado());
        }
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        for (Map<String, Object> a : asistencias) {
        	UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get(RepositoryConstants.ID_USUARIO));
        	
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get(RepositoryConstants.ID_TIPO_DIA));
        	tipoDia.setNombre((String) a.get(RepositoryConstants.NOMBRE));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get(RepositoryConstants.ESTATUS));
        	estatus.setIdEstatus((Integer) a.get(RepositoryConstants.ID_ESTATUS));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get(RepositoryConstants.DESCUENTO) != null) {
        		incidencia.setDescuento((Boolean) a.get(RepositoryConstants.DESCUENTO));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get(RepositoryConstants.ID_ASISTENCIA));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get(RepositoryConstants.ENTRADA));
    		asistencia.setSalida((Timestamp) a.get(RepositoryConstants.SALIDA));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public List<AsistenciaDto> buscaAsistenciaEmpleadoRangoDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {
			
		StringBuilder qry = new StringBuilder();
	       
        qry.append(RepositoryConstants.ARIL233);
        qry.append(RepositoryConstants.ARIL234);
        qry.append(RepositoryConstants.ARIL235);
        qry.append(RepositoryConstants.ARIL236);
        qry.append(RepositoryConstants.ARIL237);
        qry.append(RepositoryConstants.ARIL238);
        qry.append(RepositoryConstants.ARIL239);
        qry.append(RepositoryConstants.ARIL240);
        qry.append(RepositoryConstants.ARIL241);
        qry.append(RepositoryConstants.ARIL242);
        
        if (asistenciaBusquedaUtil.getFechaInicialDate() != null && asistenciaBusquedaUtil.getFechaFinalDate() != null) {
        	qry.append(RepositoryConstants.ARIL245 + asistenciaBusquedaUtil.getFechaInicialDate() + "'");
        	qry.append(RepositoryConstants.ARIL246 + asistenciaBusquedaUtil.getFechaFinalDate()  + "'");
        }
        
    	if (!asistenciaBusquedaUtil.getCveMusuario().isEmpty()) {
    		qry.append(RepositoryConstants.ARIL250 + asistenciaBusquedaUtil.getCveMusuario());
    	}
        
        if (!asistenciaBusquedaUtil.getNombre().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL254 + asistenciaBusquedaUtil.getNombre() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getPaterno().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL258 + asistenciaBusquedaUtil.getPaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getMaterno().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL262 + asistenciaBusquedaUtil.getMaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getUnidadAdministrativa().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL266 + asistenciaBusquedaUtil.getUnidadAdministrativa());
        }
        
        if (!asistenciaBusquedaUtil.getNivel().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL270 + asistenciaBusquedaUtil.getNivel() + "%' ");
        }
        
        if (asistenciaBusquedaUtil.getTipo() != null) {
        	qry.append(RepositoryConstants.ARIL274 + asistenciaBusquedaUtil.getTipo());
        }
        
        if (asistenciaBusquedaUtil.getEstado() != null) {
        	qry.append(RepositoryConstants.ARIL278 + asistenciaBusquedaUtil.getEstado());
        }
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        for (Map<String, Object> a : asistencias) {
        	UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get(RepositoryConstants.ID_USUARIO));
        	
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get(RepositoryConstants.ID_TIPO_DIA));
        	tipoDia.setNombre((String) a.get(RepositoryConstants.NOMBRE));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get(RepositoryConstants.ESTATUS));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get(RepositoryConstants.DESCUENTO) != null) {
        		incidencia.setDescuento((Boolean) a.get(RepositoryConstants.DESCUENTO));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get(RepositoryConstants.ID_ASISTENCIA));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get(RepositoryConstants.ENTRADA));
    		asistencia.setSalida((Timestamp) a.get(RepositoryConstants.SALIDA));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}
	
	@Override
	public AsistenciaDto buscaAsistenciaPorId(Integer id) {
		
		StringBuilder qry = new StringBuilder();
        
        qry.append(RepositoryConstants.ARIL323);
        qry.append(RepositoryConstants.ARIL324);
        qry.append(RepositoryConstants.ARIL325);
        qry.append(RepositoryConstants.ARIL326);
        qry.append(RepositoryConstants.ARIL327);
        qry.append(RepositoryConstants.ARIL328);
        qry.append(RepositoryConstants.ARIL329);
        qry.append(RepositoryConstants.ARIL330);
        qry.append(RepositoryConstants.ARIL331);
        qry.append(RepositoryConstants.ARIL332);
        qry.append(RepositoryConstants.ARIL333);
        qry.append(RepositoryConstants.ARIL334);
        qry.append(RepositoryConstants.ARIL335);
        qry.append(RepositoryConstants.ARIL336);

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, id);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        
        PerfilDto perfil = new PerfilDto();
        perfil.setDescripcion((String) informacionConsulta.get(RepositoryConstants.DESCRIPCION));
        
        UsuarioDto usuario = new UsuarioDto();
        usuario.setNombre((String) informacionConsulta.get(RepositoryConstants.NOMBRE_USUARIO));
        usuario.setApellidoPaterno((String) informacionConsulta.get(RepositoryConstants.APELLIDO_PATERNO));
        usuario.setApellidoMaterno((String) informacionConsulta.get(RepositoryConstants.APELLIDO_MATERNO));
        usuario.setFechaIngreso((Date) informacionConsulta.get(RepositoryConstants.FECHA_INGRESO));
        usuario.setClaveUsuario((String) informacionConsulta.get(RepositoryConstants.CLAVE_M_USUARIO));
        usuario.setClavePerfil(perfil);
        usuario.setIdPuesto((String) informacionConsulta.get(RepositoryConstants.PUESTO));
        usuario.setRfc((String) informacionConsulta.get(RepositoryConstants.RFC)) ;
        usuario.setNivel((String) informacionConsulta.get(RepositoryConstants.NIVEL));
        usuario.setIdPuesto((String) informacionConsulta.get(RepositoryConstants.ID_PUESTO));
        usuario.setNombreUnidad((String) informacionConsulta.get(RepositoryConstants.NOMBRE_UNIDAD));
        usuario.setNombreJefe((String) informacionConsulta.get(RepositoryConstants.NOMBRE_JEFE));
        
        TipoDiaDto tipoDia = new TipoDiaDto();
        tipoDia.setIdTipoDia((Integer) informacionConsulta.get(RepositoryConstants.ID_TIPO_DIA));
        tipoDia.setNombre((String) informacionConsulta.get(RepositoryConstants.NOMBRE_TIPO));
        
        EstatusDto estatus = new EstatusDto();
        estatus.setIdEstatus((Integer) informacionConsulta.get(RepositoryConstants.ID_ESTATUS) );
    	estatus.setEstatus((String) informacionConsulta.get(RepositoryConstants.ESTATUS));
    	
    	JustificacionDto justificacion = new JustificacionDto();
    	justificacion.setIdJustificacion((Integer) informacionConsulta.get(RepositoryConstants.ID_JUSTIFICACION));
    	justificacion.setJustificacion((String) informacionConsulta.get(RepositoryConstants.JUSTIFICACION));
    	
    	ArchivoDto archivo = new ArchivoDto();
    	archivo.setIdArchivo((Integer) informacionConsulta.get(RepositoryConstants.ID_ARCHIVO));
    	archivo.setUrl((String) informacionConsulta.get(RepositoryConstants.URL));
    	archivo.setNombre((String) informacionConsulta.get(RepositoryConstants.NOMBRE_ARCHIVO));
    	
    	IncidenciaDto incidencia = new IncidenciaDto();
    	incidencia.setIdIncidencia((Integer) informacionConsulta.get(RepositoryConstants.ID_INCIDENCIA));
    	incidencia.setJustificacion(justificacion);
    	incidencia.setIdArchivo(archivo);
    	
    	AsistenciaDto asistencia = new AsistenciaDto();
        asistencia.setIdAsistencia((Integer) informacionConsulta.get(RepositoryConstants.ID_ASISTENCIA));
        asistencia.setEntrada((Timestamp) informacionConsulta.get(RepositoryConstants.ENTRADA));
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
		parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue(RepositoryConstants.ID_TIPO_DIA2, incidencia.getTipoDia().getIdTipoDia());
		parametros.addValue(RepositoryConstants.ID_ESTATUS2, incidencia.getEstatus().getIdEstatus());
		parametros.addValue(RepositoryConstants.ID_JUSTIFICACION2, incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue(RepositoryConstants.ID_ARCHIVO2, incidencia.getIdArchivo().getIdArchivo());
		parametros.addValue(RepositoryConstants.NOMBRE_AUTORIZADOR2, incidencia.getNombreAutorizador());

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}
	
	@Override
	public Integer creaDescuento(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO m_incidencia (id_asistencia, id_tipo_dia, id_archivo, id_estatus, id_responsable, descuento, observaciones, id_justificacion, nombre_autorizador) ");
		qry.append("VALUES (:idAsistencia, :idTipoDia, :idArchivo, :idEstatus, null, :descuento, null, :idJustificacion, :nombreAutorizador) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue(RepositoryConstants.ID_TIPO_DIA2, incidencia.getTipoDia().getIdTipoDia());
		parametros.addValue(RepositoryConstants.ID_ESTATUS2, incidencia.getEstatus().getIdEstatus());
		parametros.addValue(RepositoryConstants.DESCUENTO, incidencia.getDescuento());
		parametros.addValue(RepositoryConstants.ID_JUSTIFICACION2, incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue(RepositoryConstants.ID_ARCHIVO2, incidencia.getIdArchivo().getIdArchivo());
		parametros.addValue(RepositoryConstants.NOMBRE_AUTORIZADOR2, incidencia.getNombreAutorizador());

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public boolean existeIncidencia(Integer idAsistencia) {
		boolean existe = false;
		StringBuilder qry = new StringBuilder();
		
		qry.append("SELECT id_incidencia ");
        qry.append("FROM m_incidencia ");
        qry.append("WHERE id_asistencia = ? ");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, idAsistencia);
        
        List<Map<String, Object>> asistencia = jdbcTemplate.queryForList(qry.toString(), idAsistencia);
        
        if (asistencia.isEmpty()) {
        	existe = false;
        } else {
        	existe = true;
        }
        
        return existe;
	}
	
	@Override
	public boolean existeDescuento(Integer idAsistencia) {
		boolean respuesta = false;
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_incidencia ");
        qry.append("FROM m_incidencia ");
        qry.append("WHERE id_asistencia = ? ");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, idAsistencia);
        
        List<Map<String, Object>> asistencia = jdbcTemplate.queryForList(qry.toString(), idAsistencia);
        
        if (asistencia.isEmpty()) {
        	respuesta = false;
        } else {
        	respuesta = true;
        }
        
        return respuesta;
	}

	@Override
	public Integer editaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append(RepositoryConstants.L476);
		qry.append(RepositoryConstants.L477);
        qry.append(RepositoryConstants.L478);
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue(RepositoryConstants.ID_JUSTIFICACION2, incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue(RepositoryConstants.ID_ARCHIVO2, incidencia.getIdArchivo().getIdArchivo());
		parametros.addValue(RepositoryConstants.NOMBRE_AUTORIZADOR2, incidencia.getNombreAutorizador());
		parametros.addValue(RepositoryConstants.DESCUENTO, incidencia.getDescuento());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al consultar la incidencia con número de asistencia: {} ", incidencia.getIdAsistencia().getIdAsistencia() + ". " + e.getMessage());
		}	
		return 0;
	}
	
	@Override
	public Integer editaDescuento(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append(RepositoryConstants.ARL502);
		qry.append(RepositoryConstants.ARL503);
        qry.append(RepositoryConstants.ARL504);
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue(RepositoryConstants.ID_JUSTIFICACION2, incidencia.getJustificacion().getIdJustificacion());
		parametros.addValue(RepositoryConstants.NOMBRE_AUTORIZADOR2, incidencia.getNombreAutorizador());
		parametros.addValue(RepositoryConstants.DESCUENTO, incidencia.getDescuento());
		parametros.addValue(RepositoryConstants.ID_ARCHIVO2, incidencia.getIdArchivo().getIdArchivo());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al consultar la incidencia con número de asistencia: {} " ,incidencia.getIdAsistencia().getIdAsistencia() + ". " + e.getMessage());
		}
		
		return 0;
	}
	
	@Override
	public Integer dictaminaIncidencia(IncidenciaDto incidencia) {
		StringBuilder qry = new StringBuilder();
		
		qry.append("update m_incidencia ");
		qry.append("set id_estatus = :idEstatus, descuento = :descuento ");
        qry.append("WHERE id_asistencia = :idAsistencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue(RepositoryConstants.ID_ESTATUS2, incidencia.getEstatus().getIdEstatus());
		parametros.addValue(RepositoryConstants.DESCUENTO, incidencia.getDescuento());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al dictaminar la justificación, en la indicencia: {} ", incidencia.getIdAsistencia().getIdAsistencia() + " " + e.getMessage());
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
		parametros.addValue(RepositoryConstants.ID_ASISTENCIA2, incidencia.getIdAsistencia().getIdAsistencia());
		parametros.addValue(RepositoryConstants.ID_ESTATUS2, incidencia.getEstatus().getIdEstatus());
		parametros.addValue(RepositoryConstants.DESCUENTO, incidencia.getDescuento());
		
		try {
			return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		} catch (Exception e) {
			logger.error("Error al aplicar descuento en la incidencia: {} ", incidencia.getIdAsistencia().getIdAsistencia() + " " + e.getMessage());
		}
		
		return 0;
	}

	@Override
	public Integer agregaAsistencia(AsistenciaDto asistenciaDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("insert into m_asistencia (id_usuario, id_tipo_dia, id_estatus, entrada, salida ) ");
		qry.append("values (:idUsuario, :idTipoDia, :idEstatus, :entrada, :salida) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_USUARIO2, asistenciaDto.getUsuarioDto().getClaveUsuario());
		parametros.addValue(RepositoryConstants.ID_TIPO_DIA2, asistenciaDto.getIdTipoDia().getIdTipoDia());
		parametros.addValue(RepositoryConstants.ID_ESTATUS2, asistenciaDto.getIdEstatus().getIdEstatus());
		parametros.addValue(RepositoryConstants.ENTRADA, asistenciaDto.getEntrada());
		parametros.addValue(RepositoryConstants.SALIDA, asistenciaDto.getSalida());

		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public List<String> obtieneListaEmpleadosDeVacacionesHoy() {
		StringBuilder qry = new StringBuilder();
	       
        qry.append(RepositoryConstants.L584);
        qry.append(RepositoryConstants.L585);
        qry.append(RepositoryConstants.L586); //interesa el día de ayer
        qry.append(RepositoryConstants.L587);
        qry.append(RepositoryConstants.L588); //vacación

        List<Map<String, Object>> empleados = jdbcTemplate.queryForList(qry.toString());
        List<String> listaEmpleados = new ArrayList<>();
        
        for (Map<String, Object> a : empleados) {
    		listaEmpleados.add((String) a.get(RepositoryConstants.ID_USUARIO));
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
        qry.append("and id_tipo_dia = 7"); //comisión

        List<Map<String, Object>> empleados = jdbcTemplate.queryForList(qry.toString());
        List<String> listaEmpleados = new ArrayList<>();
        
        for (Map<String, Object> a : empleados) {
    		listaEmpleados.add((String) a.get(RepositoryConstants.ID_USUARIO));
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
        qry.append("and id_tipo_dia = 6"); //licencia

        List<Map<String, Object>> empleados = jdbcTemplate.queryForList(qry.toString());
        List<String> listaEmpleados = new ArrayList<>();
        
        for (Map<String, Object> a : empleados) {
    		listaEmpleados.add((String) a.get(RepositoryConstants.ID_USUARIO));
    	}
        
        return listaEmpleados;
	}

	@Override
	public List<AsistenciaDto> reporteDireccion(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {

		StringBuilder qry = new StringBuilder();
	       
        qry.append(RepositoryConstants.ARIL646);
        qry.append(RepositoryConstants.ARIL647);
        qry.append(RepositoryConstants.ARIL648);
        qry.append(RepositoryConstants.ARIL649);
        qry.append(RepositoryConstants.ARIL650);
        qry.append(RepositoryConstants.ARIL651);
        qry.append(RepositoryConstants.ARIL652);
        qry.append(RepositoryConstants.ARIL653);
        qry.append(RepositoryConstants.ARIL654);
        qry.append(RepositoryConstants.ARIL655);
        qry.append(RepositoryConstants.ARIL656);
        qry.append(RepositoryConstants.ARIL657);
        qry.append(RepositoryConstants.ARIL658);
        
        if (asistenciaBusquedaUtil.getFechaInicialDate() != null && asistenciaBusquedaUtil.getFechaFinalDate() != null) {
        	qry.append(RepositoryConstants.ARIL661 + asistenciaBusquedaUtil.getFechaInicialDate() + "'");
        	qry.append(RepositoryConstants.ARIL662 + asistenciaBusquedaUtil.getFechaFinalDate()  + "'");
        }
        
    	if (!asistenciaBusquedaUtil.getCveMusuario().isEmpty()) {
    		qry.append(RepositoryConstants.ARIL666 + asistenciaBusquedaUtil.getCveMusuario());
    	}
        
        if (!asistenciaBusquedaUtil.getNombre().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL670 + asistenciaBusquedaUtil.getNombre() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getPaterno().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL674 + asistenciaBusquedaUtil.getPaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getMaterno().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL678 + asistenciaBusquedaUtil.getMaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getUnidadAdministrativa().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL682 + asistenciaBusquedaUtil.getUnidadAdministrativa());
        }
        
        if (!asistenciaBusquedaUtil.getNivel().isEmpty()) {
        	qry.append(RepositoryConstants.ARIL686 + asistenciaBusquedaUtil.getNivel() + "%' ");
        }
        
        if (asistenciaBusquedaUtil.getTipo() != null) {
        	qry.append(RepositoryConstants.ARIL690 + asistenciaBusquedaUtil.getTipo());
        }
        
        if (asistenciaBusquedaUtil.getEstado() != null) {
        	qry.append(RepositoryConstants.ARIL694 + asistenciaBusquedaUtil.getEstado());
        }
        
        //reglas para condiciones de permisos 
        if (!asistenciaBusquedaUtil.getPermisos().isEmpty()) {
        	String[] arrayPermisos = asistenciaBusquedaUtil.getPermisos().split(",");
            List<String> listaPermisos = new ArrayList<>(Arrays.asList(arrayPermisos));
            String condicionVacacion = "or";
            String condicionComision = "or";
            String condicionLicencia = "or";
            String condicionDescuento = "or";
            
            //verificando que permisos hay para definir que el primero no llevará 'or' y los demás sí
            for (String permiso : listaPermisos) {
	        	if (permiso.contains(RepositoryConstants.VACACION)) {
	        		condicionVacacion = "";
	        		break;
	        	} 
	        	
	        	if (permiso.contains(RepositoryConstants.COMISION)) {
	        		condicionComision = "";
	        		break;
	        	}
	        	
	        	if (permiso.contains(RepositoryConstants.LICENCIA)) {
	        		condicionLicencia = "";
	        		break;
	        	} 
	        	
	        	if (permiso.contains(RepositoryConstants.DESCUENTO)) {
	        		condicionDescuento = "";
	        		break;
	        	} 
        	}
        	
        	//se arma la sentencia de la consulta
        	qry.append( " and (");
        	
    		if (listaPermisos.contains(RepositoryConstants.VACACION)) {
    			qry.append(condicionVacacion + " a.id_tipo_dia = 5 ");
    		}
    		
    		if (listaPermisos.contains(RepositoryConstants.COMISION)) {
    			qry.append(condicionComision + " a.id_tipo_dia = 7 ");
    		}
    		
    		if (listaPermisos.contains(RepositoryConstants.LICENCIA)) {
    			qry.append(condicionLicencia + " a.id_tipo_dia = 6 ");
    		}
    		
    		//descuento: validada y la bandera descuento
    		if (listaPermisos.contains(RepositoryConstants.DESCUENTO)) {
    			qry.append(condicionDescuento + " (e.id_estatus = 2 and i.descuento = 1)");
    		}
        	
        	qry.append(")");
        }
        
        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
        
        for (Map<String, Object> a : asistencias) {
        	UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get(RepositoryConstants.ID_USUARIO));
        	
        	TipoDiaDto tipoDia = new TipoDiaDto();
        	tipoDia.setIdTipoDia((Integer) a.get(RepositoryConstants.ID_TIPO_DIA));
        	tipoDia.setNombre((String) a.get(RepositoryConstants.NOMBRE));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get(RepositoryConstants.ESTATUS));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get(RepositoryConstants.DESCUENTO) != null) {
        		incidencia.setDescuento((Boolean) a.get(RepositoryConstants.DESCUENTO));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get(RepositoryConstants.ID_ASISTENCIA));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get(RepositoryConstants.ENTRADA));
    		asistencia.setSalida((Timestamp) a.get(RepositoryConstants.SALIDA));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}

	@Override
	public List<AsistenciaDto> reporteCoordinador(AsistenciaBusquedaUtil asistenciaBusquedaUtil) {

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
        qry.append("left join m_licencia_medica l on l.id_usuario = u.id_usuario ");
        qry.append("left join m_comision c on c.id_usuario = u.id_usuario ");
        qry.append("left join d_detalle_vacacion v on v.id_usuario = u.id_usuario ");
        qry.append("where uua.id_unidad = " + asistenciaBusquedaUtil.getIdUnidadCoordinador());
        
        if (asistenciaBusquedaUtil.getFechaInicialDate() != null && asistenciaBusquedaUtil.getFechaFinalDate() != null) {
        	qry.append(" and entrada >= '" + asistenciaBusquedaUtil.getFechaInicialDate() + "'");
        	qry.append(" and entrada < '" + asistenciaBusquedaUtil.getFechaFinalDate()  + "'");
        } 
        
    	if (!asistenciaBusquedaUtil.getCveMusuario().isEmpty()) {
    		qry.append(" and a.id_usuario = " + asistenciaBusquedaUtil.getCveMusuario());
    	}
        
        if (!asistenciaBusquedaUtil.getNombre().isEmpty()) {
        	qry.append(" and u.nombre like '%" + asistenciaBusquedaUtil.getNombre() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getPaterno().isEmpty()) {
        	qry.append(" and u.apellido_paterno like '%" + asistenciaBusquedaUtil.getPaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getMaterno().isEmpty()) {
        	qry.append(" and u.apellido_materno like '%" + asistenciaBusquedaUtil.getMaterno() + "%' ");
        }
        
        if (!asistenciaBusquedaUtil.getNivel().isEmpty()) {
        	qry.append(" and u.nivel like '%" + asistenciaBusquedaUtil.getNivel() + "%' ");
        }
        
        if (asistenciaBusquedaUtil.getTipo() != null) {
        	qry.append(" and t.id_tipo_dia = " + asistenciaBusquedaUtil.getTipo());
        }
        
        if (asistenciaBusquedaUtil.getEstado() != null) {
        	qry.append(" and e.id_estatus = " + asistenciaBusquedaUtil.getEstado());
        }
        
        //reglas para condiciones de permisos 
        if (!asistenciaBusquedaUtil.getPermisos().isEmpty()) {
        	String[] arrayPermisos = asistenciaBusquedaUtil.getPermisos().split(",");
            List<String> listaPermisos = new ArrayList<>(Arrays.asList(arrayPermisos));
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
    			qry.append(condicionLicencia + " a.id_tipo_dia = 6 ");
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
        	tipoDia.setIdTipoDia((Integer) a.get(RepositoryConstants.ID_TIPO_DIA));
        	tipoDia.setNombre((String) a.get(RepositoryConstants.NOMBRE));
        	
        	EstatusDto estatus = new EstatusDto();
        	estatus.setEstatus((String) a.get(RepositoryConstants.ESTATUS));
        	
        	IncidenciaDto incidencia = new IncidenciaDto();
        	incidencia.setEstatus(estatus);
        	
        	if ((Boolean) a.get(RepositoryConstants.DESCUENTO) != null) {
        		incidencia.setDescuento((Boolean) a.get(RepositoryConstants.DESCUENTO));
        	} else {
        		incidencia.setDescuento(false);
        	}
        	
        	AsistenciaDto asistencia = new AsistenciaDto();
        	asistencia.setIdAsistencia((Integer) a.get(RepositoryConstants.ID_ASISTENCIA));
    		asistencia.setUsuarioDto(usuario);
    		asistencia.setIdTipoDia(tipoDia);
    		asistencia.setEntrada((Timestamp) a.get(RepositoryConstants.ENTRADA));
    		asistencia.setSalida((Timestamp) a.get(RepositoryConstants.SALIDA));
    		asistencia.setIdEstatus(estatus);
    		asistencia.setIncidencia(incidencia);
    		
    		listaAsistencia.add(asistencia);
    	}
        
        return listaAsistencia;
	}

	@Override
	public void eliminaAsistencia(Integer idAsistencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("delete from m_asistencia  where id_asistencia = :idAsistencia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idAsistencia", idAsistencia);

			nameParameterJdbcTemplate.update(qry.toString(), parametros);
			
		
	}

  @Override
  public List<AsistenciaDto> buscaAsistenciaEmpleado(String claveUsuario, Integer tipo,
      Integer estado, Date fechaInicial, Date fechaFinal) {
    StringBuilder qry = new StringBuilder();
    Boolean usuarioFueAgregadoAQuery = false;

    qry.append(
        "SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ");
    qry.append("i.id_estatus, i.descuento ");
    qry.append("FROM m_asistencia a ");
    qry.append("inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ");
    qry.append("left join m_incidencia i on a.id_asistencia = i.id_asistencia ");
    qry.append("left join m_estatus e on e.id_estatus = i.id_estatus ");
    qry.append("inner join m_usuario u on u.cve_m_usuario = a.id_usuario ");
    qry.append(
        "inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ");
    qry.append("inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ");

    if (fechaInicial != null && fechaFinal != null) {
      qry.append("where entrada >= '" + fechaInicial + "'");
      qry.append(" and entrada <= '" + fechaFinal + "'");
    } else {
      if (!claveUsuario.isEmpty()) {
        qry.append("where a.id_usuario = " + claveUsuario);
        usuarioFueAgregadoAQuery = true;
      }
    }

    if (!usuarioFueAgregadoAQuery) { // si usuario ya fue agregado a la query, entonces ya no agrega
                                     // esta sección
      if (!claveUsuario.isEmpty()) {
        qry.append(" and a.id_usuario = " + claveUsuario);
      }
    }

    if (tipo > 0) {
      qry.append(" and t.id_tipo_dia = " + tipo);
    }

    if (estado > 0) {
      qry.append(" and e.id_estatus = " + estado);
    }

    List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
    List<AsistenciaDto> listaAsistencia = new ArrayList<>();

    for (Map<String, Object> a : asistencias) {
      UsuarioDto usuario = usuarioRepository.buscaUsuario((String) a.get(RepositoryConstants.ID_USUARIO));

      TipoDiaDto tipoDia = new TipoDiaDto();
      tipoDia.setIdTipoDia((Integer) a.get(RepositoryConstants.ID_TIPO_DIA));
      tipoDia.setNombre((String) a.get(RepositoryConstants.NOMBRE));

      EstatusDto estatus = new EstatusDto();
      estatus.setEstatus((String) a.get(RepositoryConstants.ESTATUS));

      IncidenciaDto incidencia = new IncidenciaDto();
      incidencia.setEstatus(estatus);

      if ((Boolean) a.get(RepositoryConstants.DESCUENTO) != null) {
        incidencia.setDescuento((Boolean) a.get(RepositoryConstants.DESCUENTO));
      } else {
        incidencia.setDescuento(false);
      }

      AsistenciaDto asistencia = new AsistenciaDto();
      asistencia.setIdAsistencia((Integer) a.get(RepositoryConstants.ID_ASISTENCIA));
      asistencia.setUsuarioDto(usuario);
      asistencia.setIdTipoDia(tipoDia);
      asistencia.setEntrada((Timestamp) a.get(RepositoryConstants.ENTRADA));
      asistencia.setSalida((Timestamp) a.get(RepositoryConstants.SALIDA));
      asistencia.setIdEstatus(estatus);
      asistencia.setIncidencia(incidencia);

      listaAsistencia.add(asistencia);
    }

    return listaAsistencia;
  }

}
