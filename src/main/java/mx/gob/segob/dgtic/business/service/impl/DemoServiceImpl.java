/*
* ****************************************************
* * Aplicaci&oacute;n Base
* * Versi&oacute;n 1.0.0
* * Secretar&iacute;a de Gobernaci&oacute;n - DGTIC
* ****************************************************
*/
package mx.gob.segob.dgtic.business.service.impl;

import mx.gob.segob.dgtic.business.service.DemoService;
import mx.gob.segob.dgtic.business.service.base.ServiceBase;
import mx.gob.segob.dgtic.comun.transport.dto.demo.MapeoComplejoPadreDTO;
import mx.gob.segob.dgtic.comun.transport.dto.demo.TablaDemo;
import mx.gob.segob.dgtic.comun.transport.dto.demo.DemoAnotacionesMapperDTO;
import mx.gob.segob.dgtic.persistence.repository.DemoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hp6460b on 05/10/2017.
 */
@Service
public class DemoServiceImpl extends ServiceBase implements DemoService {

    /**
     * The demo repository.
     */
    @Autowired
    private DemoRepository demoRepository;

    /* (non-Javadoc)
     * @see mx.gob.segob.dgtic.business.service.DemoService#obtenerUsuarioByAnotaciones(java.lang.String)
     */
    @Override
    @Transactional
    public DemoAnotacionesMapperDTO obtenerUsuarioByAnotaciones(String nombreUsuario){
        DemoAnotacionesMapperDTO demo;
        demo = demoRepository.obtenerUsuarioByAnotaciones(nombreUsuario);
        return demo;
    }

    /* (non-Javadoc)
     * @see mx.gob.segob.dgtic.business.service.DemoService#obtenerUsuarioMapeoComplejo(java.lang.String)
     */
    @Override
    @Transactional
    public MapeoComplejoPadreDTO obtenerUsuarioMapeoComplejo(String nombreUsuario){
    	MapeoComplejoPadreDTO demo;
        demo = demoRepository.obtenerUsuarioMapeoComplejo(nombreUsuario);       
        return demo;
    }
    
    
    @Override
    @Transactional
    public List<TablaDemo> obtenerDatosDemoPagination(Integer registroInicial, Integer numeroRegistros){
    	
    	//Se simula una busqueda paginada
    	List<TablaDemo> tablaCompleto = obtenerListaCompletaDemo();
    	int registroFinal = (registroInicial+numeroRegistros < tablaCompleto.size() )?registroInicial+numeroRegistros : tablaCompleto.size();
        return tablaCompleto.subList(registroInicial, registroFinal);
    }
    
    @Override
    @Transactional
    public int obtenertotalregistrosPaginar(){
    	return obtenerListaCompletaDemo().size();
    }
    
    private List<TablaDemo> obtenerListaCompletaDemo(){
    	
    	
    	ArrayList<TablaDemo> tablaDemo = new ArrayList<>(0);
    	
    	
    	String [] departamentos = new String[]{"Econom\u00eda", "Contadur\u00eda", "F\u00edsica", "Sistemas"};
    	
    	
    	for(int i = 1 ; i<=12 ; i++){
    		
    		int deptoIdx = ThreadLocalRandom.current().nextInt(0, 4);
    		TablaDemo registro = new TablaDemo();
    		registro.setUsuario("usuario "+i);
            registro.setDepartamento(departamentos[deptoIdx]);
            tablaDemo.add(registro);	
    	}        
        return tablaDemo;
    }
}
