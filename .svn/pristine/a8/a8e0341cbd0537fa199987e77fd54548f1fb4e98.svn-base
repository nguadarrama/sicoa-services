/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.test.business.demo;

import mx.gob.segob.dgtic.business.service.DemoService;
import mx.gob.segob.dgtic.comun.transport.dto.demo.MapeoComplejoPadreDTO;
import mx.gob.segob.dgtic.comun.util.crypto.HashUtils;
import mx.gob.segob.dgtic.comun.transport.dto.demo.DemoAnotacionesMapperDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Hp6460b on 05/10/2017.
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestAppConfig.class})
@Transactional*/
public class DemoTestUnit {

        /**
         * The logger.
         */
        private static Logger logger  = LoggerFactory.getLogger(DemoTestUnit.class);

       /**
        * The service.
        */
       @Autowired
        private DemoService service;

        /**
         * Test.
         */
        //@Test
        public void test() {
                DemoAnotacionesMapperDTO dto = service.obtenerUsuarioByAnotaciones("ADMIN");
            logger.info(" DEMO : "+dto.getNombre());
        }
        
        /**
         * Test mappeo complejo.
         */
        //@Test
        public void testMappeoComplejo() {
        	
        	logger.info(HashUtils.md5("password"));
        	MapeoComplejoPadreDTO dto = service.obtenerUsuarioMapeoComplejo("ADMIN");
            logger.info(" DEMO : "+dto.getIdUsuario()+" "+dto.getUsuarioNombre()+" "+dto.getChild());
            logger.info("		 CHILD : "+dto.getChild()+" "+dto.getChild().getDescripcionPerfil());
            
            
        }
    }