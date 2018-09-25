package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.PerfilDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.persistence.repository.CargaInicialRepository;
import mx.gob.segob.dgtic.persistence.repository.HorarioRepository;
import mx.gob.segob.dgtic.persistence.repository.PerfilRepository;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;

@Repository
public class CargaInicialRepositoryImpl extends RecursoBase implements CargaInicialRepository{
	
	@Autowired
	@Qualifier("jdbcTemplateOracle")
	private JdbcTemplate jdbcTemplateOracle;
	
	
	
	DateFormat formatoFecha = new SimpleDateFormat("yyyy/mm/dd");

	@Override
	public List<UsuarioDto> recuperarUsuariosCargaInicial() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT NUMERO_EMPLEADO, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, FECHA_ING_SECRETARIA, PUESTO_EMPLEADO ");
        qry.append("FROM VW_SEGOB_W00_ACT ");
        
        List<Map<String, Object>> usuarios = jdbcTemplateOracle.queryForList(qry.toString());
        List<UsuarioDto> listaUsuario = new ArrayList<>();
        //PerfilDto perfilDto= new PerfilDto();
        //perfilDto=perfilRepository.buscaPerfil("CVE000001");
        //Log.info("dato recuperado "+perfilDto.getClavePerfil());
        //Horario horarioDto=new Horario(); 
        //horarioDto=horarioRepository.buscaHorario(1);
        for (Map<String, Object> usuario : usuarios) {
    		UsuarioDto usuarioDto=new UsuarioDto();
    		//
    		//System.out.println("numeroEmpleaso"+usuario.get("NUMERO_EMPLEADO"));
    		usuarioDto.setClaveUsuario((String)usuario.get("NUMERO_EMPLEADO"));
    		usuarioDto.setNombre((String)usuario.get("NOMBRE"));
    		usuarioDto.setApellidoPaterno((String)usuario.get("APELLIDO_PATERNO"));
    		usuarioDto.setApellidoMaterno((String)usuario.get("APELLIDO_MATERNO"));
    		usuarioDto.setFechaIngreso((Timestamp)usuario.get("FECHA_ING_SECRETARIA"));
    		usuarioDto.setClavePerfil("1");
    		usuarioDto.setPassword((String)usuario.get("NUMERO_EMPLEADO"));
    		usuarioDto.setIdPuesto((String)usuario.get("PUESTO_EMPLEADO"));
    		//usuarioDto.setClavePerfil(perfilDto);
    		usuarioDto.setEnSesion("N");
    		usuarioDto.setBloqueado("N");
    		usuarioDto.setPrimeraVez("Y");
    		usuarioDto.setEstatus("A");
    		//usuarioDto.setIdHorario(horarioDto);
    		usuarioDto.setNumeroIntentos(0);
    		String formatter = formatoFecha.format((usuario.get("FECHA_ING_SECRETARIA")));
    		Date date = null;
			try {
				date = formatoFecha.parse(formatter);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
    	    //String parsedDate = formatter.format((Date)usuario.get("FECHA_ING_SECRETARIA"));
    	    //System.out.println("fecha ingreso "+usuario.get("FECHA_ING_SECRETARIA"));
    	    //System.out.println("fecha ingreso "+formatter);
    		usuarioDto.setFechaIngreso(date);
    		logger.debug("clave ingreso "+usuarioDto.getClaveUsuario());
    		
    		//Log.debug("numeroEmpleado "+usuarioDto.getClaveUsuario());
    		listaUsuario.add(usuarioDto);
    	}
		return listaUsuario;
	}

}
