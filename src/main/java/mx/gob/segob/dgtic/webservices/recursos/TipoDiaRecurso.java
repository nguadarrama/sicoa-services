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

import mx.gob.segob.dgtic.business.service.TipoDiaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.TipoDiaDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class TipoDiaRecurso {
	@Autowired 
	private TipoDiaService tipoDiaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneTipoDias")
	@PermitAll
	public Response obtieneTipoDias(){
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, tipoDiaService.obtenerListaTipoDias());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaTipoDia")	
	@PermitAll
	public Response buscaTipoDia(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, tipoDiaService.buscaTipoDia(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaTipoDia")	
	@PermitAll
	public Response modificaTipoDia(@RequestParam String jsonTipoDia) {
		JsonObject jsonObject = new JsonParser().parse(jsonTipoDia).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		TipoDiaDto tipoDiaDto = gson.fromJson(jsonObject.get("tipoDia"), TipoDiaDto.class);
		
		tipoDiaService.modificaTipoDia(tipoDiaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaTipoDia")	
	@PermitAll
	public Response agregaTipoDia(@RequestParam String jsonPermiso) {
		JsonObject jsonObject = new JsonParser().parse(jsonPermiso).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		TipoDiaDto tipoDiaDto = gson.fromJson(jsonObject.get("tipoDia"), TipoDiaDto.class);
		
		tipoDiaService.agregaTipoDia(tipoDiaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaTipoDia")	
	@PermitAll
	public Response eliminaTipoDia(@QueryParam("id") Integer id) {
		
		tipoDiaService.eliminaTipoDia(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
