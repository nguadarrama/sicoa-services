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

import mx.gob.segob.dgtic.business.service.VacacionPeriodoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.VacacionPeriodoDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class VacacionPeriodoRecurso {

	@Autowired
	private VacacionPeriodoService vacacionPeriodoService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneVacacionesPeriodos")	
	@PermitAll
	public Response obtieneVacacionesPeriodos() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, vacacionPeriodoService.obtenerListaVacacionPeriodo());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaVacacionPeriodo")	
	@PermitAll
	public Response buscaVacacionPeriodo(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, vacacionPeriodoService.buscaVacacionPeriodo(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaVacacionPeriodo")	
	@PermitAll
	public Response modificaVacacionPeriodo(@RequestParam String jsonVacacionPeriodo) {
		JsonObject jsonObject = new JsonParser().parse(jsonVacacionPeriodo).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		VacacionPeriodoDto vacacionArchivoDto = gson.fromJson(jsonObject.get("vacacionPeriodo"), VacacionPeriodoDto.class);
		
		vacacionPeriodoService.modificaVacacionPeriodo(vacacionArchivoDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaVacacionPeriodo")	
	@PermitAll
	public Response agregaVacacionPeriodo(@RequestParam String jsonVacacionPeriodo) {
		JsonObject jsonObject = new JsonParser().parse(jsonVacacionPeriodo).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		VacacionPeriodoDto vacacionArchivoDto = gson.fromJson(jsonObject.get("vacacionArchivo"), VacacionPeriodoDto.class);
		
		vacacionPeriodoService.agregaVacacionPeriodo(vacacionArchivoDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
}
