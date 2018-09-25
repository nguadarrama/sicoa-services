//package mx.gob.segob.dgtic.persistence.repository.impl;
//
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import mx.gob.segob.dgtic.comun.sicoa.dto.AsistenciaDto;
//import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
//import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
//import mx.gob.segob.dgtic.persistence.repository.AsistenciaRepository;
//
//@Repository
//public class AsistenciaRespositoryImpl1 implements AsistenciaRepository {
//	@Autowired
//    private JdbcTemplate jdbcTemplate;
//	
//	@Autowired
//    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
//	
//	@Override
//    public List<AsistenciaDto> obtenerListaAsistencia() {
//
//        StringBuilder qry = new StringBuilder();
//        qry.append("SELECT id_usuario, entrada, salida ");
//        qry.append("FROM m_asistencia limit 1000");
//        
//        List<Map<String, Object>> asistencias = jdbcTemplate.queryForList(qry.toString());
//        List<AsistenciaDto> listaAsistencia = new ArrayList<>();
//        
//        for (Map<String, Object> a : asistencias) {
//    		AsistenciaDto asistencia = new AsistenciaDto();
//    		UsuarioDto usuarioDto = new UsuarioDto();
//    		usuarioDto.setClaveUsuario((String) a.get("id_usuario"));
//    		
//    		asistencia.setUsuarioDto(usuarioDto);
//    		asistencia.setEntrada((Timestamp) a.get("entrada"));
//    		asistencia.setSalida((Timestamp) a.get("salida"));
//    		
//    		listaAsistencia.add(asistencia);
//    	}
//
//        
//        return listaAsistencia;
//    }
//
//	@Override
//	public AsistenciaDto buscaAsistencia(int idUsuario) {
//
//		StringBuilder qry = new StringBuilder();
//		qry.append("SELECT id_usuario, entrada, salida ");
//        qry.append("FROM m_asistencia ");
//        qry.append("WHERE id_usuario = :idUsuario");
//
//        MapSqlParameterSource parametros = new MapSqlParameterSource();
//        parametros.addValue("idUsuario", idUsuario);
//
//        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<AsistenciaDto>(AsistenciaDto.class));
//	}
//
//	@Override
//	public void eliminaAsistencia(Integer idAsistencia) {
//		StringBuilder qry = new StringBuilder();
//		qry.append("DELETE FROM m_asistencia WHERE id_asistencia = :idAsistencia");
//		
//		MapSqlParameterSource parametros = new MapSqlParameterSource();
//		parametros.addValue("idAsistencia", idAsistencia);
//
//		nameParameterJdbcTemplate.update(qry.toString(), parametros);
//		
//	}
//}
