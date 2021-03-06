package mx.gob.segob.dgtic.persistence.repository.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.DiaFestivoDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.DiaFestivoRepository;
import mx.gob.segob.dgtic.persistence.repository.constants.RepositoryConstants;

@Repository
public class DiaFestivoRepositoryImpl implements DiaFestivoRepository {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;
	
	@Override
	public List<DiaFestivoDto> obtenerListaDiasFestivos(){
	
		StringBuilder qry = new StringBuilder();
        qry.append(RepositoryConstants.DL31);
        qry.append(RepositoryConstants.DL32);
        
        List<Map<String, Object>> diasFestivos = jdbcTemplate.queryForList(qry.toString());
        List<DiaFestivoDto> listaDiasFestivos = new ArrayList<>();
        
        for (Map<String, Object> diaFestivo : diasFestivos) {
        	DiaFestivoDto diaFestivoDTO = new DiaFestivoDto();
        	diaFestivoDTO.setIdDiaFestivo((Integer)diaFestivo.get(RepositoryConstants.ID_FESTIVO));
        	diaFestivoDTO.setNombre((String)diaFestivo.get(RepositoryConstants.NOMBRE));
        	diaFestivoDTO.setFecha((Date)diaFestivo.get(RepositoryConstants.FECHA));
        	diaFestivoDTO.setActivo((Boolean)diaFestivo.get(RepositoryConstants.ACTIVO));
        	listaDiasFestivos.add(diaFestivoDTO);
    	}
     return listaDiasFestivos;
	}
	
	@Override
	public List<DiaFestivoDto> obtenerListaDiasCatalogo(){
	
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_festivo, nombre, fecha, activo ");
        qry.append("FROM c_dia_festivo ");
        
        List<Map<String, Object>> diasFestivos = jdbcTemplate.queryForList(qry.toString());
        List<DiaFestivoDto> listaDiasFestivos = new ArrayList<>();
        
        for (Map<String, Object> diaFestivo : diasFestivos) {
        	DiaFestivoDto diaFestivoDTO = new DiaFestivoDto();
        	diaFestivoDTO.setIdDiaFestivo((Integer)diaFestivo.get(RepositoryConstants.ID_FESTIVO));
        	diaFestivoDTO.setNombre((String)diaFestivo.get(RepositoryConstants.NOMBRE));
        	diaFestivoDTO.setFecha((Date)diaFestivo.get(RepositoryConstants.FECHA));
        	diaFestivoDTO.setActivo((Boolean)diaFestivo.get(RepositoryConstants.ACTIVO));
        	listaDiasFestivos.add(diaFestivoDTO);
    	}
     return listaDiasFestivos;
	}
	
	@Override
	public DiaFestivoDto buscaDiaFestivo (Integer idFestivo){
		
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_festivo, nombre, fecha, activo ");
        qry.append("FROM c_dia_festivo ");
        qry.append("WHERE id_festivo = :id_festivo ");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue(RepositoryConstants.ID_FESTIVO, idFestivo);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<DiaFestivoDto>(DiaFestivoDto.class));
	}
	
	@Override
	public DiaFestivoDto modificaDiaFestivo (DiaFestivoDto diaFestivo){
		
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE c_dia_festivo SET nombre= :nombre, fecha = :fecha, activo = :activo ");
		qry.append("WHERE id_festivo = :idDia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue(RepositoryConstants.ID_DIA2, diaFestivo.getIdDiaFestivo());
		parametros.addValue(RepositoryConstants.NOMBRE, diaFestivo.getNombre());
		parametros.addValue(RepositoryConstants.FECHA, diaFestivo.getFecha());
		parametros.addValue(RepositoryConstants.ACTIVO, diaFestivo.getActivo());

		try{
			Integer i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1){
				diaFestivo.setMensaje("El Día Inhábil se ha actualizado correctamente.");
			}else{
				diaFestivo.setMensaje("Se ha generado un error al guardar, revise la información");
			}
		}catch(Exception e){
			
			diaFestivo.setMensaje("El registro ya existe en el sistema, favor de validar");
		}
		return diaFestivo;
		
	}
	
	@Override 
	public DiaFestivoDto agregaDiaFestivo (DiaFestivoDto diaFestivo){
		
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO c_dia_festivo (nombre, fecha, activo) ");
		qry.append("VALUES (:nombre, :fecha, :activo) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("nombre", diaFestivo.getNombre());
		parametros.addValue("fecha", diaFestivo.getFecha());
		parametros.addValue("activo", diaFestivo.getActivo());
		try{
			Integer i = nameParameterJdbcTemplate.update(qry.toString(), parametros);
			if(i == 1){
				diaFestivo.setMensaje("El Día Inhábil se ha registrado correctamente.");
			}else{
				diaFestivo.setMensaje("Se ha generado un error al guardar, revise la información");
			}
		}catch(Exception e){
			
			diaFestivo.setMensaje("El registro ya existe en el sistema, favor de validar");
		}
		return diaFestivo;
	}
	
	@Override
	public void eliminaDiaFestivo (Integer idDia){
		
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM c_dia_festivo WHERE id_festivo = :idDia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idDia", idDia);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
	}

	@Override
	public List<DiaFestivoDto> obtenerDiasFestivosActivos() {
		StringBuilder qry = new StringBuilder();
        qry.append("select id_festivo, nombre, fecha, activo ");
        qry.append("from c_dia_festivo ");
        qry.append("where activo=true ");
        
        List<Map<String, Object>> diasFestivos = jdbcTemplate.queryForList(qry.toString());
        List<DiaFestivoDto> listaDiasFestivos = new ArrayList<>();
        
        for (Map<String, Object> diaFestivo : diasFestivos) {
        	DiaFestivoDto diaFestivoDTO = new DiaFestivoDto();
        	diaFestivoDTO.setIdDiaFestivo((Integer)diaFestivo.get("id_festivo"));
        	diaFestivoDTO.setNombre((String)diaFestivo.get("nombre"));
        	diaFestivoDTO.setFecha((Date)diaFestivo.get("fecha"));
        	diaFestivoDTO.setActivo((Boolean)diaFestivo.get("activo"));
        	listaDiasFestivos.add(diaFestivoDTO);
    	}
     return listaDiasFestivos;
	}
	
}
