package mx.gob.segob.dgtic.webservices.recursos;

import java.util.ArrayList;
import java.util.List;

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
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("buscaVacacionPeriodoPorClaveUsuarioYPeriodo")	
	@PermitAll
	public Response buscaVacacionPeriodoPorClaveUsuarioYPeriodo(@RequestParam String jsonVacaciones ) {
		JsonObject jsonObject = new JsonParser().parse(jsonVacaciones).getAsJsonObject();
		String datoUsuario="claveUsuario";
		String usuario =""+ jsonObject.get(datoUsuario);
		usuario=usuario.replace("\"", "");
		System.out.println("clave para usuario "+usuario);
		String dato="idPeriodo";
		String id =""+ jsonObject.get(dato);
		id=id.replace("\"", "");
		System.out.println("id "+id);
		
				Integer idPeriodo=Integer.parseInt(id);
				System.out.println("clave para periodo "+idPeriodo);
				return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, vacacionPeriodoService.consultaVacacionPeriodoPorClaveUsuarioYPeriodo(idPeriodo, usuario));
		        
			
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtenerUsuariosVacacionesPorFiltros")	
	@PermitAll
	public Response obtenerUsuariosVacacionesPorFiltros(@QueryParam("claveUsuario") String claveUsuario, @QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno") String apellidoMaterno, @QueryParam("idUnidad") String idUnidad) {
		
		List<VacacionPeriodoDto> lista = new ArrayList<>();
		lista= vacacionPeriodoService.obtenerUsuariosConVacacionesPorFiltros(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idUnidad);
		System.out.println("pasa por aqui "+lista.size());
		for(VacacionPeriodoDto vacacion: lista){
			System.out.println("DatoSSSSSSSSSSSSSSSSSSSSSSSS"+vacacion.getIdUsuario().getClaveUsuario());
		}
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, lista);
	}
	
}
