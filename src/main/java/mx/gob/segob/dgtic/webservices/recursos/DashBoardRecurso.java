package mx.gob.segob.dgtic.webservices.recursos;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mx.gob.segob.dgtic.business.service.DashService;
import mx.gob.segob.dgtic.comun.transport.constants.StatusResponse;
import mx.gob.segob.dgtic.webservices.util.ResponseJSONGenericoUtil;

@Path("dashBoard")
@Component
public class DashBoardRecurso {
	
	@Autowired 
	private DashService dashService;

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("top")	
	@PermitAll
	public Response buscaJustificacion(@QueryParam("id") Integer id) {
		return ResponseJSONGenericoUtil.getRespuestaExito(StatusResponse.OK, dashService.dashBoard(id));
	}

}
