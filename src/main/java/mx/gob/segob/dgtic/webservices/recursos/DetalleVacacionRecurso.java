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

import mx.gob.segob.dgtic.business.service.DetalleVacacionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.DetalleVacacionDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class DetalleVacacionRecurso {

	@Autowired
	private DetalleVacacionService detalleVacacionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneDetalleVacaciones")	
	@PermitAll
	public Response obtieneDetalleVacaciones() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.obtenerListaDetalleVacaciones());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaDetalleVacacion")	
	@PermitAll
	public Response buscaDetalleVacacion(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, detalleVacacionService.buscaDetalleVacacion(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaDetalleVacacion")	
	@PermitAll
	public Response modificaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DetalleVacacionDto detalleVacacionDto = gson.fromJson(jsonObject.get("detalleVacacion"), DetalleVacacionDto.class);
		
		detalleVacacionService.modificaDetalleVacacion(detalleVacacionDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaDetalleVacacion")	
	@PermitAll
	public Response agregaDetalleVacacion(@RequestParam String jsonDetalleVacacion) {
		JsonObject jsonObject = new JsonParser().parse(jsonDetalleVacacion).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DetalleVacacionDto detalleVacacionDto = gson.fromJson(jsonObject.get("detalleVacacion"), DetalleVacacionDto.class);
		
		detalleVacacionService.agregaDetalleVacacion(detalleVacacionDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaDetalleVacacion")	
	@PermitAll
	public Response eliminaDetalleVacacion(@QueryParam("id") Integer id) {
		
		detalleVacacionService.eliminaDetalleVacacion(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
