package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;
import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Ucase extends Column {

	public Ucase(String name) {
		super(name);
	}

	public Ucase(String table, String name) {
		super(table, name);
	}

	public Ucase(Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("UCASE(");
		super.nameInFunction(sb);
		sb.append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

}