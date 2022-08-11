/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.inec.captura.ejb.crones;


import ec.gob.inec.captura.ejb.facade.CaptCabeceraFacade;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 *
 * @author Alumno
 */
@Singleton
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class Cron1 {


   
   
    @EJB
    private CaptCabeceraFacade cabeceraDao;
    

    @Schedule(hour = "23", minute = "59", dayOfWeek = "1-6")
    public void ejecucionSentenciaNativa1() {
        try {
            String sql="SELECT captura.sipe_tareas(1);";
            cabeceraDao.ejecutarQueryNativo(sql);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
