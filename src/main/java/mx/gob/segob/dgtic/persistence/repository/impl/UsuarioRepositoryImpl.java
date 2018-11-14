package mx.gob.segob.dgtic.persistence.repository.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.crypto.HashUtils;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Service
public class UsuarioRepositoryImpl extends RecursoBase implements UsuarioRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<UsuarioDto> obtenerListaUsuarios() {
		StringBuilder qry = new StringBuilder();
        qry.append("select distinct usuario.id_usuario, usuario.id_area, usuario.cve_c_perfil, usuario.id_horario, usuario.id_puesto, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.fecha_ingreso, usuario.activo, usuario.nuevo, usuario.en_sesion, usuario.ultimo_acceso, usuario.numero_intentos, usuario.bloqueado, usuario.fecha_bloqueo,  usuario.primera_vez, usuario.estatus, usuario.nivel, usuario.rfc, unidad.id_unidad, unidad.nombre nombre_unidad ");
        qry.append("from m_usuario usuario, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario");
        logger.info("Qry obtenerListaUsuarios: {} ",qry);
        List<Map<String, Object>> usuarios = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioDto> listaUsuario = new ArrayList<>();
        
        for (Map<String, Object> usuario : usuarios) {
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioDto.setIdUsuario((Integer)usuario.get(RepositoryConstants.ID_USUARIO));
    		usuarioDto.setIdArea((Integer)usuario.get(RepositoryConstants.ID_AREA));
    		usuarioDto.setIdPuesto((String)usuario.get(RepositoryConstants.ID_PUESTO));
    		usuarioDto.setClaveUsuario((String)usuario.get(RepositoryConstants.CLAVE_M_USUARIO));
    		usuarioDto.setNombre((String)usuario.get(RepositoryConstants.NOMBRE));
    		usuarioDto.setApellidoPaterno((String)usuario.get(RepositoryConstants.APELLIDO_PATERNO));
    		usuarioDto.setApellidoMaterno((String)usuario.get(RepositoryConstants.APELLIDO_MATERNO));
    		usuarioDto.setFechaIngreso((Date)usuario.get(RepositoryConstants.FECHA_INGRESO));
    		usuarioDto.setActivo((Boolean)usuario.get(RepositoryConstants.ACTIVO));
    		usuarioDto.setNuevo((Boolean)usuario.get(RepositoryConstants.NUEVO));
    		usuarioDto.setEnSesion((String)usuario.get(RepositoryConstants.EN_SESION));
    		usuarioDto.setUltimoAcceso((Date)usuario.get(RepositoryConstants.ULTIMO_ACCESO));
    		usuarioDto.setNumeroIntentos((Integer)usuario.get(RepositoryConstants.NUMERO_INTENTOS));
    		usuarioDto.setBloqueado((String)usuario.get(RepositoryConstants.BLOQUEADO));
    		usuarioDto.setActivo((Boolean)usuario.get(RepositoryConstants.ACTIVO));
    		usuarioDto.setFechaBloqueo((Date)usuario.get(RepositoryConstants.FECHA_BLOQUEO));
    		usuarioDto.setPrimeraVez((String)usuario.get(RepositoryConstants.PRIMERA_VEZ));
    		usuarioDto.setEstatus((String)usuario.get(RepositoryConstants.ESTATUS));
    		usuarioDto.setNivel((String)usuario.get(RepositoryConstants.NIVEL));
    		usuarioDto.setRfc((String)usuario.get(RepositoryConstants.RFC));
    		usuarioDto.setNombreUnidad((String)usuario.get(RepositoryConstants.NOMBRE_UNIDAD));
    		usuarioDto.setIdUnidad((Integer)usuario.get(RepositoryConstants.ID_UNIDAD));
    		listaUsuario.add(usuarioDto);
    	} 
        logger.info("usuarios encontrados: {}",listaUsuario.size());
		return listaUsuario;
	}

	@Override
	public UsuarioDto buscaUsuario(String claveUsuario) {
		
        StringBuilder qry = new StringBuilder();
        qry.append("select u.id_usuario, u.id_area, u.cve_c_perfil, u.id_horario, u.id_puesto, u.cve_m_usuario, ");
        qry.append("u.nombre, u.apellido_paterno, u.apellido_materno, u.fecha_ingreso, u.activo, u.nuevo, u.en_sesion, ");
        qry.append("u.ultimo_acceso, u.numero_intentos, u.bloqueado, u.fecha_bloqueo, u.primera_vez, u.estatus estatus_usuario, u.nivel, u.rfc, ");
        qry.append("p.descripcion, p.estatus, unidad.id_unidad, unidad.nombre as nombre_unidad, ");
        qry.append("h.hora_entrada, h.hora_salida ");
        qry.append("from m_usuario u, c_perfil p, c_horario h, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where u.cve_m_usuario = :claveUsuario ");
        qry.append("and u.cve_c_perfil = p.cve_c_perfil ");
        qry.append("and u.id_horario = h.id_horario and unidad.id_unidad=relacion.id_unidad and u.cve_m_usuario=relacion.cve_m_usuario limit 1");
        /** logger.info("Qry buscaUsuario: {} ", qry); **/
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.CLAVE_USUARIO2, claveUsuario);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        
        Horario horario = new Horario();
        horario.setIdHorario((Integer) informacionConsulta.get(RepositoryConstants.ID_HORARIO));
        horario.setHoraEntrada((Time) informacionConsulta.get(RepositoryConstants.HORA_ENTRADA));
        horario.setHoraSalida((Time) informacionConsulta.get(RepositoryConstants.HORA_SALIDA));
        
        PerfilDto perfil = new PerfilDto();
        perfil.setClavePerfil((String) informacionConsulta.get(RepositoryConstants.CVE_C_PERFIL));
        perfil.setDescripcion((String) informacionConsulta.get(RepositoryConstants.DESCRIPCION));
        
        UsuarioDto usuario = new UsuarioDto();
        
        usuario.setIdUsuario((Integer) informacionConsulta.get(RepositoryConstants.ID_USUARIO));
        usuario.setClavePerfil(perfil);
        usuario.setIdHorario(horario);
        usuario.setIdPuesto((String) informacionConsulta.get(RepositoryConstants.ID_PUESTO));
        usuario.setClaveUsuario((String) informacionConsulta.get(RepositoryConstants.CLAVE_M_USUARIO));
        usuario.setNombre((String) informacionConsulta.get(RepositoryConstants.NOMBRE));
        usuario.setApellidoPaterno((String) informacionConsulta.get(RepositoryConstants.APELLIDO_PATERNO));
        usuario.setApellidoMaterno((String) informacionConsulta.get(RepositoryConstants.APELLIDO_MATERNO));
        usuario.setFechaIngreso((Date) informacionConsulta.get(RepositoryConstants.FECHA_INGRESO));
        usuario.setActivo((Boolean) informacionConsulta.get(RepositoryConstants.ACTIVO));
        usuario.setNuevo((Boolean) informacionConsulta.get(RepositoryConstants.NUEVO));
        usuario.setEnSesion((String) informacionConsulta.get(RepositoryConstants.EN_SESION));
        usuario.setUltimoAcceso((Date) informacionConsulta.get(RepositoryConstants.ULTIMO_ACCESO));
        usuario.setNumeroIntentos((Integer) informacionConsulta.get(RepositoryConstants.NUMERO_INTENTOS));
        usuario.setBloqueado((String) informacionConsulta.get(RepositoryConstants.BLOQUEADO));
        usuario.setFechaBloqueo((Date) informacionConsulta.get(RepositoryConstants.FECHA_BLOQUEO));
        usuario.setPrimeraVez((String) informacionConsulta.get(RepositoryConstants.PRIMERA_VEZ));
        usuario.setEstatus((String) informacionConsulta.get(RepositoryConstants.ESTATUS_USUARIO));
        usuario.setNivel((String) informacionConsulta.get(RepositoryConstants.NIVEL));
        usuario.setRfc((String) informacionConsulta.get(RepositoryConstants.RFC));
        usuario.setIdUnidad((Integer)informacionConsulta.get(RepositoryConstants.ID_UNIDAD));
        usuario.setNombreUnidad((String) informacionConsulta.get(RepositoryConstants.NOMBRE_UNIDAD));
        
        return usuario;
	}

	@Override
	public Integer modificaUsuario(UsuarioDto usuarioDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("update m_usuario set id_horario = :idHorario, estatus = :estatus ");
		qry.append("WHERE cve_m_usuario = :claveUsuario ");
		MapSqlParameterSource parametros = new MapSqlParameterSource();	
		parametros.addValue(RepositoryConstants.ID_HORARIO2, usuarioDto.getIdHorario().getIdHorario());
		parametros.addValue(RepositoryConstants.CLAVE_USUARIO2, usuarioDto.getClaveUsuario());
		parametros.addValue(RepositoryConstants.ESTATUS, usuarioDto.getEstatus());
		return nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}

	@Override
	public void agregaUsuario(UsuarioDto usuarioDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("insert into m_usuario (id_area, cve_c_perfil, id_horario, id_puesto, cve_m_usuario, nombre, apellido_paterno, apellido_materno, "
        		+ "fecha_ingreso, password, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos, bloqueado, fecha_bloqueo,  primera_vez, estatus, "
        		+ "nivel, rfc, nombre_jefe) ");
		qry.append("values ( :idArea, :clavePerfil, :idHorario, :idPuesto, :claveUsuario, :nombre, :apellidoPaterno, :apellidoMaterno, :fechaIngreso, "
				+ ":password, :activo, :nuevo, :enSesion, :ultimoAcceso, :numeroIntentos, :bloqueado, :fechaBloqueo, :primeraVez, :estatus, "
				+ ":nivel, :rfc, :nombreJefe ) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_AREA2, usuarioDto.getIdArea());
		parametros.addValue(RepositoryConstants.CLAVE_PERFIL2, usuarioDto.getClavePerfil().getClavePerfil());
		parametros.addValue(RepositoryConstants.ID_HORARIO2, usuarioDto.getIdHorario().getIdHorario());
		parametros.addValue(RepositoryConstants.ID_PUESTO2, usuarioDto.getIdPuesto());
		parametros.addValue(RepositoryConstants.CLAVE_USUARIO2, usuarioDto.getClaveUsuario());
		parametros.addValue(RepositoryConstants.NOMBRE, usuarioDto.getNombre());
		parametros.addValue(RepositoryConstants.APELLIDO_PATERNO2, usuarioDto.getApellidoPaterno());
		parametros.addValue(RepositoryConstants.APELLIDO_MATERNO2, usuarioDto.getApellidoMaterno());
		parametros.addValue(RepositoryConstants.FECHA_INGRESO2, usuarioDto.getFechaIngreso());
		parametros.addValue(RepositoryConstants.PASS_WORD, usuarioDto.getPassword());
		parametros.addValue(RepositoryConstants.ACTIVO, usuarioDto.getActivo());
		parametros.addValue(RepositoryConstants.NUEVO, usuarioDto.getNuevo());
		parametros.addValue(RepositoryConstants.EN_SESION2, usuarioDto.getEnSesion());
		parametros.addValue(RepositoryConstants.ULTIMO_ACCESO2, usuarioDto.getUltimoAcceso());
		parametros.addValue(RepositoryConstants.NUMERO_INTENTOS2, usuarioDto.getNumeroIntentos());
		parametros.addValue(RepositoryConstants.BLOQUEADO, usuarioDto.getBloqueado());
		parametros.addValue(RepositoryConstants.FECHA_BLOQUEO2, usuarioDto.getFechaBloqueo());
		parametros.addValue(RepositoryConstants.PRIMERA_VEZ2, usuarioDto.getPrimeraVez());
		parametros.addValue(RepositoryConstants.ESTATUS, usuarioDto.getEstatus());
		parametros.addValue(RepositoryConstants.NIVEL, usuarioDto.getNivel());
		parametros.addValue(RepositoryConstants.RFC, usuarioDto.getRfc());
		parametros.addValue(RepositoryConstants.NOMBRE_JEFE2, usuarioDto.getNombreJefe());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public void eliminaUsuario(String claveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("delete from m_usuario where cve_m_usuario = :claveUsuario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.CLAVE_USUARIO2, claveUsuario);
		nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}

	@Override
	public String consultaContrasenia(String claveUsuario) {
	StringBuilder qry = new StringBuilder();
	UsuarioDto usuarioDto=null;
	       qry.append("select password ");
	       qry.append("from m_usuario ");
	       qry.append("where cve_m_usuario = :claveUsuario");
	       MapSqlParameterSource parametros = new MapSqlParameterSource();
	       parametros.addValue("claveUsuario", claveUsuario);
	       
	       usuarioDto= nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<UsuarioDto>(UsuarioDto.class));
	       return usuarioDto.getPassword();
	}
	
	@Override
	public void reiniciaContrasenia(String claveUsuario) {
		StringBuilder qry = new StringBuilder();
		qry.append("update m_usuario set primera_vez ='S', password = :nuevaContrasenia WHERE cve_m_usuario = :claveUsuario  ");
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", claveUsuario);
		String nuevaContrasenia=HashUtils.md5(claveUsuario);
		logger.info("Datos idUsuario: {} ",claveUsuario);
		logger.info("contrasenia:  {} ",nuevaContrasenia);
		parametros.addValue("nuevaContrasenia", nuevaContrasenia);
		nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}

	@Override
	public List<UsuarioDto> obtenerListaUsuariosActivos(String fecha) {
		logger.info("fecha method-UsuarioRepoImpl: {} ",fecha);
		
		StringBuilder qry = new StringBuilder();
        qry.append("select id_usuario, id_area, cve_c_perfil, id_horario, id_puesto,"
        		+ " cve_m_usuario, nombre, apellido_paterno, apellido_materno, "
        		+ "fecha_ingreso, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos,"
        		+ " bloqueado, fecha_bloqueo,  primera_vez, estatus ");
        qry.append(" from m_usuario ");
        qry.append(" where estatus = 'A' ");
        qry.append(" and fecha_ingreso <= '")
        .append(fecha)
        .append("' ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("fecha", fecha);
        logger.info("parametros: {} ",parametros);
        List<Map<String, Object>> usuarios = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioDto> listaUsuario = new ArrayList<>();      
        for (Map<String, Object> usuario : usuarios) {
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioDto.setIdUsuario((Integer)usuario.get(RepositoryConstants.ID_USUARIO));
    		usuarioDto.setIdArea((Integer)usuario.get(RepositoryConstants.ID_AREA));
    		usuarioDto.setIdPuesto((String)usuario.get(RepositoryConstants.ID_PUESTO));
    		usuarioDto.setClaveUsuario((String)usuario.get(RepositoryConstants.CLAVE_M_USUARIO));
    		usuarioDto.setNombre((String)usuario.get(RepositoryConstants.NOMBRE));
    		usuarioDto.setApellidoPaterno((String)usuario.get(RepositoryConstants.APELLIDO_PATERNO));
    		usuarioDto.setApellidoMaterno((String)usuario.get(RepositoryConstants.APELLIDO_MATERNO));
    		usuarioDto.setFechaIngreso((Date)usuario.get(RepositoryConstants.FECHA_INGRESO));
    		usuarioDto.setActivo((Boolean)usuario.get(RepositoryConstants.ACTIVO));
    		usuarioDto.setNuevo((Boolean)usuario.get(RepositoryConstants.NUEVO));
    		usuarioDto.setEnSesion((String)usuario.get(RepositoryConstants.EN_SESION));
    		usuarioDto.setUltimoAcceso((Date)usuario.get(RepositoryConstants.ULTIMO_ACCESO));
    		usuarioDto.setNumeroIntentos((Integer)usuario.get(RepositoryConstants.NUMERO_INTENTOS));
    		usuarioDto.setBloqueado((String)usuario.get(RepositoryConstants.BLOQUEADO));
    		usuarioDto.setActivo((Boolean)usuario.get(RepositoryConstants.ACTIVO));
    		usuarioDto.setFechaBloqueo((Date)usuario.get(RepositoryConstants.FECHA_BLOQUEO));
    		usuarioDto.setPrimeraVez((String)usuario.get(RepositoryConstants.PRIMERA_VEZ));
    		usuarioDto.setEstatus((String)usuario.get(RepositoryConstants.ESTATUS));
    		listaUsuario.add(usuarioDto);
    	}        
        logger.info("usuarios encontrados method--UsuarioRepoImpl: {} ",listaUsuario.size());       
		return listaUsuario;
	}
	
	@Override
	public UsuarioDto buscaUsuarioPorId(Integer idUsuario) {
		StringBuilder qry = new StringBuilder();
        qry.append("select u.id_usuario, u.id_area, u.cve_c_perfil, u.id_horario, u.id_puesto, u.cve_m_usuario, ");
        qry.append("u.nombre, u.apellido_paterno, u.apellido_materno, u.fecha_ingreso, u.activo, u.nuevo, u.en_sesion, ");
        qry.append("u.ultimo_acceso, u.numero_intentos, u.bloqueado, u.fecha_bloqueo, u.primera_vez, u.estatus, u.nivel, u.rfc, ");
        qry.append("p.descripcion, p.estatus, unidad.id_unidad, unidad.nombre nombre_unidad, ");
        qry.append("h.hora_entrada, h.hora_salida ");
        qry.append("from m_usuario u, c_perfil p, c_horario h, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where u.id_usuario = :idUsuario ");
        qry.append("and u.cve_c_perfil = p.cve_c_perfil ");
        qry.append("and u.id_horario = h.id_horario and unidad.id_unidad=relacion.id_unidad and u.cve_m_usuario=relacion.cve_m_usuario limit 1");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idUsuario", idUsuario);
        
        Map<String, Object> informacionConsulta = nameParameterJdbcTemplate.queryForMap(qry.toString(), parametros);
        
        Horario horario = new Horario();
        horario.setIdHorario((Integer) informacionConsulta.get("id_horario"));
        horario.setHoraEntrada((Time) informacionConsulta.get("hora_entrada"));
        horario.setHoraSalida((Time) informacionConsulta.get("hora_salida"));
        
        PerfilDto perfil = new PerfilDto();
        perfil.setClavePerfil((String) informacionConsulta.get("cve_c_perfil"));
        perfil.setDescripcion((String) informacionConsulta.get("descripcion"));
        
        UsuarioDto usuario = new UsuarioDto();
        
        usuario.setIdUsuario((Integer) informacionConsulta.get("id_usuario"));
        usuario.setClavePerfil(perfil);
        usuario.setIdHorario(horario);
        usuario.setIdPuesto((String) informacionConsulta.get("id_puesto"));
        usuario.setClaveUsuario((String) informacionConsulta.get("cve_m_usuario"));
        usuario.setNombre((String) informacionConsulta.get("nombre"));
        usuario.setApellidoPaterno((String) informacionConsulta.get("apellido_paterno"));
        usuario.setApellidoMaterno((String) informacionConsulta.get("apellido_materno"));
        usuario.setFechaIngreso((Date) informacionConsulta.get("fecha_ingreso"));
        usuario.setActivo((Boolean) informacionConsulta.get("activo"));
        usuario.setNuevo((Boolean) informacionConsulta.get("nuevo"));
        usuario.setEnSesion((String) informacionConsulta.get("en_sesion"));
        usuario.setUltimoAcceso((Date) informacionConsulta.get("ultimo_acceso"));
        usuario.setNumeroIntentos((Integer) informacionConsulta.get("numero_intentos"));
        usuario.setBloqueado((String) informacionConsulta.get("bloqueado"));
        usuario.setFechaBloqueo((Date) informacionConsulta.get("fecha_bloqueo"));
        usuario.setPrimeraVez((String) informacionConsulta.get("primera_vez"));
        usuario.setEstatus((String) informacionConsulta.get("estatus"));
        usuario.setNivel((String) informacionConsulta.get("nivel"));
        usuario.setRfc((String) informacionConsulta.get("rfc"));
        usuario.setIdUnidad((Integer)informacionConsulta.get("id_unidad"));
        usuario.setNombreUnidad((String)informacionConsulta.get("nombre_unidad"));  
        return usuario;
	}
	
	@Override
	public List<UsuarioDto> obtenerListaJefes() {
		StringBuilder qry = new StringBuilder();
        qry.append("select distinct(nombre_jefe) as nombre_jefe ");
        qry.append("from m_usuario ");
        qry.append("where nombre_jefe is not null");
        
        List<Map<String, Object>> jefes = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioDto> listaUsuarios = new ArrayList<>();
        
        for (Map<String, Object> j : jefes) {
        	UsuarioDto usuario = new UsuarioDto();
    		usuario.setNombre((String) j.get("nombre_jefe"));
    		listaUsuarios.add(usuario);
    	}
        
        return listaUsuarios;
	}
	
	
}
