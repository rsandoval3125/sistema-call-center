package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;
import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Format extends Column {
	public String format;

	public Format(String name, String format) {
		super(name);
		this.format = format;
	}

	public Format(String table, String name, String format) {
		super(table, name);
		this.format = format;
	}

	public Format(Select select, String tableAlias, String name, String format) {
		super(select, tableAlias, name);
		this.format = format;
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append("FORMAT(");
		nameInFunction(sb);
		sb.append(", ").append(format).append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

}