package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;
import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Mid extends Column {
	private int start, length;

	public Mid(String name, int start, int length) {
		super(name);
		this.start = start;
		this.length = length;
	}

	public Mid(String table, String name, int start, int length) {
		super(table, name);
		this.start = start;
		this.length = length;
	}

	public Mid(Select select, String tableAlias, String name, int start, int length) {
		super(select, tableAlias, name);
		this.start = start;
		this.length = length;
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("MID(");
		nameInFunction(sb);
		sb.append(',').append(start).append(',').append(length).append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

	@Override
	public void nameInWhere(StringBuilder sb) {
		sb.append('`').append(nameAlias).append('`');
	}

}