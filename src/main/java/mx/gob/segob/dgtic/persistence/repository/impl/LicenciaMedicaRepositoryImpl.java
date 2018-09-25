package mx.gob.segob.dgtic.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.gob.segob.dgtic.comun.sicoa.dto.ArchivoDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.EstatusDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.LicenciaMedicaDto;
import mx.gob.segob.dgtic.comun.sicoa.dto.UsuarioDto;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.LicenciaMedicaRepository;

@Repository
public class LicenciaMedicaRepositoryImpl implements LicenciaMedicaRepository{

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

	@Override
	public List<LicenciaMedicaDto> obtenerListaLicenciaMedica() {
		StringBuilder qry = new StringBuilder();
        qry.append("SELECT id_licencia, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, padecimiento ");
        qry.append("FROM m_licencia_medica ");
        
        List<Map<String, Object>> licenciasMedicas = jdbcTemplate.queryForList(qry.toString());
        List<LicenciaMedicaDto> listaLicenciaMedica = new ArrayList<>();
        
        for (Map<String, Object> licenciaMedica : licenciasMedicas) {
        	LicenciaMedicaDto licenciaMedicaDto = new LicenciaMedicaDto();
        	licenciaMedicaDto.setIdLicencia((Integer)licenciaMedica.get("id_licencia"));
        	UsuarioDto usuarioDto= new UsuarioDto();
        	usuarioDto.setIdUsuario((Integer)licenciaMedica.get("id_usuario"));
        	licenciaMedicaDto.setIdUsuario(usuarioDto);
        	licenciaMedicaDto.setIdResponsable((Integer)licenciaMedica.get("id_responsable"));
        	ArchivoDto archivoDto = new ArchivoDto();
        	archivoDto.setIdArchivo((Integer)licenciaMedica.get("id_archivo"));
        	licenciaMedicaDto.setIdArchivo(archivoDto);
        	EstatusDto estatusDto= new EstatusDto();
    		estatusDto.setIdEstatus((Integer)licenciaMedica.get("id_estatus"));
    		licenciaMedicaDto.setIdEstatus(estatusDto);
    		licenciaMedicaDto.setFechaInicio((Date)licenciaMedica.get("fecha_inicio"));
    		licenciaMedicaDto.setFechaFin((Date)licenciaMedica.get("fecha_fin"));
    		licenciaMedicaDto.setDias((Integer)licenciaMedica.get("dias"));
    		licenciaMedicaDto.setPadecimiento((String) licenciaMedica.get("padecimiento"));
    		listaLicenciaMedica.add(licenciaMedicaDto);
    	}
     return listaLicenciaMedica;
	}

	@Override
	public LicenciaMedicaDto buscaLicenciaMedica(Integer idLicencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("SELECT id_licencia, id_usuario, id_responsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, padecimiento ");
        qry.append("FROM m_licencia_medica ");
        qry.append("WHERE id_licencia = :idLicencia");
        
        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("idLicencia", idLicencia);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<LicenciaMedicaDto>(LicenciaMedicaDto.class));
	}

	@Override
	public void modificaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("UPDATE M_LICENCIA_MEDICA SET id_estatus= :idEstatus, fecha_inicio = :fechaInicio, fecha_fin = :fechaFin, dias = :dias, padecimiento = :padecimiento ");
		qry.append("WHERE id_licencia = :idLicencia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idLicencia", licenciaMedicaDto.getIdLicencia());
		parametros.addValue("idEstatus", licenciaMedicaDto.getIdEstatus().getIdEstatus());
		parametros.addValue("fechaInicio", licenciaMedicaDto.getFechaInicio());
		parametros.addValue("fechaFin", licenciaMedicaDto.getFechaFin());
		parametros.addValue("dias", licenciaMedicaDto.getDias());
		parametros.addValue("padecimiento", licenciaMedicaDto.getPadecimiento());
		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void agregaLicenciaMedica(LicenciaMedicaDto licenciaMedicaDto) {
		StringBuilder qry = new StringBuilder();
		qry.append("INSERT INTO M_LICENCIA_MEDICA (id_usuario, id_reponsable, id_archivo, id_estatus, fecha_inicio, fecha_fin, dias, padecimiento ) ");
		qry.append("VALUES (:idUsuario, :idResponsable, :idArchivo, :idEstatus, :fechaInicio, :fechaFin, :dias, :padecimiento) ");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idUsuario", licenciaMedicaDto.getIdUsuario().getIdUsuario());
		parametros.addValue("idResponsable", licenciaMedicaDto.getIdResponsable());
		parametros.addValue("idArchivo", licenciaMedicaDto.getIdArchivo().getIdArchivo());
		parametros.addValue("idEstatus", licenciaMedicaDto.getIdEstatus().getIdEstatus());
		parametros.addValue("fechaInicio", licenciaMedicaDto.getFechaInicio());
		parametros.addValue("fechaFin", licenciaMedicaDto.getFechaFin());
		parametros.addValue("dias", licenciaMedicaDto.getDias());
		parametros.addValue("padecimiento", licenciaMedicaDto.getPadecimiento());

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}

	@Override
	public void eliminaLicenciaMedica(Integer idLicencia) {
		StringBuilder qry = new StringBuilder();
		qry.append("DELETE FROM M_LICENCIA_MEDICA WHERE id_licencia = :idLicencia");
		
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("idLicencia", idLicencia);

		nameParameterJdbcTemplate.update(qry.toString(), parametros);
		
	}
}
