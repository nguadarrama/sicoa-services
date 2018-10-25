
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

@Path("diaFestivo")
@Component
public class DiaFestivoRecurso {
	
	@Autowired
	private DiaFestivoService diaFestivoService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtiene")	
	@PermitAll
	public Response obtieneDiasFestivos() {
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, diaFestivoService.obtenerListaDiasFestivos());
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("busca")	
	@PermitAll
	public Response buscaDiaFestivo(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, diaFestivoService.buscaDiaFestivo(id));
		
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modifica")	
	@PermitAll
	public Response modificaDiaFestivo(@RequestParam String jsonDiaFestivo) {
		
		JsonObject jsonObject = new JsonParser().parse(jsonDiaFestivo).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DiaFestivoDto diaFestivo = gson.fromJson(jsonObject.get("diaFestivo"), DiaFestivoDto.class);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, diaFestivoService.modificaDiaFestivo(diaFestivo));
		
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agrega")	
	@PermitAll
	public Response agregaaHorario(@RequestParam String jsonDiaFestivo) {
		
		JsonObject jsonObject = new JsonParser().parse(jsonDiaFestivo).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DiaFestivoDto diaFestivoDto = gson.fromJson(jsonObject.get("diaFestivo"), DiaFestivoDto.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, diaFestivoService.agregaDiaFestivo(diaFestivoDto));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("elimina")	
	@PermitAll
	public Response eliminaDiaFestivo(@QueryParam("id") Integer id) {
		
		diaFestivoService.eliminaDiaFestivo(id);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		
	}
	
}