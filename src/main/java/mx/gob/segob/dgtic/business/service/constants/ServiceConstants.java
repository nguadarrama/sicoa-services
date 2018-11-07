package mx.gob.segob.dgtic.business.service.constants;

import javax.resource.spi.IllegalStateException;

public class ServiceConstants {
	
	private ServiceConstants () throws IllegalStateException {
		throw new IllegalStateException("Constants class");
	}

	public static final String NOMBRE = "nombre";
	public static final String APELLIDO_PATERNO = "apellidoPaterno";
	public static final String APELLIDO_MATERNO = "apellidoMaterno";
	public static final String RFC = "rfc";
	public static final String ID_SOLICITUD = "idSolicitud";
	public static final String ID_ESTATUS = "idEstatus";
	public static final String PUESTO = "puesto";
	public static final String UNIDAD_ADMVA = "unidadAdministrativa";
	public static final String NUMERO_EMPLEADO = "numeroEmpleado";
	public static final String FECHA_INGRESO = "fechaIngreso";
	public static final String FECHA_INICIO = "fechaInicio";
	public static final String FECHA_FIN = "fechaFin";
	public static final String RESPONSABLE = "responsable";
	public static final String DIAS_VACACIONES = "diasVacaciones";
	public static final String MMM_DD_YYYY = "MMM dd, yyyy";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String INCIDENCIA = "incidencia";
	public static final String GENERA_REPORTE_ARCHIVO = "generaReporteArchivo";
	public static final String NULL = "null";
	public static final String DETALLE_VACACION = "detalleVacacion";
	public static final String COMISION = "comision";
	public static final String PERIODO = "periodo";
}
