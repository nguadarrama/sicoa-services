/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.transport.dto.demo;

import mx.gob.segob.dgtic.comun.util.mapper.annotations.MapeaColumna;

/**
 * Created by Hp6460b on 05/10/2017.
 */
public class DemoAnotacionesMapperDTO {


    /**
     * The id usuario.
     */
    @MapeaColumna(columna = "cve_m_usuario") private String idUsuario;
    
    /**
     * The nombre.
     */
    @MapeaColumna(columna = "USUARIO")     private String nombre;
    
    /**
     * The appaterno.
     */
    @MapeaColumna(columna = "AP_PATERNO") private String appaterno;
    
    /**
     * The apmaterno.
     */
    @MapeaColumna(columna = "AP_MATERNO") private String apmaterno;

    /**
     * Gets the id usuario.
     *
     * @return the id usuario
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Sets the id usuario.
     *
     * @param idUsuario the new id usuario
     */
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Gets the nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the nombre.
     *
     * @param nombre the new nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the appaterno.
     *
     * @return the appaterno
     */
    public String getAppaterno() {
        return appaterno;
    }

    /**
     * Sets the appaterno.
     *
     * @param appaterno the new appaterno
     */
    public void setAppaterno(String appaterno) {
        this.appaterno = appaterno;
    }

    /**
     * Gets the apmaterno.
     *
     * @return the apmaterno
     */
    public String getApmaterno() {
        return apmaterno;
    }

    /**
     * Sets the apmaterno.
     *
     * @param apmaterno the new apmaterno
     */
    public void setApmaterno(String apmaterno) {
        this.apmaterno = apmaterno;
    }
}
