package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;
import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Round extends Column {
	public int decimals;

	public Round(String name, int decimals) {
		super(name);
		this.decimals = decimals;
	}

	public Round(String table, String name, int decimals) {
		super(table, name);
		this.decimals = decimals;
	}

	public Round(Select select, String tableAlias, String name, int decimals) {
		super(select, tableAlias, name);
		this.decimals = decimals;
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("ROUND(");
		nameInFunction(sb);
		sb.append(", ").append(decimals).append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

}