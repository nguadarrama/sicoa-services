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
	public static final String ID_ARCHIVO2 = "idArchivo";
	public static final String ID_DETALLE = "id_detalle";
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
	public static final String ID_PERIODO = "id_periodo";
	public static final String ID_PERIODO2 = "idPeriodo";
	public static final String ID_RESPONSABLE = "id_responsable";
	public static final String ID_TIPO_DIA = "id_tipo_dia";
	public static final String ID_TIPO_DIA2 = "idTipoDia";
	public static final String ID_UNIDAD = "id_unidad";
	public static final String ID_USUARIO = "id_usuario";
	public static final String ID_VACACION = "id_vacacion";
	
	
	
	
	
	
	
	public static final String APELLIDO_PATERNO = "apellido_paterno";
	public static final String APELLIDO_MATERNO = "apellido_materno";
	public static final String ACTIVO = "activo";
	public static final String BLOQUEADO = "bloqueado";
	public static final String COMISION = "comision";
	public static final String CLAVE_USUARIO2 = "claveUsuario";
	public static final String CVE_C_PERFIL = "cve_c_perfil";
	public static final String CLAVE_M_USUARIO = "cve_m_usuario";
	public static final String DESCUENTO = "descuento";
	public static final String DESCRIPCION = "descripcion";
	public static final String DIAS = "dias";
	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	public static final String ESTATUS = "estatus";
	public static final String ESTATUS_USUARIO = "estatus_usuario";
	public static final String ENTRADA = "entrada";
	public static final String EN_SESION = "en_sesion";
	public static final String FECHA = "fecha";
	public static final String FECHA_INICIO = "fecha_inicio";
	public static final String FECHA_INICIO2 = "fechaInicio";
	public static final String FECHA_FIN = "fecha_fin";
	public static final String FECHA_FIN2 = "fechaFin";
	public static final String FECHA_REGISTRO = "fecha_registro";
	public static final String FECHA_INGRESO = "fecha_ingreso";
	public static final String FECHA_BLOQUEO = "fecha_bloqueo";
	
	
	public static final String HORARIO = "horario";
	public static final String HORA_ENTRADA = "hora_entrada";
	public static final String HORA_SALIDA = "hora_salida";
	public static final String INCIDENCIA = "incidencia";
	public static final String LICENCIA = "licencia";
	public static final String NOMBRE_AUTORIZADOR2 = "nombreAutorizador";
	public static final String NOMBRE_UNIDAD = "nombre_unidad";
	public static final String NUMERO_INTENTOS = "numero_intentos";
	public static final String NUEVO = "nuevo";
	public static final String NIVEL = "nivel";
	public static final String NOMBRE = "nombre";
	public static final String OBSERVACIONES = "observaciones";
	public static final String OBSERVACION = "observacion";
	public static final String PRIMERA_VEZ = "primera_vez";
	public static final String PADECIMIENTO = "padecimiento";
	
	public static final String RFC = "rfc";
	public static final String SUMA_DIAS = "suma_dias";
	public static final String SIZE = "size";
	public static final String SALIDA = "salida";
	public static final String TOTAL_LICENCIAS = "total_licencias";
	public static final String URL = "url";
	public static final String ULTIMO_ACCESO = "ultimo_acceso";
	public static final String VACACION = "vacacion";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY buscaPeriodo
	 */
	public static final String L90 = "select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ";
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
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
//	public static final String
	

}
