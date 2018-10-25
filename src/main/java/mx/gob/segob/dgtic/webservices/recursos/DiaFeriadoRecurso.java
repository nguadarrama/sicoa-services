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
import mx.gob.segob.dgtic.business.service.DiaFestivoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class DiaFeriadoRecurso {
	
	@Autowired 
	private DiaFestivoService diaFestivoService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneDiasFestivos")
	@PermitAll
	public Response obtieneDiaFestivos(){
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, diaFestivoService.obtenerListaDiasFestivos());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaDiaFestivo")	
	@PermitAll
	public Response buscaDiaFestivo(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, diaFestivoService.buscaDiaFestivo(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaDiaFestivo")	
	@PermitAll
	public Response modificaDiaFestivo(@RequestParam String jsonDiaFestivo) {
		
		JsonObject jsonObject = new JsonParser().parse(jsonDiaFestivo).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DiaFestivoDto tipoDiaDto = gson.fromJson(jsonObject.get("tipoDia"), DiaFestivoDto.class);
		diaFestivoService.modificaDiaFestivo(tipoDiaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaDiaFestivo")	
	@PermitAll
	public Response agregaDiaFestivo(@RequestParam String jsonPermiso) {
		JsonObject jsonObject = new JsonParser().parse(jsonPermiso).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DiaFestivoDto tipoDiaDto = gson.fromJson(jsonObject.get("tipoDia"), DiaFestivoDto.class);
		
		diaFestivoService.agregaDiaFestivo(tipoDiaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaDiaFestivo")	
	@PermitAll
	public Response eliminaDiaFestivo(@QueryParam("id") Integer id) {
		diaFestivoService.eliminaDiaFestivo(id);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
}
