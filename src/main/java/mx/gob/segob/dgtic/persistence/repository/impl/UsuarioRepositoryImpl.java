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

import mx.gob.segob.dgtic.business.service.HorarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.PerfilRepository;
import mx.gob.segob.dgtic.persistence.repository.UsuarioRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Service
public class UsuarioRepositoryImpl extends RecursoBase implements UsuarioRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Autowired
	private HorarioService horarioService;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	
	@Override
	public List<UsuarioDto> obtenerListaUsuarios() {
		StringBuilder qry = new StringBuilder();
        qry.append("select usuario.id_usuario, usuario.id_area, usuario.cve_c_perfil, usuario.id_horario, usuario.id_puesto, usuario.cve_m_usuario, usuario.nombre, usuario.apellido_paterno, usuario.apellido_materno, usuario.fecha_ingreso, usuario.activo, usuario.nuevo, usuario.en_sesion, usuario.ultimo_acceso, usuario.numero_intentos, usuario.bloqueado, usuario.fecha_bloqueo,  usuario.primera_vez, usuario.estatus, usuario.nivel, usuario.rfc, unidad.id_unidad, unidad.nombre nombre_unidad ");
        qry.append("from m_usuario usuario, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where unidad.id_unidad=relacion.id_unidad and usuario.cve_m_usuario=relacion.cve_m_usuario ");
        
        List<Map<String, Object>> usuarios = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioDto> listaUsuario = new ArrayList<>();
        
        for (Map<String, Object> usuario : usuarios) {
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioDto.setIdUsuario((Integer)usuario.get("id_usuario"));
    		usuarioDto.setIdArea((Integer)usuario.get("id_area"));
    		//usuarioDto.setClavePerfil(PerfilDto)usuario.get("cve_c_perfil"));
    		usuarioDto.setIdPuesto((String)usuario.get("id_puesto"));
    		usuarioDto.setClaveUsuario((String)usuario.get("cve_m_usuario"));
    		usuarioDto.setNombre((String)usuario.get("nombre"));
    		usuarioDto.setApellidoPaterno((String)usuario.get("apellido_paterno"));
    		usuarioDto.setApellidoMaterno((String)usuario.get("apellido_materno"));
    		usuarioDto.setFechaIngreso((Date)usuario.get("fecha_ingreso"));
    		//usuarioDto.setPassword((String)usuario.get("password"));
    		usuarioDto.setActivo((Boolean)usuario.get("activo"));
    		usuarioDto.setNuevo((Boolean)usuario.get("nuevo"));
    		usuarioDto.setEnSesion((String)usuario.get("en_sesion"));
    		usuarioDto.setUltimoAcceso((Date)usuario.get("ultimo_acceso"));
    		usuarioDto.setNumeroIntentos((Integer)usuario.get("numero_intentos"));
    		usuarioDto.setBloqueado((String)usuario.get("bloqueado"));
    		usuarioDto.setActivo((Boolean)usuario.get("activo"));
    		usuarioDto.setFechaBloqueo((Date)usuario.get("fecha_bloqueo"));
    		usuarioDto.setPrimeraVez((String)usuario.get("primera_vez"));
    		usuarioDto.setEstatus((String)usuario.get("estatus"));
    		usuarioDto.setNivel((String)usuario.get("nivel"));
    		usuarioDto.setRfc((String)usuario.get("rfc"));
    		usuarioDto.setNombreUnidad((String)usuario.get("nombre_unidad"));
    		usuarioDto.setIdUnidad((Integer)usuario.get("id_unidad"));
    		listaUsuario.add(usuarioDto);
    	}
        
        logger.info(listaUsuario.size() + " usuarios encontrados.");
        
		return listaUsuario;
	}

	@Override
	public UsuarioDto buscaUsuario(String claveUsuario) {
		
        StringBuilder qry = new StringBuilder();
        qry.append("select u.id_usuario, u.id_area, u.cve_c_perfil, u.id_horario, u.id_puesto, u.cve_m_usuario, ");
        qry.append("u.nombre, u.apellido_paterno, u.apellido_materno, u.fecha_ingreso, u.activo, u.nuevo, u.en_sesion, ");
        qry.append("u.ultimo_acceso, u.numero_intentos, u.bloqueado, u.fecha_bloqueo, u.primera_vez, u.estatus, u.nivel, u.rfc, ");
        qry.append("p.descripcion, p.estatus, unidad.id_unidad, ");
        qry.append("h.hora_entrada, h.hora_salida ");
        qry.append("from m_usuario u, c_perfil p, c_horario h, c_unidad_administrativa unidad, usuario_unidad_administrativa relacion ");
        qry.append("where u.cve_m_usuario = :claveUsuario ");
        qry.append("and u.cve_c_perfil = p.cve_c_perfil ");
        qry.append("and u.id_horario = h.id_horario and unidad.id_unidad=relacion.id_unidad and u.cve_m_usuario=relacion.cve_m_usuario limit 1");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveUsuario", claveUsuario);
        
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
//        usuario.setIdArea(area);
        usuario.setClavePerfil(perfil);
        usuario.setIdHorario(horario);
//        usuario.setIdPuesto(puesto);
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
        
        return usuario;
	}

	@Override
	public void modificaUsuario(UsuarioDto usuarioDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("update m_usuario set id_area = :idArea, id_horario = :idHorario, "
				+ "password = :password, activo = :activo, "
				+ "nuevo =: nuevo, en_sesion =: enSesion, ultimo_acceso =: ultimoAcceso, numero_intentos = :numeroIntentos, bloqueado = :bloqueado, "
				+ "fecha_bloqueo = :fechaBloqueo, primera_vez = :primeraVez, estatus = :estatus ");
		qry.append("WHERE cve_m_usuario = :claveUsuario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArea", usuarioDto.getIdArea());
		//parametros.addValue("clavePerfil", usuarioDto.getClavePerfil().getClavePerfil());
		parametros.addValue("idHorario", usuarioDto.getIdHorario().getIdHorario());
		//parametros.addValue("idPuesto", usuarioDto.getIdPuesto());
		parametros.addValue("claveUsuario", usuarioDto.getClaveUsuario());
		//parametros.addValue("nombre", usuarioDto.getNombre());
		//parametros.addValue("apellidoPaterno", usuarioDto.getApellidoPaterno());
		//parametros.addValue("apellidoMaterno", usuarioDto.getApellidoMaterno());
		//parametros.addValue("fechaIngreso", usuarioDto.getFechaIngreso());
		parametros.addValue("password", usuarioDto.getPassword());
		parametros.addValue("activo", usuarioDto.getActivo());
		parametros.addValue("nuevo", usuarioDto.getNuevo());
		parametros.addValue("enSesion", usuarioDto.getEnSesion());
		parametros.addValue("ultimoAcceso", usuarioDto.getUltimoAcceso());
		parametros.addValue("NumeroIntentos", usuarioDto.getNumeroIntentos());
		parametros.addValue("bloqueado", usuarioDto.getBloqueado());
		parametros.addValue("fechaBloqueo", usuarioDto.getFechaBloqueo());
		parametros.addValue("primeraVez", usuarioDto.getPrimeraVez());
		parametros.addValue("estatus", usuarioDto.getEstatus());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}

	@Override
	public void agregaUsuario(UsuarioDto usuarioDto) {
		
		StringBuilder qry = new StringBuilder();
		System.out.println(" numero de intentos"+usuarioDto.getNumeroIntentos());
		qry.append("insert into m_usuario (id_area, cve_c_perfil, id_horario, id_puesto, cve_m_usuario, nombre, apellido_paterno, apellido_materno, "
        		+ "fecha_ingreso, password, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos, bloqueado, fecha_bloqueo,  primera_vez, estatus, "
        		+ "nivel, rfc, nombre_jefe) ");
		qry.append("values ( :idArea, :clavePerfil, :idHorario, :idPuesto, :claveUsuario, :nombre, :apellidoPaterno, :apellidoMaterno, :fechaIngreso, "
				+ ":password, :activo, :nuevo, :enSesion, :ultimoAcceso, :numeroIntentos, :bloqueado, :fechaBloqueo, :primeraVez, :estatus, "
				+ ":nivel, :rfc, :nombreJefe ) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArea", usuarioDto.getIdArea());
		parametros.addValue("clavePerfil", usuarioDto.getClavePerfil().getClavePerfil());
		parametros.addValue("idHorario", usuarioDto.getIdHorario().getIdHorario());
		parametros.addValue("idPuesto", usuarioDto.getIdPuesto());
		parametros.addValue("claveUsuario", usuarioDto.getClaveUsuario());
		parametros.addValue("nombre", usuarioDto.getNombre());
		parametros.addValue("apellidoPaterno", usuarioDto.getApellidoPaterno());
		parametros.addValue("apellidoMaterno", usuarioDto.getApellidoMaterno());
		parametros.addValue("fechaIngreso", usuarioDto.getFechaIngreso());
		parametros.addValue("password", usuarioDto.getPassword());
		parametros.addValue("activo", usuarioDto.getActivo());
		parametros.addValue("nuevo", usuarioDto.getNuevo());
		parametros.addValue("enSesion", usuarioDto.getEnSesion());
		parametros.addValue("ultimoAcceso", usuarioDto.getUltimoAcceso());
		parametros.addValue("numeroIntentos", usuarioDto.getNumeroIntentos());
		parametros.addValue("bloqueado", usuarioDto.getBloqueado());
		parametros.addValue("fechaBloqueo", usuarioDto.getFechaBloqueo());
		parametros.addValue("primeraVez", usuarioDto.getPrimeraVez());
		parametros.addValue("estatus", usuarioDto.getEstatus());
		parametros.addValue("nivel", usuarioDto.getNivel());
		parametros.addValue("rfc", usuarioDto.getRfc());
		parametros.addValue("nombreJefe", usuarioDto.getNombreJefe());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public void eliminaUsuario(String claveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("delete from m_usuario where cve_m_usuario = :claveUsuario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", claveUsuario);
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
		qry.append("update m_usuario set primera_vez ='S' WHERE cve_m_usuario = :claveUsuario  ");
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", claveUsuario);
		nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}

	@Override
	public List<UsuarioDto> obtenerListaUsuariosActivos(String fecha) {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_usuario, id_area, cve_c_perfil, id_horario, id_puesto,"
        		+ " cve_m_usuario, nombre, apellido_paterno, apellido_materno, "
        		+ "fecha_ingreso, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos,"
        		+ " bloqueado, fecha_bloqueo,  primera_vez, estatus ");
        qry.append("from m_usuario ");
        qry.append(" where activo = 1 ");
        qry.append(" and fecha_ingreso <= :fecha");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("fecha", fecha);
        
        List<Map<String, Object>> usuarios = jdbcTemplate.queryForList(qry.toString());
        List<UsuarioDto> listaUsuario = new ArrayList<>();
        
        for (Map<String, Object> usuario : usuarios) {
    		UsuarioDto usuarioDto = new UsuarioDto();
    		usuarioDto.setIdUsuario((Integer)usuario.get("id_usuario"));
    		usuarioDto.setIdArea((Integer)usuario.get("id_area"));
    		//usuarioDto.setIdIncidencia ((Integer)usuario.get("id_incidencia"));
    		//usuarioDto.setClavePerfil(PerfilDto)usuario.get("cve_c_perfil"));
    		usuarioDto.setIdPuesto((String)usuario.get("id_puesto"));
    		usuarioDto.setClaveUsuario((String)usuario.get("cve_m_usuario"));
    		usuarioDto.setNombre((String)usuario.get("nombre"));
    		usuarioDto.setApellidoPaterno((String)usuario.get("apellido_paterno"));
    		usuarioDto.setApellidoMaterno((String)usuario.get("apellido_materno"));
    		usuarioDto.setFechaIngreso((Date)usuario.get("fecha_ingreso"));
    		//usuarioDto.setPassword((String)usuario.get("password"));
    		usuarioDto.setActivo((Boolean)usuario.get("activo"));
    		usuarioDto.setNuevo((Boolean)usuario.get("nuevo"));
    		usuarioDto.setEnSesion((String)usuario.get("en_sesion"));
    		usuarioDto.setUltimoAcceso((Date)usuario.get("ultimo_acceso"));
    		usuarioDto.setNumeroIntentos((Integer)usuario.get("numero_intentos"));
    		usuarioDto.setBloqueado((String)usuario.get("bloqueado"));
    		usuarioDto.setActivo((Boolean)usuario.get("activo"));
    		usuarioDto.setFechaBloqueo((Date)usuario.get("fecha_bloqueo"));
    		usuarioDto.setPrimeraVez((String)usuario.get("primera_vez"));
    		usuarioDto.setEstatus((String)usuario.get("estatus"));
    		//usuarioDto.setRfc((String)usuario.get("rfc"));
    		//usuarioDto.setNivel((String)usuario.get("nivel"));
    		listaUsuario.add(usuarioDto);
    	}
        
        logger.info(listaUsuario.size() + " usuarios encontrados.");
        
		return listaUsuario;
	}
	
	
}
