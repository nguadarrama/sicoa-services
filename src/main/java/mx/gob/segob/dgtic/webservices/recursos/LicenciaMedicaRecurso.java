package mx.gob.segob.dgtic.webservices.recursos;

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

import mx.gob.segob.dgtic.business.service.LicenciaMedicaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDtoAux;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("licencia")
@Component
public class LicenciaMedicaRecurso {
	
	@Autowired
	private LicenciaMedicaService licenciaMedicaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneLicenciasMedicas")	
	public Response obtieneLicenciasMedicas() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.obtenerListaLicenciaMedica());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaLicenciaMedica")	
	public Response buscaLicenciaMedica(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.buscaLicenciaMedica(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaLicenciaMedica")	
	public Response modificaLicenciaMedica(@RequestParam String jsonLicenciaMedica) {
		JsonObject jsonObject = new JsonParser().parse(jsonLicenciaMedica).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		LicenciaMedicaDtoAux licenciaMedicaDto = gson.fromJson(jsonObject.get("licenciaMedica"), LicenciaMedicaDtoAux.class);
		
		

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,licenciaMedicaService.modificaLicenciaMedica(licenciaMedicaDto));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaLicenciaMedica")	
	public Response agregaLicenciaMedica(@RequestParam String jsonLicenciaMedica) {
		JsonObject jsonObject = new JsonParser().parse(jsonLicenciaMedica).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		LicenciaMedicaDtoAux licenciaMedicaDto = gson.fromJson(jsonObject.get("licenciaMedica"), LicenciaMedicaDtoAux.class);
		
		

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK,licenciaMedicaService.agregaLicenciaMedica(licenciaMedicaDto));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaLicenciaMedica")	
	public Response eliminaLicenciaMedica(@QueryParam("id") Integer id) {
		
		licenciaMedicaService.eliminaLicenciaMedica(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneListaLicenciaMedicaPorFiltros")	
	public Response obtieneListaLicenciaMedicaPorFiltros(@QueryParam("claveUsuario") String claveUsuario,@QueryParam("idEstatus") String idEstatus, 
			@QueryParam("fechaInicio") String fechaInicio, @QueryParam("fechaFin") String fechaFin) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.obtenerListaLicenciaMedicaPorFiltros(claveUsuario, fechaInicio, fechaFin, idEstatus));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneListaLicenciaMedicaEmpleados")	
	public Response obtieneListaLicenciaMedicaEmpleados(@QueryParam("claveUsuario") String claveUsuario,@QueryParam("idEstatus") String idEstatus, 
			@QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno") String apellidoMaterno,
			@QueryParam("idUnidad") String idUnidad) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.obtenerListaLicenciaMedicaEmpleados(claveUsuario, nombre, apellidoPaterno, apellidoMaterno, idEstatus, idUnidad));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneLicenciasPorUnidad")	
	public Response obtieneLicenciasPorUnidad(@QueryParam("idUnidad") String idUnidad, @QueryParam("claveUsuario") String claveUsuario,
			@QueryParam("nombre") String nombre, @QueryParam("apellidoPaterno") String apellidoPaterno, @QueryParam("apellidoMaterno")String apellidoMaterno) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.obtenerLicenciasPorUnidad(idUnidad, claveUsuario, nombre, apellidoPaterno, apellidoMaterno));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaDiasLicenciaMedica")	
	public Response buscaDiasLicenciaMedica(@QueryParam("claveUsuario") String claveUsuario) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.consultaDiasLicenciaMedica(claveUsuario));
	}

}
