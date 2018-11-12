package mx.gob.segob.dgtic.webservices.recursos;


import java.io.FileNotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.business.service.constants.ServiceConstants;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.util.FormatoIncidencia;
import mx.gob.segob.dgtic.webservices.recursos.base.RecursoBase;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("asistencia")
@Component
public class AsistenciaRecurso extends RecursoBase{
	
	@Autowired
	private AsistenciaService asistenciaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoMes")	
	public Response buscaAsistenciaEmpleadoMes(@QueryParam("claveEmpleado") String claveEmpleado) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaEmpleadoMes(claveEmpleado));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoRango")	
	public Response buscaAsistenciaEmpleadoRango(
			@QueryParam("claveEmpleado") String claveEmpleado, 
			@QueryParam("inicio") String inicio, 
			@QueryParam("fin") String fin) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaEmpleadoRango(claveEmpleado, inicio, fin));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoRangoCoordinador")	
	public Response buscaAsistenciaEmpleadoRangoCoordinador(
			@QueryParam("claveEmpleado") String claveEmpleado,
			@QueryParam("nombre") String nombre,
			@QueryParam("paterno") String paterno,
			@QueryParam("materno") String materno,
			@QueryParam("nivel") String nivel,
			@QueryParam("tipo") String tipo,
			@QueryParam("estado") String estado, 
			@QueryParam("inicio") String inicio, 
			@QueryParam("fin") String fin,
			@QueryParam("unidad") String unidadAdministrativa, 
			@QueryParam("cveCoordinador") String cveCoordinador) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaEmpleadoRangoCoordinador(claveEmpleado, nombre, 
				paterno, materno, nivel, !tipo.equals(ServiceConstants.NULL) ? Integer.parseInt(tipo) : 0, !estado.equals(ServiceConstants.NULL) ? Integer.parseInt(estado) : 0, inicio, fin, unidadAdministrativa, cveCoordinador));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoRangoDireccion")	
	public Response buscaAsistenciaEmpleadoRangoDireccion(
			@QueryParam("claveEmpleado") String claveEmpleado,
			@QueryParam("nombre") String nombre,
			@QueryParam("paterno") String paterno,
			@QueryParam("materno") String materno,
			@QueryParam("nivel") String nivel,
			@QueryParam("tipo") String tipo,
			@QueryParam("estado") String estado, 
			@QueryParam("inicio") String inicio, 
			@QueryParam("fin") String fin,
			@QueryParam("unidad") String unidadAdministrativa) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaEmpleadoRangoDireccion(claveEmpleado, nombre, 
				paterno, materno, nivel, !tipo.equals(ServiceConstants.NULL) ? Integer.parseInt(tipo) : 0, !estado.equals(ServiceConstants.NULL) ? Integer.parseInt(estado) : 0, inicio, fin, unidadAdministrativa));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciaPorId")	
	public Response buscaAsistenciaPorId(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaPorId(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("creaIncidencia")	
	public Response creaIncidencia(@RequestParam String jsonIncidencia) {
		jsonIncidencia = this.cambiaCaracter(jsonIncidencia);
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(ServiceConstants.YYYY_MM_DD);
		Gson gson = builder.create();
		IncidenciaDto incidencia = gson.fromJson(jsonObject.get(ServiceConstants.INCIDENCIA), IncidenciaDto.class);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.creaIncidencia(incidencia));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("creaDescuento")	
	public Response creaDescuento(@RequestParam String jsonDescuento) {
		jsonDescuento = this.cambiaCaracter(jsonDescuento);
		
		JsonObject jsonObject = new JsonParser().parse(jsonDescuento).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(ServiceConstants.YYYY_MM_DD);
		Gson gson = builder.create();
		IncidenciaDto descuento = gson.fromJson(jsonObject.get(ServiceConstants.INCIDENCIA), IncidenciaDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.creaDescuento(descuento));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("dictaminaIncidencia")	
	public Response dictaminaIncidencia(@RequestParam String jsonIncidencia) {
		
		jsonIncidencia = this.cambiaCaracter(jsonIncidencia);
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(ServiceConstants.YYYY_MM_DD);
		Gson gson = builder.create();
		IncidenciaDto incidencia = gson.fromJson(jsonObject.get(ServiceConstants.INCIDENCIA), IncidenciaDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.dictaminaIncidencia(incidencia));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("descuentaIncidencia")	
	public Response descuentaIncidencia(@RequestParam String jsonIncidencia) {
		
		jsonIncidencia = this.cambiaCaracter(jsonIncidencia);
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(ServiceConstants.YYYY_MM_DD);
		Gson gson = builder.create();
		IncidenciaDto incidencia = gson.fromJson(jsonObject.get(ServiceConstants.INCIDENCIA), IncidenciaDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.aplicaDescuento(incidencia));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("formatoJustificacion")	
	public Response formatoJustificacion(@RequestParam String jsonFormatoJustificacion) throws FileNotFoundException {
		
		jsonFormatoJustificacion = this.cambiaCaracter(jsonFormatoJustificacion);
		JsonObject jsonObject = new JsonParser().parse(jsonFormatoJustificacion).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		FormatoIncidencia generaReporteArchivo = gson.fromJson(jsonObject.get(ServiceConstants.GENERA_REPORTE_ARCHIVO), FormatoIncidencia.class);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,asistenciaService.generaFormatoJustificacion(generaReporteArchivo));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("formatoDescuento")	
	public Response formatoDescuento(@RequestParam String jsonFormatoDescuento) throws FileNotFoundException {
		jsonFormatoDescuento = this.cambiaCaracter(jsonFormatoDescuento);
		
		JsonObject jsonObject = new JsonParser().parse(jsonFormatoDescuento).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		FormatoIncidencia generaReporteArchivo = gson.fromJson(jsonObject.get(ServiceConstants.GENERA_REPORTE_ARCHIVO), FormatoIncidencia.class);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,asistenciaService.generaFormatoDescuento(generaReporteArchivo));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("reporteCoordinador")	
	public Response reporteCoordinador(
			@QueryParam("claveEmpleado") String claveEmpleado,
			@QueryParam("nombre") String nombre,
			@QueryParam("paterno") String paterno,
			@QueryParam("materno") String materno,
			@QueryParam("nivel") String nivel,
			@QueryParam("tipo") String tipo,
			@QueryParam("estado") String estado, 
			@QueryParam("inicio") String inicio, 
			@QueryParam("fin") String fin,
			@QueryParam("unidad") String unidadAdministrativa, 
			@QueryParam("cveCoordinador") String cveCoordinador,
			@QueryParam("permisos") String permisos) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.reporteCoordinador(claveEmpleado, nombre, 
				paterno, materno, nivel, !tipo.equals(ServiceConstants.NULL) ? Integer.parseInt(tipo) : 0, !estado.equals(ServiceConstants.NULL) ? Integer.parseInt(estado) : 0, inicio, 
						fin, unidadAdministrativa, cveCoordinador, permisos));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("reporteDireccion")	
	public Response reporteDireccion(
			@QueryParam("claveEmpleado") String claveEmpleado,
			@QueryParam("nombre") String nombre,
			@QueryParam("paterno") String paterno,
			@QueryParam("materno") String materno,
			@QueryParam("nivel") String nivel,
			@QueryParam("tipo") String tipo,
			@QueryParam("estado") String estado, 
			@QueryParam("inicio") String inicio, 
			@QueryParam("fin") String fin,
			@QueryParam("unidad") String unidadAdministrativa,
			@QueryParam("permisos") String permisos) {
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.reporteDireccion(claveEmpleado, nombre, 
				paterno, materno, nivel, !tipo.equals(ServiceConstants.NULL) ? Integer.parseInt(tipo) : 0, !estado.equals(ServiceConstants.NULL) ? Integer.parseInt(estado) : 0, inicio, fin, unidadAdministrativa, permisos));
	}
	
}