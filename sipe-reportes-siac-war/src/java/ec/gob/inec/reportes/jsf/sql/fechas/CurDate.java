package ec.gob.inec.reportes.jsf.sql.fechas;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;

public class CurDate extends Column {

    public static final CurDate now = new CurDate(null);

    public CurDate(String alias) {
        super(null);
        as(alias);
    }

    @Override
    public void nameInColumn(StringBuilder sb) {
        if (nameAlias == null) {
            sb.append("CURDATE()");
        } else {
            sb.append("CURDATE() AS `").append(nameAlias).append('`');
        }
    }

    @Override
    public void nameInWhere(StringBuilder sb) {
        sb.append("CURDATE()");
    }
}
