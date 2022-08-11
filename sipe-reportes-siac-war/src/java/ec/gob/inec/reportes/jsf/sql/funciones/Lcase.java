package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;
import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Lcase extends Column {

	public Lcase(String name) {
		super(name);
	}

	public Lcase(String table, String name) {
		super(table, name);
	}

	public Lcase(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("LCASE(");
		nameInFunction(sb);
		sb.append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

}