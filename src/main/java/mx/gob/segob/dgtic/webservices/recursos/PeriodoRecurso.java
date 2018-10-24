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

import mx.gob.segob.dgtic.business.service.PeriodoService;
import mx.gob.segob.dgtic.comun.sicoa.dto.PeriodoDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;
@Path("catalogo")
@Component
public class PeriodoRecurso {

	
	
		
		@Autowired
		private PeriodoService periodoService;
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("obtienePeriodos")	
		@PermitAll
		public Response obtienePeriodos() {

			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, periodoService.obtenerListaPeriodos());
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("buscaPeriodo")	
		@PermitAll
		public Response buscaPeriodo(@QueryParam("id") Integer id) {

			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, periodoService.buscaPeriodo(id));
		}
		
		@PUT
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("modificaPeriodo")	
		@PermitAll
		public Response modificaPeriodo(@RequestParam String jsonPeriodo) {
			JsonObject jsonObject = new JsonParser().parse(jsonPeriodo).getAsJsonObject();
			
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			PeriodoDto periodoDto = gson.fromJson(jsonObject.get("periodo"), PeriodoDto.class);
			
			periodoService.modificaPeriodo(periodoDto);

			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		}
		
		@PUT
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("agregaPeriodo")	
		@PermitAll
		public Response agregaPeriodo(@RequestParam String jsonPeriodo) {
			JsonObject jsonObject = new JsonParser().parse(jsonPeriodo).getAsJsonObject();
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			PeriodoDto periodoDto = gson.fromJson(jsonObject.get("periodo"), PeriodoDto.class);
			periodoDto = periodoService.agregaPeriodo(periodoDto);
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, periodoDto);
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("eliminaPeriodo")	
		@PermitAll
		public Response eliminaPeriodo(@QueryParam("id") Integer id) {
			
			periodoService.eliminaPeriodo(id);

			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("buscaPeriodoPorClaveUsuario")	
		@PermitAll
		public Response buscaPeriodoPorClaveUsuario(@QueryParam("claveUsuario") String claveUsuario) {
			
			PeriodoDto periodo=periodoService.buscaPeriodoPorClaveUsuario(claveUsuario);
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,periodo );
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("generaPeriodoVacacional")	
		@PermitAll
		public Response generaPeriodoVacacional(@RequestParam String inicio, @RequestParam String descripcion,@RequestParam boolean activo) {
			System.out.println("datos recibidos: "+" fechhaInicio: "+inicio+" descripcion: "+descripcion+" activo: "+activo);
			periodoService.generaPeriodoVacacional(inicio, descripcion, activo);
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
		}
		
		@PUT
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		@Path("modificaEstatusPeriodo")	
		@PermitAll
		public Response modificaestatusPeriodo(@RequestParam String jsonPeriodo) {
			JsonObject jsonObject = new JsonParser().parse(jsonPeriodo).getAsJsonObject();
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			PeriodoDto periodoDto = gson.fromJson(jsonObject.get("periodo"), PeriodoDto.class);
			System.out.println("periodoRecurso method--modificaestatusPeriodo-- idPeriodo: "+periodoDto.getIdPeriodo()+" +activo: "+periodoDto.getActivo() );
			periodoDto = periodoService.cambiaEstatusPeriodo(periodoDto);
			return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, periodoDto);
		}
	
}
