/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.persistence.repository.impl;

import mx.gob.segob.dgtic.comun.transport.dto.demo.MapeoComplejoPadreDTO;
import mx.gob.segob.dgtic.comun.transport.dto.demo.DemoAnotacionesMapperDTO;
import mx.gob.segob.dgtic.comun.util.mapper.RowAnnotationBeanMapper;
import mx.gob.segob.dgtic.persistence.repository.DemoRepository;
import mx.gob.segob.dgtic.persistence.repository.base.RepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Hp6460b on 05/10/2017.
 */
@Repository
public class DemoRepositoryImpl extends RepositoryBase implements DemoRepository {

    /**
     * The jdbc template.
     */
    @SuppressWarnings("unused")
	@Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * The name parameter jdbc template.
     */
    @Autowired
    private NamedParameterJdbcTemplate nameParameterJdbcTemplate;

    /* (non-Javadoc)
     * @see mx.gob.segob.dgtic.persistence.repository.DemoRepository#obtenerUsuarioByAnotaciones(java.lang.String)
     */
    @Override
    public DemoAnotacionesMapperDTO obtenerUsuarioByAnotaciones(String nombreUsuario){


        StringBuilder qry = new StringBuilder();
        qry.append(" SELECT cve_m_usuario ");
        qry.append(" FROM M_USUARIO  ");
        qry.append(" WHERE cve_m_usuario = :usuarioNombre  ");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("usuarioNombre", nombreUsuario);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<>(DemoAnotacionesMapperDTO.class));
    }
    
    /* (non-Javadoc)
     * @see mx.gob.segob.dgtic.persistence.repository.DemoRepository#obtenerUsuarioMapeoComplejo(java.lang.String)
     */
    @Override
    public MapeoComplejoPadreDTO obtenerUsuarioMapeoComplejo(String nombreUsuario){

        StringBuilder qry = new StringBuilder();
        qry.append(" SELECT a.ID_M_USUARIO, a.USUARIO, a.EN_SESION, a.ULTIMO_ACCESO, ");
        qry.append("        c.ID_C_PERFIL, c.DESCRIPCION DESCRIPCION_PERFIL ");
        qry.append(" FROM M_USUARIO a ");
        qry.append(" INNER JOIN D_USUARIO_PERFIL b ON b.ID_M_USUARIO = a.ID_M_USUARIO  ");
        qry.append(" INNER JOIN C_PERFIL c ON c.ID_C_PERFIL = b.ID_C_PERFIL  ");
        qry.append(" WHERE USUARIO = :usuarioNombre  ");

        MapSqlParameterSource parametros = new MapSqlParameterSource();
        parametros.addValue("usuarioNombre", nombreUsuario);

        return nameParameterJdbcTemplate.queryForObject(qry.toString(), parametros, new RowAnnotationBeanMapper<MapeoComplejoPadreDTO>(MapeoComplejoPadreDTO.class));
    }

}
