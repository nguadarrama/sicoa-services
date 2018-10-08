package mx.gob.segob.dgtic.webservices.recursos;


import javax.annotation.security.PermitAll;
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
import mx.gob.segob.dgtic.business.service.HorarioService;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
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
		
		asistenciaService.creaIncidencia(incidencia);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	
	
}