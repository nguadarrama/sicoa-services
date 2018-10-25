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
import mx.gob.segob.dgtic.business.service.HorarioService;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.comun.transport.dto.catalogo.Horario;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class HorarioRecurso {
	
	@Autowired
	private HorarioService horarioService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneHorarios")	
	@PermitAll
	public Response obtieneHorarios() {
		//asistenciaService.procesaAsistencia();

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, horarioService.obtenerListaHorarios());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaHorario")	
	@PermitAll
	public Response buscaHorario(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, horarioService.buscaHorario(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaHorario")	
	@PermitAll
	public Response modificaHorario(@RequestParam String jsonHorario) {
		JsonObject jsonObject = new JsonParser().parse(jsonHorario).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		Horario horario = gson.fromJson(jsonObject.get("horario"), Horario.class);
		
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, horarioService.modificaHorario(horario));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaHorario")	
	@PermitAll
	public Response agregaaHorario(@RequestParam String jsonHorario) {
		JsonObject jsonObject = new JsonParser().parse(jsonHorario).getAsJsonObject();
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd");
		Gson gson = builder.create();
		Horario horario = gson.fromJson(jsonObject.get("horario"), Horario.class);	
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, horarioService.agregaHorario(horario));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaHorario")	
	@PermitAll
	public Response eliminaHorario(@QueryParam("id") Integer id) {
		horarioService.eliminaHorario(id);
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
}