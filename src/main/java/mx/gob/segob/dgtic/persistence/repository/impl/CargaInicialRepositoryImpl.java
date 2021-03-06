package mx.gob.segob.dgtic.persistence.repository.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.persistence.repository.CargaInicialRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Repository
public class CargaInicialRepositoryImpl extends RecursoBase implements CargaInicialRepository{
	
	@Autowired
	@Qualifier("jdbcTemplateOracle")
	private JdbcTemplate jdbcTemplateOracle;
	private static final String NUMERO_EMPLEADO = "NUMERO_EMPLEADO";
	private static final String FECHA_ING_SECRETARIA = "FECHA_ING_SECRETARIA";
	
	DateFormat formatoFecha = new SimpleDateFormat("yyyy/mm/dd");

	@Override
	public List<UsuarioDto> recuperarUsuariosCargaInicial() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT NUMERO_EMPLEADO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_ING_SECRETARIA, PUESTO_EMPLEADO, RFC, NIVEL, NOMBRE_JEFE ");
        qry.append("FROM APPS.VW_SEGOB_W00_ACT ");
        
        List<Map<String, Object>> usuarios = jdbcTemplateOracle.queryForList(qry.toString());
        List<UsuarioDto> listaUsuario = new ArrayList<>();
        PerfilDto perfilDto= new PerfilDto();
        perfilDto.setClavePerfil("1");

        Horario horarioDto=new Horario(); 
        horarioDto.setIdHorario(1);
        for (Map<String, Object> usuario : usuarios) {
    		UsuarioDto usuarioDto=new UsuarioDto();

    		usuarioDto.setClaveUsuario((String)usuario.get(NUMERO_EMPLEADO));
    		usuarioDto.setNombre((String)usuario.get("NOMBRE"));
    		usuarioDto.setApellidoPaterno((String)usuario.get("APELLIDO_PATERNO"));
    		usuarioDto.setApellidoMaterno((String)usuario.get("APELLIDO_MATERNO"));
    		usuarioDto.setFechaIngreso((Timestamp)usuario.get(FECHA_ING_SECRETARIA));
    		usuarioDto.setClavePerfil(perfilDto);
    		usuarioDto.setPassword((String)usuario.get(NUMERO_EMPLEADO));
    		usuarioDto.setIdPuesto((String)usuario.get("PUESTO_EMPLEADO"));
    		usuarioDto.setRfc((String)usuario.get("RFC"));
    		usuarioDto.setNivel((String)usuario.get("NIVEL"));
    		usuarioDto.setNombreJefe((String) usuario.get("NOMBRE_JEFE"));
    		usuarioDto.setEnSesion("N");
    		usuarioDto.setBloqueado("N");
    		usuarioDto.setPrimeraVez("Y");
    		usuarioDto.setEstatus("A");
    		usuarioDto.setIdHorario(horarioDto);
    		usuarioDto.setNumeroIntentos(0);
    		String formatter = formatoFecha.format((usuario.get(FECHA_ING_SECRETARIA)));
    		Date date = null;
			try {
				date = formatoFecha.parse(formatter);
			} catch (ParseException e) {
				logger.warn("Warn : {} ", e);
			}

    		usuarioDto.setFechaIngreso(date);
    		logger.debug("clave ingreso: {} ",usuarioDto.getClaveUsuario());

    		listaUsuario.add(usuarioDto);
    	}
		return listaUsuario;
	}

	@Override
	public UsuarioDto obtieneUsuarioPorCveMusuario(String cveMusuario) {
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT NUMERO_EMPLEADO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_ING_SECRETARIA, PUESTO_EMPLEADO, RFC, NIVEL, NOMBRE_JEFE ");
        qry.append("FROM APPS.VW_SEGOB_W00_ACT ");
        qry.append("WHERE NUMERO_EMPLEADO = '" + cveMusuario + "' ");
        
        List<Map<String, Object>> usuarios = jdbcTemplateOracle.queryForList(qry.toString());
        List<UsuarioDto> listaUsuario = new ArrayList<>();
        PerfilDto perfilDto= new PerfilDto();
        perfilDto.setClavePerfil("1");
        Horario horarioDto=new Horario(); 
        horarioDto.setIdHorario(1);
        for (Map<String, Object> usuario : usuarios) {
    		UsuarioDto usuarioDto=new UsuarioDto();
    		usuarioDto.setClaveUsuario((String)usuario.get(NUMERO_EMPLEADO));
    		usuarioDto.setNombre((String)usuario.get("NOMBRE"));
    		usuarioDto.setApellidoPaterno((String)usuario.get("APELLIDO_PATERNO"));
    		usuarioDto.setApellidoMaterno((String)usuario.get("APELLIDO_MATERNO"));
    		usuarioDto.setFechaIngreso((Timestamp)usuario.get(FECHA_ING_SECRETARIA));
    		usuarioDto.setClavePerfil(perfilDto);
    		usuarioDto.setPassword((String)usuario.get(NUMERO_EMPLEADO));
    		usuarioDto.setIdPuesto((String)usuario.get("PUESTO_EMPLEADO"));
    		usuarioDto.setRfc((String)usuario.get("RFC"));
    		usuarioDto.setNivel((String)usuario.get("NIVEL"));
    		usuarioDto.setNombreJefe((String) usuario.get("NOMBRE_JEFE"));
    		usuarioDto.setEnSesion("N");
    		usuarioDto.setBloqueado("N");
    		usuarioDto.setPrimeraVez("Y");
    		usuarioDto.setEstatus("A");
    		usuarioDto.setIdHorario(horarioDto);
    		usuarioDto.setNumeroIntentos(0);
    		String formatter = formatoFecha.format((usuario.get(FECHA_ING_SECRETARIA)));
    		Date date = null;
			try {
				date = formatoFecha.parse(formatter);
			} catch (ParseException e) {
				logger.warn("Warn. {} ", e);
			}
    		usuarioDto.setFechaIngreso(date);
    		logger.debug("clave ingreso: {} ",usuarioDto.getClaveUsuario());
    		
    		listaUsuario.add(usuarioDto);
    	}
        
        if (!listaUsuario.isEmpty()) {
        	return listaUsuario.get(0);
        } else {
        	return null;
        }
	}

}
