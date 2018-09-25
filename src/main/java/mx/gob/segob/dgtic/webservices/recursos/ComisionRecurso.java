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

import mx.gob.segob.dgtic.business.service.ComisionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.ComisionDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class ComisionRecurso {

	@Autowired
	private ComisionService comisionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneComisiones")	
	@PermitAll
	public Response obtieneComisiones() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, comisionService.obtenerListacomisiones());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaComision")	
	@PermitAll
	public Response buscaComision(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, comisionService.buscaComision(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaComision")	
	@PermitAll
	public Response modificaComision(@RequestParam String jsonComision) {
		JsonObject jsonObject = new JsonParser().parse(jsonComision).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		ComisionDto comisionDto = gson.fromJson(jsonObject.get("comision"), ComisionDto.class);
		
		comisionService.modificaComision(comisionDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaComision")	
	@PermitAll
	public Response agregaComision(@RequestParam String jsonComision) {
		JsonObject jsonObject = new JsonParser().parse(jsonComision).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		ComisionDto comisionDto = gson.fromJson(jsonObject.get("comision"), ComisionDto.class);
		
		comisionService.agregaComision(comisionDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaComision")	
	@PermitAll
	public Response eliminaComision(@QueryParam("id") Integer id) {
		
		comisionService.eliminaComision(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
