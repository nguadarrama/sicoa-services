package mx.gob.segob.dgtic.webservices.recursos;


import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.segob.dgtic.business.service.AsistenciaService;
import mx.gob.segob.dgtic.business.service.HorarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.GeneraReporteArchivo;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.comun.util.FormatoIncidencia;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("asistencia")
@Component
public class AsistenciaRecurso {
	
	@Autowired
	private AsistenciaService asistenciaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoMes")	
	@PermitAll
	public Response buscaAsistenciaEmpleadoMes(@QueryParam("claveEmpleado") String claveEmpleado) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaEmpleadoMes(claveEmpleado));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoRango")	
	@PermitAll
	public Response buscaAsistenciaEmpleadoRango(
			@QueryParam("claveEmpleado") String claveEmpleado, 
			@QueryParam("inicio") String inicio, 
			@QueryParam("fin") String fin) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaEmpleadoRango(claveEmpleado, inicio, fin));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoRangoCoordinador")	
	@PermitAll
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
				paterno, materno, nivel, !tipo.equals("null") ? Integer.parseInt(tipo) : 0, estado, inicio, fin, unidadAdministrativa, cveCoordinador));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciasEmpleadoRangoDireccion")	
	@PermitAll
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
				paterno, materno, nivel, !tipo.equals("null") ? Integer.parseInt(tipo) : 0, estado, inicio, fin, unidadAdministrativa));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneAsistenciaPorId")	
	@PermitAll
	public Response buscaAsistenciaPorId(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.buscaAsistenciaPorId(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("creaIncidencia")	
	@PermitAll
	public Response creaIncidencia(@RequestParam String jsonIncidencia) {
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		IncidenciaDto incidencia = gson.fromJson(jsonObject.get("incidencia"), IncidenciaDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.creaIncidencia(incidencia));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("creaDescuento")	
	@PermitAll
	public Response creaDescuento(@RequestParam String jsonDescuento) {
		JsonObject jsonObject = new JsonParser().parse(jsonDescuento).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		IncidenciaDto descuento = gson.fromJson(jsonObject.get("incidencia"), IncidenciaDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.creaDescuento(descuento));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("dictaminaIncidencia")	
	@PermitAll
	public Response dictaminaIncidencia(@RequestParam String jsonIncidencia) {
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		IncidenciaDto incidencia = gson.fromJson(jsonObject.get("incidencia"), IncidenciaDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.dictaminaIncidencia(incidencia));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("descuentaIncidencia")	
	@PermitAll
	public Response descuentaIncidencia(@RequestParam String jsonIncidencia) {
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		IncidenciaDto incidencia = gson.fromJson(jsonObject.get("incidencia"), IncidenciaDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, asistenciaService.aplicaDescuento(incidencia));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("formatoJustificacion")	
	@PermitAll
	public Response formatoJustificacion(@RequestParam String jsonFormatoJustificacion) {
		JsonObject jsonObject = new JsonParser().parse(jsonFormatoJustificacion).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		FormatoIncidencia generaReporteArchivo = gson.fromJson(jsonObject.get("generaReporteArchivo"), FormatoIncidencia.class);
		
		

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,asistenciaService.generaFormatoJustificacion(generaReporteArchivo));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("formatoDescuento")	
	@PermitAll
	public Response formatoDescuento(@RequestParam String jsonFormatoDescuento) {
		JsonObject jsonObject = new JsonParser().parse(jsonFormatoDescuento).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		FormatoIncidencia generaReporteArchivo = gson.fromJson(jsonObject.get("generaReporteArchivo"), FormatoIncidencia.class);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,asistenciaService.generaFormatoDescuento(generaReporteArchivo));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("reporteCoordinador")	
	@PermitAll
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
				paterno, materno, nivel, !tipo.equals("null") ? Integer.parseInt(tipo) : 0, estado, inicio, 
						fin, unidadAdministrativa, cveCoordinador, permisos));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("reporteDireccion")	
	@PermitAll
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
				paterno, materno, nivel, !tipo.equals("null") ? Integer.parseInt(tipo) : 0, estado, inicio, fin, unidadAdministrativa, permisos));
	}
	
}