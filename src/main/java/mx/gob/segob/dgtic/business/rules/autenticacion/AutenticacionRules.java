/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.business.rules.autenticacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.segob.dgtic.comun.transport.constants.EstatusEnum;
import mx.gob.segob.dgtic.business.rules.catalogo.UsuarioRules;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.transport.constants.DecisionEnum;
import mx.gob.segob.dgtic.comun.transport.dto.autenticacion.UsuarioAcceso;
import mx.gob.segob.dgtic.comun.util.config.AplicacionPropertiesUtil;
import mx.gob.segob.dgtic.comun.util.crypto.HashUtils;
import mx.gob.segob.dgtic.persistence.repository.AutenticacionRepository;

/**
 * Reglas de negocio aplicadas a la autenticaci&oacute;n de usuarios.
 */
@Component 
public class AutenticacionRules {
	
	/** Constante que representa USUARIO_NO_EXISTE, Mensaje de respuesta en servicio de autenticaci&oacute;n. */
	private static final String USUARIO_NO_EXISTE = "El usuario o contraseña es incorrecto, por favor intenta de nuevo.";
	
	/**  Constante que representa CREDENCIALES_INCORRECTAS, Mensaje de respuesta en servicio de autenticaci&oacute;n. */
	private static final String CREDENCIALES_INCORRECTAS = "El usuario o contraseña es incorrecto, por favor intenta de nuevo.";
	
	/**  Constante que representa USUARIO_BLOQUEADO, Mensaje de respuesta en servicio de autenticaci&oacute;n. */
	private static final String USUARIO_BLOQUEADO = "Usuario bloqueado, vuelva intentarlo en unos minutos";
	
	/**  Constante que representa USUARIO_INACTIVO, Mensaje de respuesta en servicio de autenticaci&oacute;n. */
	private static final String USUARIO_INACTIVO = "El usuario no se encuntra activo";
	
	/**  Constante que representa USUARIO_SESSION_ACTIVA, Mensaje de respuesta en servicio de autenticaci&oacute;n. */
	private static final String USUARIO_SESSION_ACTIVA = "Ya existe una session activa";
	
	/**  Constante que representa CONFIGURACION_AUTENTICACION_MINUTOS_BLOQUEADO, variable de las propiedades de configuraci&oacute;n de la aplicaci&oacute;n ({@code aplicacion.properties} : {@code autenticacion.minutos_bloqueo} ) . */
	private static final String CONFIGURACION_AUTENTICACION_MINUTOS_BLOQUEADO = "autenticacion.minutos_bloqueo";
	
	/**  Constante que representa CONFIGURACION_USO_MULTISESSION, variable de las propiedades de configuraci&oacute;n de la aplicaci&oacute;n ({@code aplicacion.properties} : {@code autenticacion.multiple_sesion} ) . */
	private static final String CONFIGURACION_USO_MULTISESSION = "autenticacion.multiple_sesion";
	
	/**  Constante que representa CONFIG_AUT_INTENTOS_ACCESO_PERMITIDO, variable de las propiedades de configuraci&oacute;n de la aplicaci&oacute;n ({@code aplicacion.properties} : {@code autenticacion.numero_intentos_bloqueo} ) . */
	private static final String CONFIG_AUT_INTENTOS_ACCESO_PERMITIDO = "autenticacion.numero_intentos_bloqueo";
	
	
	/**
	 * Repositorio de datos para la autenticaci&oacute;n de usuario.
	 */
	@Autowired
	private AutenticacionRepository repositorioAutenticacion;
	
	@Autowired 
	private UsuarioRules usuarioRules;
		
	/**
	 * Valida las reglas de negocio para la autentiaci&oacute;n.
	 * 
	 * <p>
	 * 	Claves posibles que se devolver&aacute;n seg&uacute;n evaluaci&oacute;n de usuario
	 * 	<ul>	
	 * 		<li><em>USUARIO_NO_EXISTE</em> : Indica que no existe registro en BD para ese nombre de usuario (depende de la bandera {@code usuarioNoExiste}).</li>
	 * 		<li><em>CREDENCIALES_INCORRECTAS</em> : Indica que la palabra clave no corresponde al usuario registrado en la BD.</li>
	 * 		<li>
	 * 			<em>USUARIO_BLOQUEADO</em> : Indica que el usuario a superado el n&uacute;mero de intentos de acceso permitidos, 
	 * 			definido por el par&aacute;metro de configuraci&oacute;n  {@code aplicacion.properties} : {@code autenticacion.numero_intentos_bloqueo}, 
	 * 			el valor -1 no restringe a un n&uacute;mero de intentos.
	 *		</li>
	 * 		<li><em>USUARIO_INACTIVO</em> : Indica que el usuario se encuentra inactivo en su registro de BD.</li>
	 * 		<li><em>USUARIO_SESSION_ACTIVA</em> : Indica que el usuario ya tiene una sesi&oacute;n activa en BD, este error solo aplica si el par&aacute;metro de configuraci&oacute;n  {@code aplicacion.properties} : {@code autenticacion.multiple_sesion} es true.</li>
	 *  </ul> 
	 *  
	 * @param usuario El usuario que se evaluara sus estados
	 * @param contraseniaAsignadaUsuarioBD Palabra clave asignada al usuario, mediante el cual se identifican sus credenciales.
	 * @param credencialAVerificar Palabra clave que se recibio y que se verificara que corresponda al usuario
	 * 
	 * @return Lista de claves de error de la evaluaci&oacute;n del usuario
	 */
	public List<String> validaAutenticacionRules(UsuarioAcceso usuario, String contraseniaAsignadaUsuarioBD, String credencialAVerificar) {
		System.out.println("contraseniaAsignadaUsuarioBD "+contraseniaAsignadaUsuarioBD);
		List<String> errores = new ArrayList<>(0);
		if(usuario == null){
				errores.add(USUARIO_NO_EXISTE);
		} else {
			String hashCredencialAVerificar = HashUtils.md5(credencialAVerificar);
			if(esUsuarioBloqueado(usuario)){
				errores.add(USUARIO_BLOQUEADO);
			} else if(StringUtils.isBlank(credencialAVerificar) || 
					hashCredencialAVerificar.compareTo(contraseniaAsignadaUsuarioBD) != 0){
				errores.add(CREDENCIALES_INCORRECTAS);
			} else if(esUsuarioInactivo(usuario)){
				errores.add(USUARIO_INACTIVO);
			}  else if(!permitirAutenticacionMultisesion(usuario)){
				errores.add(USUARIO_SESSION_ACTIVA);
			}			
		}
		return errores;
	}
	
	/**
	 * Evalualaci&oacute;n de regla de negocio para indicar si se deber&aacute; bloquear un usuario. En base a intentos de autenticaci&oacute;n
	 * 
	 * <p>
	 * Se eval&uacute;a el n&uacute;mero de intentos a permitir antes de bloquear a un usuario. El par&aacute;metro de configuraci&oacute;n que indica cuantos intentos se permiten 
	 * est&aacute; definido en {@code aplicacion.properties} : {@code autenticacion.numero_intentos_bloqueo}.
	 * <p>
	 * El valor -1 indica que no hay un l&iacute;mite de intentos antes de bloquear un usuario.
	 * 
	 * @param numeroIntentoAcceso El intento de acceso actual a evaluar.
	 * 
	 * @return true, Si se debe bloquear al usuario.
	 */
	public boolean evaluaBloquearUsuario(Integer numeroIntentoAcceso){
		
		String configAutIntentosAccesoPermitido = AplicacionPropertiesUtil.getPropiedades()
													.obtenerPropiedad(CONFIG_AUT_INTENTOS_ACCESO_PERMITIDO); 
		
		Integer intentosPermitidos = -1;
		if(NumberUtils.isParsable(configAutIntentosAccesoPermitido)){
			intentosPermitidos = Integer.parseInt(configAutIntentosAccesoPermitido);
		}
		
		boolean bloquearUsuario = Boolean.FALSE;
		if( intentosPermitidos > 0 && 
			numeroIntentoAcceso >= intentosPermitidos ){					
				bloquearUsuario = Boolean.TRUE;
		}
		return bloquearUsuario;
	}
	
	/**
	 * Eval&uacute;a el estado del usuario para indicar si se encuentra bloqueado o no.
	 * 
	 * <p>
	 * Se toma en cuenta la bandera BLOQUEADO y el tiempo que ha pasado desde que se realiz&oacute; el bloqueo para permitir el paso.
	 * 
	 * <p>
	 * <b><i>Nota: </i></b> El bloqueo solo se realiza por los minutos definidos en el par&aacute;metro de configuraci&oacute;n {@code aplicacion.properties} : {@code autenticacion.minutos_bloqueo}. Antes de permitir nuevamente un acceso. 
	 *
	 * @param usuario El usuario que se evaluara su estado.
	 * @return true, si el usuario se encuentra bloqueado.
	 */
	private boolean esUsuarioBloqueado(UsuarioAcceso usuario){
		
		boolean bloqueado = Boolean.FALSE;
		Integer duracionMinutosBloqueo = Integer.parseInt( 
													AplicacionPropertiesUtil.getPropiedades()
																			.obtenerPropiedad(CONFIGURACION_AUTENTICACION_MINUTOS_BLOQUEADO));
		
		if( usuario.getBloqueado() != null && 
			usuario.getBloqueado().equals(DecisionEnum.S)){
			
			bloqueado = Boolean.TRUE;
			if(duracionMinutosBloqueo > 0) {
				Date fechaBloqueo = usuario.getFechaBloqueado();
				Calendar tiempoDesbloqueo = Calendar.getInstance();
				tiempoDesbloqueo.setTime(fechaBloqueo);
				tiempoDesbloqueo.add(Calendar.MINUTE, duracionMinutosBloqueo);
				
				Calendar tiempoActual = Calendar.getInstance();
				if(tiempoActual.after(tiempoDesbloqueo)){
					bloqueado = Boolean.FALSE;	
					usuario.setNumeroIntentos(0);
					repositorioAutenticacion.desbloquearUsuarioAcceso(usuario.getClaveUsuario());
				}
			}
		}		
		return bloqueado;
	}
	
	/**
	 * Eval&uacute;a el estado del usuario para indicar si se encuentra inactivo.
	 *
	 * <p>
	 * Se toma en cuenta la bandera ESTATUS.
	 * 
	 * @param usuario El usuario que se evaluara su estado.
	 * @return true, si el usuario se encuentra inactivo.
	 */
	private boolean esUsuarioInactivo(UsuarioAcceso usuario){		
		boolean usuarioInactivo = Boolean.FALSE;
		
		if( usuario.getEstatus() != null && 
			usuario.getEstatus().equals(EstatusEnum.I)){
			usuarioInactivo = Boolean.TRUE;
		}		
		return usuarioInactivo;
	}
	
	/**
	 * Eval&uacute;a si se debe permitir la autenticaci&oacute;n de un mismo usuario a la vez.
	 * 
	 * <p>
	 * Se considera el par&aacute;metro de configuraci&oacute;n {@code aplicacion.properties} : {@code autenticacion.multiple_sesion}.
	 *
	 * @param usuario El usuario que se evaluara su estado.
	 * @return true, si se debe permitir multiple-sesion
	 */
	private boolean permitirAutenticacionMultisesion(UsuarioAcceso usuario){
		boolean esUsuarioAutenticado = Boolean.FALSE;
		if( usuario.getLogueado() != null && 
			usuario.getLogueado().equals(DecisionEnum.S)){
			esUsuarioAutenticado = Boolean.TRUE;
		}
		
		String configuracionMultisession = AplicacionPropertiesUtil.getPropiedades().obtenerPropiedad(CONFIGURACION_USO_MULTISESSION);
		boolean	sePermiteMultipleSession = Boolean.TRUE;
		if(StringUtils.isNotBlank(configuracionMultisession)){
			sePermiteMultipleSession = Boolean.parseBoolean(configuracionMultisession);			
		}
		
		boolean permitirAutenticacion = Boolean.TRUE;		
		if(esUsuarioAutenticado && !sePermiteMultipleSession){
			permitirAutenticacion = Boolean.FALSE;
		}		
		
		return permitirAutenticacion;		
	}
	
	public Boolean cambiarPassword(String claveUsuario, String password){
		password=HashUtils.md5(password);
		Boolean resultado=false;
		String  contrasenia=usuarioRules.consultaContrasenia(claveUsuario);
		if(contrasenia.equals(password)){
			resultado=false;
		}else{
		repositorioAutenticacion.cambiarPassword(password, claveUsuario);
		resultado=true;
		}
		return resultado;
		
	}
}
