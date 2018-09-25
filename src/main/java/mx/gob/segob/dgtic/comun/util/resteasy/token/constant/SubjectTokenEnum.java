/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.comun.util.resteasy.token.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * Constante que identifica los asuntos para los que se puede generar un token
 */
public enum SubjectTokenEnum {
    
    /**
     * Constante que identifica el asunto de autenticaci&oacute;n
     */
    AUTENTICACION("AUTENTICACION"),
    
    /**
     * Constante que identifica el asunto de acceso
     */
    ACCESO("ACCESO");

    /**
     * Descripcion del asunto
     */
    private String subject;

    /**
     * Instancia la informaci&oacute;n del token de asunto
     *
     * @param subject La descripci&oacute;n del asunto.
     */
    private SubjectTokenEnum(String subject){
        this.subject = subject;
    }

    /**
     * Obtiene la descripcion de la constante del asunto
     *
     * @return la descripcion de la constante del asunto
     */
    public String getSubject(){
        return this.subject;
    }

    /**
     * Busca la constante que corresponda a la descripcion del asunto
     *
     * @param subject La descripci&oacute;n del asunto a buscar
     * @return La constante que corresponde a la descripci&oacute;n
     */
    public static SubjectTokenEnum findBySubject(String subject){
        SubjectTokenEnum subjectEnum = null;
        if(!StringUtils.isEmpty(subject)){
            for(SubjectTokenEnum subjecttoken : SubjectTokenEnum.values()){
                if(subjecttoken.getSubject().equals(subject)){
                    subjectEnum = subjecttoken;
                    break;
                }
            }
        }
        return subjectEnum;
    }
}
