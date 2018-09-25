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

import mx.gob.segob.dgtic.business.service.IncidenciaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.IncidenciaDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class IncidenciaRecurso {

	@Autowired
	private IncidenciaService incidenciaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneIncidencias")	
	@PermitAll
	public Response obtieneIncidencias() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, incidenciaService.obtenerListaIncidencias());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaIncidencia")	
	@PermitAll
	public Response buscaIncidencia(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, incidenciaService.buscaIncidencia(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaIncidencia")	
	@PermitAll
	public Response modificaIncidencia(@RequestParam String jsonIncidencia) {
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		IncidenciaDto incidenciaDto = gson.fromJson(jsonObject.get("incidencia"), IncidenciaDto.class);
		
		incidenciaService.modificaIncidencia(incidenciaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaIncidencia")	
	@PermitAll
	public Response agregaIncidencia(@RequestParam String jsonIncidencia) {
		JsonObject jsonObject = new JsonParser().parse(jsonIncidencia).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		IncidenciaDto incidenciaDto = gson.fromJson(jsonObject.get("incidencia"), IncidenciaDto.class);
		
		incidenciaService.agregaIncidencia(incidenciaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaIncidencia")	
	@PermitAll
	public Response eliminaIncidencia(@QueryParam("id") Integer id) {
		
		incidenciaService.eliminaIncidencia(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
