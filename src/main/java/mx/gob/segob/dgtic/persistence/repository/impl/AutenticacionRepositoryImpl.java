/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.Calendar;
import java.util.List;

import javax.security.auth.login.CredentialNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.transport.constants.DecisionEnum;
import mx.gob.segob.dgtic.comun.transport.constants.EstatusEnum;
import mx.gob.segob.dgtic.comun.transport.dto.autenticacion.UsuarioAcceso;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.AutenticacionRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;

/**
 * Implementaci&oacute;n de los m&eacute;todos que interact&uacute;an con las estructuras relacionadas con la autenticaci&oacute;n
 * 
 * @see mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase
 * @see mx.gob.segob.dgtic.persistence.repository.AutenticacionRepository
 */
@Repository
public class AutenticacionRepositoryImpl extends RepositoryBase implements AutenticacionRepository {
	
    /**
     * Template de acceso a datos por parametros de enlace de nombres 
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	/**
	 * Obtener la palabra clave asignada al usuario para su identificacion
	 *
	 * @param cveUsuario nombre del usuario a ingresar
	 * @return La palabra clave que tiene asignada como sus credenciales de acceso
	 * 
	 * 
	 * @throws CredentialNotFoundException Excepci&oacute;n lanzada cuando no existe un usuario con esa clave
	 */
    @Override
	public String obtenerPalabraClavePorUsuario(String cveUsuario) 
							throws CredentialNotFoundException{
    	logger.info("password en repository: {} ",cveUsuario);
    	 StringBuilder qry = new StringBuilder();
	     qry.append(" SELECT password ");
	     qry.append(" FROM m_usuario  ");
	     qry.append(" WHERE cve_m_usuario = :cveUsuario  ");
	     MapSqlParameterSource parametros = new MapSqlParameterSource();
	     parametros.addValue("cveUsuario", cveUsuario);
	     String palabraClave = null;
	     try {
	    	 palabraClave = jdbcTemplate.queryForObject( 
	 					qry.toString(),parametros, String.class);
	    	 logger.info("Se consulto en repository: {} ",cveUsuario);
	     } catch(EmptyResultDataAccessException exception){
	    	 throw new CredentialNotFoundException(); 
	     }	     
	     return palabraClave;
    }

	
	/**
	 * Obtener datos del usuario por clave.
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 * @return Los datos del usuario
	 */
	@Override
	public UsuarioAcceso obtenerUsuarioAccesoByCve(String cveUsuario){
		logger.info("Usuario de acceso en repository: {} ",cveUsuario);
		StringBuilder qry = new StringBuilder();
		qry.append(" SELECT cve_m_usuario, ");
		qry.append(" 	EN_SESION, ");
		qry.append(" 	ULTIMO_ACCESO, ");
		qry.append(" 	NUMERO_INTENTOS, ");
		qry.append(" 	BLOQUEADO, ");
		qry.append(" 	FECHA_BLOQUEO, ");
		qry.append(" 	PRIMERA_VEZ, ");
		qry.append("	ESTATUS ");
		qry.append(" FROM m_usuario  ");
		qry.append(" WHERE cve_m_usuario = :cveUsuario  ");

		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("cveUsuario", cveUsuario);

		UsuarioAcceso usuarioAcceso = null;
		try {
			usuarioAcceso =jdbcTemplate.queryForObject( qry.toString(), 
										parametros, 
										new RowAnnotationBeanMapper<UsuarioAcceso>(UsuarioAcceso.class)); 
			logger.info("Se realizó consulta de usuario en repository: {} ",cveUsuario);
		}  catch(EmptyResultDataAccessException exception){
			usuarioAcceso = null; 
		}
		return usuarioAcceso; 
	}
	
	/**
	 * Registra intento acceso.
	 * 
	 * <p> Actualiza el numero de intento fallido realizado con esa clave de usuario para posterior consulta
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 * @param numeroIntento el n&uacute;mero de intento al cual se actualizara la informacion del usuario
	 */
	@Override
	public void registraIntentoAcceso(String cveUsuario, Integer numeroIntento) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_usuario  SET numero_intentos = :numeroIntentos ");
		qry.append("WHERE cve_m_usuario = :cveUsuarioAcceso");
		logger.info("Se registra intentos de acceso: {} ",cveUsuario);
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("numeroIntentos", numeroIntento);
		parametros.addValue("cveUsuarioAcceso", cveUsuario);
		 

		 jdbcTemplate.update(qry.toString(), parametros);		
	}

	/**
	 * Bloquear usuario acceso.
	 * <p>
	 * Proceso que cambia a bloqueado la bandera que identifica el bloqueo de un usuario
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 */
	@Override
	public void bloquearUsuarioAcceso(String cveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_usuario SET bloqueado = :bloqueado, ");
		qry.append("    fecha_bloqueo = :fechaBloqueo ");
		qry.append("WHERE cve_m_usuario = :cveUsuarioBloqueo");
		logger.info("Se bloquea Usuario en repository: {} ",cveUsuario);
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("bloqueado", DecisionEnum.S.toString());
		parametros.addValue("fechaBloqueo", Calendar.getInstance().getTime());
		parametros.addValue("cveUsuarioBloqueo", cveUsuario);
		 

		 jdbcTemplate.update(qry.toString(), parametros);		
	}
	
	/**
	 * Desbloquear usuario acceso.
	 *	<p>
	 * Proceso que cambia a desbloqueado la bandera que identifica el bloqueo de un usuario
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 */
	@Override
	public void desbloquearUsuarioAcceso(String cveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_usuario SET BLOQUEADO = :desbloqueado, ");
		qry.append("    fecha_bloqueo = null, ");
		qry.append("    numero_intentos = 0 ");
		qry.append("WHERE cve_m_usuario = :cveUsuarioDesbloqueo");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("desbloqueado", DecisionEnum.N.toString());
		parametros.addValue("cveUsuarioDesbloqueo", cveUsuario);
		 

		 jdbcTemplate.update(qry.toString(), parametros);		
	}
	

	/**
	 * Registrar acceso usuario.
	 *	<p>
	 *	Proceso que actualiza la informaci&oacute;n de ingreso para el usuario
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 */
	@Override
	public void registrarAccesoUsuario(String cveUsuario) {
		
		StringBuilder qry = new StringBuilder();
		qry.append(" UPDATE m_usuario SET numero_intentos = 0,  ");
		qry.append(" 	ULTIMO_ACCESO = :ultimoAcceso, ");
		qry.append(" 	EN_SESION = :enSesion, ");
		qry.append(" 	BLOQUEADO = :bloqueado, ");
		qry.append(" 	FECHA_BLOQUEO = NULL ");
		qry.append(" WHERE cve_m_usuario = :cveUsuarioRegistro");
		logger.info("Se registra acceso del usuario en repository: {} ",cveUsuario);
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("enSesion", DecisionEnum.S.toString());
		parametros.addValue("bloqueado", DecisionEnum.N.toString());
		parametros.addValue("ultimoAcceso", Calendar.getInstance().getTime());
		parametros.addValue("cveUsuarioRegistro", cveUsuario);
		
		 jdbcTemplate.update(qry.toString(), parametros);		
	}
	
	
	/**
	 * Cerrar session.
	 * <p>
	 * Proceso que cambia los estados que identifican a un usuario con sesi&oacute;n iniciada a sesi&oacute;n cerrada.
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 */
	@Override
	public void cerrarSession(String cveUsuario) {
		StringBuilder qry = new StringBuilder();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		
		qry.append(" UPDATE  m_usuario SET EN_SESION = :enSesion ");
		qry.append(" WHERE cve_m_usuario = :cveUsuarioCierre");
		
		parametros.addValue("enSesion", DecisionEnum.N.toString());
		parametros.addValue("cveUsuarioCierre", cveUsuario);
		logger.info("Se cierra sesion en repository: {} ",cveUsuario);
		jdbcTemplate.update(qry.toString(), parametros);		
	}
	
	
	/**
	 * Anular session usuarios.
	 * <p>
	 * Proceso que cambia el estado de su sesion a todos los usuarios por cerrado 
	 */
	@Override
	public void anularSessionUsuarios() {		
		StringBuilder qry = new StringBuilder();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		
		qry.append(" UPDATE  m_usuario SET EN_SESION = :cierreSesion ");
		qry.append(" WHERE EN_SESION = :sesionActiva ");
		
		
		parametros.addValue("cierreSesion", DecisionEnum.N.toString());
		parametros.addValue("sesionActiva", DecisionEnum.S.toString());		
		
		jdbcTemplate.update(qry.toString(), parametros);
		
		logger.info("Cerrando session de usuarios");
	}

	/**
	 * Obtener perfiles usuario.
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 * @return Lista de perfiles activos asociados al usuario
	 */
	@Override
	public List<String> obtenerPerfilesUsuario(String cveUsuario) {
		StringBuilder qry = new StringBuilder();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		
		qry.append(" SELECT a.cve_c_perfil ");
		qry.append(" FROM c_perfil a ");
		qry.append(" INNER JOIN d_usuario_perfil b ON b.cve_c_perfil = a.cve_c_perfil ");
		qry.append(" WHERE b.cve_m_usuario = :cveUsuarioPerfiles ");
		qry.append(" AND a.estatus = :estatusPerfil ");
		
		parametros.addValue("cveUsuarioPerfiles",cveUsuario);
		parametros.addValue("estatusPerfil",EstatusEnum.A.toString());
		
		return jdbcTemplate.queryForList(qry.toString(), parametros, String.class );
	}

	/**
	 * Obtener permisos usuario.
	 *
	 * @param cveUsuario La clave &uacute;nica del usuario
	 * @return Lista de permisos activos asociados al usuario
	 */
	@Override
	public List<String> obtenerPermisosUsuario(String cveUsuario) {
		StringBuilder qry = new StringBuilder();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		
		qry.append(" SELECT a.cve_c_permiso ");
		qry.append(" FROM c_permiso a ");
		qry.append(" INNER JOIN d_perfil_permiso b ON b.cve_c_permiso = a.cve_c_permiso ");		
		qry.append(" INNER JOIN d_usuario_perfil c ON c.cve_c_perfil = b.cve_c_perfil ");
		qry.append(" INNER JOIN c_perfil d ON d.cve_c_perfil = c.cve_c_perfil ");
		qry.append(" WHERE c.cve_m_usuario = :cveUsuarioPermisos ");
		qry.append(" AND   a.estatus = :estatusPermiso ");
		qry.append(" AND   d.estatus = :estatusPermiso ");
		
		parametros.addValue("cveUsuarioPermisos",cveUsuario);
		parametros.addValue("estatusPermiso",EstatusEnum.A.toString());
		
		return jdbcTemplate.queryForList(qry.toString(), parametros, String.class );
	}


	@Override
	public Integer cambiarPassword(String password, String claveUsuario) {
		logger.info("actualizado: {} ",claveUsuario);
		logger.info("clave: {} ",password);
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE m_usuario SET password = :password, primera_vez='N' ");
		qry.append("WHERE cve_m_usuario = :claveUsuario");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("password", password);
		parametros.addValue("claveUsuario", claveUsuario);
		return nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
}
