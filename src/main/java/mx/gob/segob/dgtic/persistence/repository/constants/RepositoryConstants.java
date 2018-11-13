package mx.gob.segob.dgtic.persistence.repository.constants;

import javax.resource.spi.IllegalStateException;

public class RepositoryConstants {
	
	private RepositoryConstants () throws IllegalStateException {
		throw new IllegalStateException("Constants class");
	}
	
	public static final String ID_ARCHIVO = "id_archivo";
	
	public static final String ID_ASISTENCIA = "id_asistencia";
	public static final String ID_ASISTENCIA2 = "idAsistencia";
	public static final String ID_AREA = "id_area";
	public static final String ID_AREA2 = "idArea";
	public static final String ID_ARCHIVO2 = "idArchivo";
	public static final String ID_DETALLE = "id_detalle";
	public static final String ID_DETALLE2 = "idDetalle";
	public static final String ID_DIA2 = "idDia"; 
	public static final String ID_ESTATUS = "id_estatus";
	public static final String ID_ESTATUS2 = "idEstatus";
	public static final String ID_FESTIVO = "id_festivo";
	public static final String ID_HORARIO = "id_horario";
	public static final String ID_HORARIO2 = "idHorario";
	public static final String ID_INCIDENCIA = "id_incidencia";
	public static final String ID_INCIDENCIA2 = "idIncidencia";
	public static final String ID_JUSTIFICACION = "id_justificacion";
	public static final String ID_JUSTIFICACION2 = "idJustificacion";
	public static final String ID_LICENCIA = "id_licencia";
	public static final String ID_LICENCIA2 = "idLicencia";
	public static final String ID_NIVEL = "id_nivel";
	public static final String ID_NIVEL2 = "idNivel";
	public static final String ID_PUESTO = "id_puesto";
	public static final String ID_PUESTO2 = "idPuesto";
	public static final String ID_PERIODO = "id_periodo";
	public static final String ID_PERIODO2 = "idPeriodo";
	public static final String ID_RESPONSABLE = "id_responsable";
	public static final String ID_TIPO_DIA = "id_tipo_dia";
	public static final String ID_TIPO_DIA2 = "idTipoDia";
	public static final String ID_UNIDAD = "id_unidad";
	public static final String ID_USUARIO = "id_usuario";
	public static final String ID_USUARIO2 = "idUsuario";
	public static final String ID_VACACION = "id_vacacion";
	public static final String ID_VACACION2 = "idVacacion";
	
	
	
	
	
	
	public static final String APELLIDO_PATERNO = "apellido_paterno";
	public static final String APELLIDO_PATERNO2 = "apellidoPaterno";
	public static final String APELLIDO_MATERNO = "apellido_materno";
	public static final String APELLIDO_MATERNO2 = "apellidoMaterno";
	public static final String ACTIVO = "activo";
	public static final String BLOQUEADO = "bloqueado";
	public static final String COMISION = "comision";
	public static final String CLAVE = "clave";
	public static final String CLAVE_USUARIO2 = "claveUsuario";
	public static final String CLAVE_PERFIL2 = "clavePerfil";
	public static final String CVE_C_PERFIL = "cve_c_perfil";
	public static final String CLAVE_M_USUARIO = "cve_m_usuario";
	public static final String DESCUENTO = "descuento";
	public static final String DESCRIPCION = "descripcion";
	public static final String DESCRIPCION_PERIODO = "descripcion_periodo";
	public static final String DIAS = "dias";
	public static final String DIAS_DISPONIBLES = "dias_disponibles";
	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	public static final String ESTATUS = "estatus";
	public static final String ESTATUS_USUARIO = "estatus_usuario";
	public static final String ENTRADA = "entrada";
	public static final String EN_SESION = "en_sesion";
	public static final String EN_SESION2 = "enSesion";
	public static final String FECHA = "fecha";
	public static final String FECHA_INICIO = "fecha_inicio";
	public static final String FECHA_INICIO2 = "fechaInicio";
	public static final String FECHA_FIN = "fecha_fin";
	public static final String FECHA_FIN2 = "fechaFin";
	public static final String FECHA_REGISTRO = "fecha_registro";
	public static final String FECHA_REGISTRO2 = "fechaRegistro";
	public static final String FECHA_INGRESO = "fecha_ingreso";
	public static final String FECHA_INGRESO2 = "fechaIngreso";
	public static final String FECHA_BLOQUEO = "fecha_bloqueo";
	public static final String FECHA_BLOQUEO2 = "fechaBloqueo";
	
	public static final String HORARIO = "horario";
	public static final String HORA_ENTRADA = "hora_entrada";
	public static final String HORA_SALIDA = "hora_salida";
	public static final String INCIDENCIA = "incidencia";
	public static final String JUSTIFICACION = "justificacion";
	public static final String LICENCIA = "licencia";
	public static final String NOMBRE_AUTORIZADOR2 = "nombreAutorizador";
	public static final String NOMBRE_ARCHIVO = "nombre_archivo";
	public static final String NOMBRE_UNIDAD = "nombre_unidad";
	public static final String NOMBRE_USUARIO = "nombre_usuario";
	public static final String NUMERO_INTENTOS = "numero_intentos";
	public static final String NUMERO_INTENTOS2 = "numeroIntentos";
	public static final String NUEVO = "nuevo";
	public static final String NIVEL = "nivel";
	public static final String NOMBRE = "nombre";
	public static final String NOMBRE_JEFE = "nombre_jefe";
	public static final String NOMBRE_JEFE2 = "nombreJefe";
	public static final String NOMBRE_TIPO = "nombre_tipo";
	public static final String OBSERVACIONES = "observaciones";
	public static final String OBSERVACION = "observacion";
	public static final String PRIMERA_VEZ = "primera_vez";
	public static final String PRIMERA_VEZ2 = "primeraVez";
	public static final String PADECIMIENTO = "padecimiento";
	public static final String PASS_WORD = "password";
	public static final String PUESTO = "puesto";
	public static final String RFC = "rfc";
	public static final String SUMA_DIAS = "suma_dias";
	public static final String SIZE = "size";
	public static final String SALIDA = "salida";
	public static final String TOTAL_LICENCIAS = "total_licencias";
	public static final String URL = "url";
	public static final String ULTIMO_ACCESO = "ultimo_acceso";
	public static final String ULTIMO_ACCESO2 = "ultimoAcceso";
	public static final String VACACION = "vacacion";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY buscaPeriodo
	 */
	public static final String L90= "select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ";
	public static final String L91 = "from r_periodo ";
	public static final String L92 = "where id_periodo = :idPeriodo";
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY obtenerListaPeriodos
	 */
	public static final String L42 = "select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ";
	public static final String L43 = "from r_periodo where activo = true ";
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY obtenerListaPeriodos
	 */
	public static final String L39 = "select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ";
	public static final String L40 = "from r_periodo where activo = true ";
	
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY obtenerListaPeriodos
	 */
	public static final String L102 = "update r_periodo set fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, descripcion = :descripcion, activo = :activo ";
	public static final String L103 = "where id_periodo = :idPeriodo";
	/**
	 * ASISTENCIAREPOSITORYIMPL
	 * QUERY editaIncidencia
	 */
	public static final String L480 = "update m_incidencia ";
	public static final String L481 = "set id_justificacion = :idJustificacion, id_archivo = :idArchivo, nombre_autorizador = :nombreAutorizador, descuento = :descuento ";
	public static final String L482 = "WHERE id_asistencia = :idAsistencia";
	/**
	 * UNIDAD ADMINISTRATIVAREPOSITORY
	 * QUERY obtenerUnidadesAdministrativas
	 */
	public static final String L151 = "select id_unidad, nombre ";
	public static final String L152 = "from c_unidad_administrativa ";
	/**
	 * ASISTENCIA REPOSITORYIMPL
	 * QUERY obtieneListaEmpleadosDeVacacionesHoy
	 */
	public static final String L584 = "SELECT id_usuario ";
	public static final String L585 = "FROM m_asistencia ";
	public static final String L586 = "where entrada >= date_Add(curdate(), interval -1 day) ";
	public static final String L587 = "and entrada < curdate() ";
	public static final String L588 = "and id_tipo_dia = 5";
	/**
	 * ASISTENCIA REPOSITORYIMPL
	 * QUERY editaIncidencia
	 */
	public static final String L476 = "update m_incidencia ";
	public static final String L477 = "set id_justificacion = :idJustificacion, id_archivo = :idArchivo, nombre_autorizador = :nombreAutorizador, descuento = :descuento ";
	public static final String L478 = "WHERE id_asistencia = :idAsistencia";
	/**
	 * INCIDENCIAREPOSITORYIMPL
	 * QUERY obtenerListaIncidencias
	 */
	public static final String L34 = "SELECT id_incidencia, id_asistencia, id_tipo_dia, id_archivo, id_estatus, id_responsable, descuento, observaciones ";
	public static final String L35 = "FROM m_incidencia ";
	/**
	 * DIAFESTIVOREPOSITORYIMPL
	 * obtenerListaDiasFestivos
	 */
	public static final String DL31 = "SELECT id_festivo, nombre, fecha, activo ";
	public static final String DL32 = "FROM c_dia_festivo where activo = true ";
	/**
	 * ASISTENCIAREPOSITORYIMPL
	 * QUERY buscaAsistenciaEmpleadoRango
	 */
	public static final String ARIL84 = "SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ";
	public static final String ARIL85 = "i.id_estatus, i.descuento ";
	public static final String ARIL86 = "FROM m_asistencia a ";
	public static final String ARIL87 = "inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ";
	public static final String ARIL88 = "left join m_incidencia i on a.id_asistencia = i.id_asistencia ";
	public static final String ARIL89 = "left join m_estatus e on e.id_estatus = i.id_estatus ";
	public static final String ARIL90 = "WHERE id_usuario = ";
	public static final String ARIL91 = " and a.id_tipo_dia = t.id_tipo_dia";
	public static final String ARIL94 = " and entrada >= '";
	public static final String ARIL95 = " and entrada < '";
	/**
	 * ASISTENCIAREPOSITORYIMPL
	 * QUERY buscaAsistenciaEmpleadoRangoCoordinador
	 */
	public static final String ARIL142 = "SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, e.id_estatus, ua.nombre as nombre_unidad, ";
	public static final String ARIL143 = "i.id_estatus, i.descuento ";
	public static final String ARIL144 = "FROM m_asistencia a ";
	public static final String ARIL145 = "inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ";
	public static final String ARIL146 = "left join m_incidencia i on a.id_asistencia = i.id_asistencia ";
	public static final String ARIL147 = "left join m_estatus e on e.id_estatus = i.id_estatus ";
	public static final String ARIL148 = "inner join m_usuario u on u.cve_m_usuario = a.id_usuario ";
	public static final String ARIL149 = "inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ";
	public static final String ARIL150 = "inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ";
	public static final String ARIL151 = "where uua.id_unidad = ";
	public static final String ARIL154 = " and entrada >= '";
	public static final String ARIL155 = " and entrada < '";
	public static final String ARIL159 = " and a.id_usuario = ";
	public static final String ARIL163 = " and u.nombre like '%";
	public static final String ARIL167 = " and u.apellido_paterno like '%";
	public static final String ARIL171 = " and u.apellido_materno like '%";
	public static final String ARIL175 = " and ua.nombre like '' ";
	public static final String ARIL179 = " and u.nivel like '%";
	public static final String ARIL183 = " and t.id_tipo_dia = ";
	public static final String ARIL187 = " and e.id_estatus = ";
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY modificaPeriodo		
	 */
	public static final String PRL103 = "update r_periodo set fecha_inicio= :fechaInicio, fecha_fin = :fechaFin, descripcion = :descripcion, activo = :activo ";
	public static final String PRL104 = "where id_periodo = :idPeriodo";
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY cambioEstatusPeriodo
	 */
	public static final String PRL229 = "update r_periodo set activo = :activo ";
	public static final String PRL230 = "where id_periodo = :idPeriodo";
	/**
	 * ASISTENCIAREPOSITORYIMPL
	 * QUERY editaDescuento
	 */
	public static final String ARL502 = "update m_incidencia ";
	public static final String ARL503 = "set id_justificacion = :idJustificacion, nombre_autorizador = :nombreAutorizador, id_archivo = :idArchivo, descuento = :descuento ";
	public static final String ARL504 = "WHERE id_asistencia = :idAsistencia";
	/**
	 * ASISTENCIAREPOSITORYIMPL
	 * QUERY buscaAsistenciaEmpleadoRangoDireccion
	 */
	public static final String ARL237 = "SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ";
	public static final String ARL238 = "i.id_estatus, i.descuento ";
	public static final String ARL239 = "FROM m_asistencia a ";
	public static final String ARL240 = "inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ";
	public static final String ARL241 = "left join m_incidencia i on a.id_asistencia = i.id_asistencia ";
	public static final String ARL242 = "left join m_estatus e on e.id_estatus = i.id_estatus ";
	public static final String ARL243 = "inner join m_usuario u on u.cve_m_usuario = a.id_usuario ";
	public static final String ARL244 = "inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ";
	public static final String ARL245 = "inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ";
	public static final String ARL246 = "where 1 = 1";
	/**
	 * PERIODO REPOSITORYIMPL
	 * QUERY obtenerListaPeriodos
	 */
	public static final String PRL41 = "select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ";
	public static final String PRL42 = "from r_periodo where activo = true ";
	/**
	 * ASISTENCIAREPOSITORYIMPL
	 * QUERY buscaAsistenciaEmpleadoRangoDireccion
	 */
	public static final String ARIL233 = "SELECT a.id_asistencia, a.id_usuario, a.id_tipo_dia, a.entrada, a.salida, t.nombre, e.estatus, ";
	public static final String ARIL234 = "i.id_estatus, i.descuento ";
	public static final String ARIL235 = "FROM m_asistencia a ";
	public static final String ARIL236 = "inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ";
	public static final String ARIL237 = "left join m_incidencia i on a.id_asistencia = i.id_asistencia ";
	public static final String ARIL238 = "left join m_estatus e on e.id_estatus = i.id_estatus ";
	public static final String ARIL239 = "inner join m_usuario u on u.cve_m_usuario = a.id_usuario ";
	public static final String ARIL240 = "inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ";
	public static final String ARIL241 = "inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ";
	public static final String ARIL242 = "where 1 = 1";
	public static final String ARIL245 = " and entrada >= '";
	public static final String ARIL246 = " and entrada < '";
	public static final String ARIL250 = " and a.id_usuario = ";
	public static final String ARIL254 = " and u.nombre like '%";
	public static final String ARIL258 = " and u.apellido_paterno like '%";
	public static final String ARIL262 = " and u.apellido_materno like '%";
	public static final String ARIL266 = " and ua.id_unidad = ";
	public static final String ARIL270 = " and u.nivel like '%";
	public static final String ARIL274 = " and t.id_tipo_dia = ";
	public static final String ARIL278 = " and e.id_estatus = ";
	/**
	 * ASISTENCIAREPOSITORYIMPL
	 * QUERY buscaAsistenciaPorId
	 */
	public static final String ARIL323 = "SELECT a.id_asistencia, a.entrada, a.id_tipo_dia, t.nombre as nombre_tipo, e.estatus, e.id_estatus, ";
	public static final String ARIL324 = "i.id_incidencia, j.id_justificacion, j.justificacion, u.nombre as nombre_usuario, u.apellido_paterno, u.apellido_materno, ";
	public static final String ARIL325 = "u.fecha_ingreso, u.cve_m_usuario, u.nombre_jefe, p.descripcion, u.id_puesto, u.rfc, u.nivel, ua.nombre as nombre_unidad, i.id_archivo, ch.url, ch.nombre as nombre_archivo ";
	public static final String ARIL326 = "FROM m_asistencia a ";
	public static final String ARIL327 = "inner join m_usuario u on u.cve_m_usuario = a.id_usuario ";
	public static final String ARIL328 = "inner join usuario_unidad_administrativa uua on uua.cve_m_usuario = u.cve_m_usuario ";
	public static final String ARIL329 = "inner join c_unidad_administrativa ua on ua.id_unidad = uua.id_unidad ";
	public static final String ARIL330 = "inner join c_perfil p on p.cve_c_perfil = u.cve_c_perfil ";
	public static final String ARIL331 = "inner join c_tipo_dia t on t.id_tipo_dia = a.id_tipo_dia ";
	public static final String ARIL332 = "left join m_incidencia i on a.id_asistencia = i.id_asistencia ";
	public static final String ARIL333 = "left join m_estatus e on e.id_estatus = i.id_estatus ";
	public static final String ARIL334 = "left join c_justificacion j on j.id_justificacion = i.id_justificacion ";
	public static final String ARIL335 = "left join m_archivo ch on ch.id_archivo = i.id_archivo ";
	public static final String ARIL336 = "WHERE a.id_asistencia = :idAsistencia ";
	
//	public static final String ARIL3
//	public static final String ARIL3
//	public static final String ARIL3
	
	
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
	

}
