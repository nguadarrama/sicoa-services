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
import mx.gob.segob.dgtic.business.service.JustificacionService;
import mx.gob.segob.dgtic.comun.sicoa.dto.JustificacionDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class JustificacionRecurso {
	
	@Autowired 
	private JustificacionService justificacionService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneJustificaciones")
	@PermitAll
	public Response obtieneJustificacion(){
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, justificacionService.obtenerListaJustificaciones());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaJustificacion")	
	@PermitAll
	public Response buscaJustificacion(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, justificacionService.buscaJustificacion(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaJustificacion")	
	@PermitAll
	public Response modificaJustificacion(@RequestParam String jsonJustificacion) {
		
		JsonObject jsonObject = new JsonParser().parse(jsonJustificacion).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		JustificacionDto justificacionDto = gson.fromJson(jsonObject.get("justificacion"), JustificacionDto.class);
		justificacionService.modificaJustificacion(justificacionDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaJustificacion")	
	@PermitAll
	public Response agregaJustificacion(@RequestParam String jsonPermiso) {
		JsonObject jsonObject = new JsonParser().parse(jsonPermiso).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		JustificacionDto justificacionDto = gson.fromJson(jsonObject.get("justificacion"), JustificacionDto.class);
		
		justificacionService.agregaJustificacion(justificacionDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaJustificacion")	
	@PermitAll
	public Response eliminaJustificacion(@QueryParam("id") Integer id) {
		justificacionService.eliminaJustificacion(id);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
