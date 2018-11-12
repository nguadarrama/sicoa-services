package mx.gob.segob.dgtic.persistence.repository.constants;

import javax.resource.spi.IllegalStateException;

public class RepositoryConstants {
	
	private RepositoryConstants () throws IllegalStateException {
		throw new IllegalStateException("Constants class");
	}
	
	public static final String ID_ARCHIVO = "id_archivo";
	public static final String ID_ARCHIVO2 = "idArchivo";
	public static final String ID_PERIODO = "id_periodo";
	public static final String ID_PERIODO2 = "idPeriodo";
	public static final String ID_UNIDAD = "id_unidad";
	public static final String ID_USUARIO = "id_usuario";
	public static final String APELLIDO_PATERNO = "apellido_paterno";
	public static final String APELLIDO_MATERNO = "apellido_materno";
	public static final String NOMBRE = "nombre";
	public static final String URL = "url";
	public static final String SIZE = "size";
	public static final String ACTIVO = "activo";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	public static final String FECHA_INICIO = "fecha_inicio";
	public static final String FECHA_INICIO2 = "fechaInicio";
	public static final String FECHA_FIN = "fecha_fin";
	public static final String FECHA_FIN2 = "fechaFin";
	public static final String FECHA_REGISTRO = "fecha_registro";
	public static final String VACACION = "vacacion";
	public static final String COMISION = "comision";
	public static final String LICENCIA = "licencia";
	public static final String CLAVE_USUARIO2 = "claveUsuario";
	public static final String CLAVE_M_USUARIO = "cve_m_usuario";
	public static final String NOMBRE_UNIDAD = "nombre_unidad";
	public static final String DESCRIPCION = "descripcion";
	public static final String CARACTERES_ESPECIALES = "";
	/**
	 * PERIODOREPOSITORYIMPL
	 * QUERY buscaPeriodo
	 */
	public static final String L87 = "select id_periodo, fecha_inicio, fecha_fin, descripcion, activo ";
	public static final String L88 = "from r_periodo ";
	public static final String L89 = "where id_periodo = :idPeriodo";
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
//	public static final String
	

}
