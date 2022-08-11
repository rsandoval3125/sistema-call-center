
package ec.gob.inec.captuta.jsf.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UtilsDate {
    
    private static Locale locale = new Locale("es", "EC");
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat formatoFechayyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    
    
    public static String fechaSistema() {
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                        locale);
        return formato.format(fecha);
    }
    
    public static Date fechaString_Date(String fecha) {
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(UtilsDate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }
    
    public static String DeDateAString(Date fecha) {
        DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
        return fechaHora.format(fecha);
    }
    
    public static Date DeStringADate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String strFecha = fecha;
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(strFecha);
            return fechaDate;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return fechaDate;
        }
    }
    
    public static boolean fecha1EsMayorIgualFecha2(Date fecha1, Date fecha2) {
		
        fecha1 = UtilsDate.DeStringADate(UtilsDate.DeDateAString(fecha1));
        fecha2 = UtilsDate.DeStringADate(UtilsDate.DeDateAString(fecha2));

        if (fecha1.compareTo(fecha2) == 0)
                return true;
        else if (fecha1.compareTo(fecha2) > 0)
                return true;
        else
                return false;
    }
    
    public static int diferenciaEntreFechas(Date a, Date b) {
		int tempDifference = 0;
		int difference = 0;
		Calendar earlier = Calendar.getInstance();
		Calendar later = Calendar.getInstance();

		if (a.compareTo(b) < 0) {
			earlier.setTime(a);
			later.setTime(b);
		} else {
			earlier.setTime(b);
			later.setTime(a);
		}

		while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
			tempDifference = 365 * (later.get(Calendar.YEAR) - earlier.get(Calendar.YEAR));
			difference += tempDifference;

			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		if (earlier.get(Calendar.DAY_OF_YEAR) != later.get(Calendar.DAY_OF_YEAR)) {
			tempDifference = later.get(Calendar.DAY_OF_YEAR) - earlier.get(Calendar.DAY_OF_YEAR);
			difference += tempDifference;

			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		return difference;
	}

    
    
    
}
