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

import mx.gob.segob.dgtic.business.service.LicenciaMedicaService;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("catalogo")
@Component
public class LicenciaMedicaRecurso {
	
	@Autowired
	private LicenciaMedicaService licenciaMedicaService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("obtieneLicenciasMedicas")	
	@PermitAll
	public Response obtieneLicenciasMedicas() {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.obtenerListaLicenciaMedica());
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("buscaLicenciaMedica")	
	@PermitAll
	public Response buscaLicenciaMedica(@QueryParam("id") Integer id) {

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, licenciaMedicaService.buscaLicenciaMedica(id));
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("modificaLicenciaMedica")	
	@PermitAll
	public Response modificaLicenciaMedica(@RequestParam String jsonLicenciaMedica) {
		JsonObject jsonObject = new JsonParser().parse(jsonLicenciaMedica).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		LicenciaMedicaDto licenciaMedicaDto = gson.fromJson(jsonObject.get("licenciaMedica"), LicenciaMedicaDto.class);
		
		licenciaMedicaService.modificaLicenciaMedica(licenciaMedicaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("agregaLicenciaMedica")	
	@PermitAll
	public Response agregaLicenciaMedica(@RequestParam String jsonLicenciaMedica) {
		JsonObject jsonObject = new JsonParser().parse(jsonLicenciaMedica).getAsJsonObject();
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		LicenciaMedicaDto licenciaMedicaDto = gson.fromJson(jsonObject.get("licenciaMedica"), LicenciaMedicaDto.class);
		
		licenciaMedicaService.agregaLicenciaMedica(licenciaMedicaDto);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("eliminaLicenciaMedica")	
	@PermitAll
	public Response eliminaLicenciaMedica(@QueryParam("id") Integer id) {
		
		licenciaMedicaService.eliminaLicenciaMedica(id);

		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, "");
	}

}
