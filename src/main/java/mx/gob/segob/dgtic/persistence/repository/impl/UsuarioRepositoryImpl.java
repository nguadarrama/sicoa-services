package mx.gob.segob.dgtic.persistence.repository.impl;

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
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
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
	
	
	@Override
	public List<UsuarioDto> obtenerListaUsuarios() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_usuario, id_area, cve_c_perfil, id_horario, id_puesto, cve_m_usuario, nombre, apellido_paterno, apellido_materno, "
        		+ "fecha_ingreso, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos, bloqueado, fecha_bloqueo,  primera_vez, estatus ");
        qry.append("FROM m_usuario ");
        
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
    		listaUsuario.add(usuarioDto);
    	}
        
        logger.info(listaUsuario.size() + " usuarios encontrados.");
        
		return listaUsuario;
	}

	@Override
	public UsuarioDto buscaUsuario(String claveUsuario) {
		
		
        StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_usuario, id_area, cve_c_perfil, id_horario, id_puesto, cve_m_usuario, nombre, apellido_paterno, apellido_materno, "
        		+ "fecha_ingreso, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos, bloqueado, fecha_bloqueo,  primera_vez, estatus ");
        qry.append("FROM m_usuario ");
        qry.append("WHERE cve_m_usuario = :claveUsuario");
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("claveUsuario", claveUsuario);
        
        UsuarioDto usuario = nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<UsuarioDto>(UsuarioDto.class));
        
        //busca el horario del usuario y se lo setea
        /*if (usuario.getIdHorario() != null && usuario.getIdHorario().getIdHorario() != null) { 
	    	Horario horario = horarioService.buscaHorario(usuario.getIdHorario().getIdHorario());
	    	usuario.getIdHorario().setHoraEntrada(horario.getHoraEntrada());
	        usuario.getIdHorario().setHoraSalida(horario.getHoraSalida());
        } else {
        	logger.info("Usuario: " + usuario.getClaveUsuario() + " no cuenta con id_horario");
        }*/
        
        return usuario;
		
	}

	@Override
	public void modificaUsuario(UsuarioDto usuarioDto) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_usuario SET id_area = :idArea, cve_c_perfil = :clavePerfil, id_horario = :idHorario, id_puesto = :idPuesto, nombre = :nombre, "
				+ "apellido_paterno = : apellidoPaterno, apellido_materno =: apellidoMaterno, fecha_ingreso =: fechaIngreso, password =: password, activo =:activo, "
				+ "nuevo =: nuevo, en_sesion =: enSesion, ultimo_acceso =: ultimoAcceso, numero_intentos =: numeroIntentos, bloqueado =: bloqueado, "
				+ "fecha_bloqueo =: fechaBloqueo, primera_vez =: primeraVez, estatus =: estatus");
		qry.append("WHERE cve_m_usuario = :claveUsuario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArea", usuarioDto.getIdArea());
		//parametros.addValue("clavePerfil", usuarioDto.getClavePerfil().getClavePerfil());
		//parametros.addValue("idHorario", usuarioDto.getIdHorario().getIdHorario());
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
		qry.append("INSERT INTO m_usuario (id_area, cve_c_perfil, id_horario, id_puesto, cve_m_usuario, nombre, apellido_paterno, apellido_materno, "
        		+ "fecha_ingreso, password, activo, nuevo, en_sesion, ultimo_acceso, numero_intentos, bloqueado, fecha_bloqueo,  primera_vez, estatus) ");
		qry.append("VALUES ( :idArea, :clavePerfil, :idHorario, :idPuesto, :claveUsuario, :nombre, :apellidoPaterno, :apellidoMaterno, :fechaIngreso, "
				+ ":password, :activo, :nuevo, :enSesion, :ultimoAcceso, :numeroIntentos, :bloqueado, :fechaBloqueo, :primeraVez, :estatus) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idArea", usuarioDto.getIdArea());
		parametros.addValue("clavePerfil", usuarioDto.getClavePerfil());
		parametros.addValue("idHorario", usuarioDto.getIdHorario());
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
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public void eliminaUsuario(String claveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM m_usuario WHERE cve_m_usuario = :claveUsuario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("claveUsuario", claveUsuario);
		nameParameterJdbcTemplate.update(qry.toString(), parametros);	
	}

	@Override
	public String consultaContrasenia(String claveUsuario) {
	StringBuilder qry = new StringBuilder();
	UsuarioDto usuarioDto=null;
	       qry.append("SELECT password ");
	       qry.append("FROM m_usuario ");
	       qry.append("WHERE cve_m_usuario = :claveUsuario");
	       MapSqlParameterSource parametros = new MapSqlParameterSource();
	       parametros.addValue("claveUsuario", claveUsuario);
	       
	       usuarioDto= nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<UsuarioDto>(UsuarioDto.class));
	       return usuarioDto.getPassword();
	}
	
}
