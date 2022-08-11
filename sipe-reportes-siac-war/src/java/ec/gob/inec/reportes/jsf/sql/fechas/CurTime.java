package ec.gob.inec.reportes.jsf.sql.fechas;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;

public class CurTime extends Column {

    public static final CurTime now = new CurTime(null);

    public CurTime(String alias) {
        super(null);
        as(alias);
    }

    @Override
    public void nameInColumn(StringBuilder sb) {
        if (nameAlias == null) {
            sb.append("CURTIME()");
        } else {
            sb.append("CURTIME() AS `").append(nameAlias).append('`');
        }
    }

    @Override
    public void nameInWhere(StringBuilder sb) {
        sb.append("CURTIME()");
    }
}
