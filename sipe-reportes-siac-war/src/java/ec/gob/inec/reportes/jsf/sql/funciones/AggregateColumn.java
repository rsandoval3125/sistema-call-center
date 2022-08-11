package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Column;
import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class AggregateColumn extends Column {
	private final String fun;

	public AggregateColumn(String fun, String name) {
		super(name);
		this.fun = fun;
	}

	public AggregateColumn(String fun, String table, String name) {
		super(table, name);
		this.fun = fun;
	}

	public AggregateColumn(String fun, Select select, String tableAlias, String name) {
		super(select, tableAlias, name);
		this.fun = fun;
	}

	@Override
	public void nameInColumn(StringBuilder sb) {
		sb.append(fun).append('(');
		nameInFunction(sb);
		sb.append(')');
		if (nameAlias != null) {
			sb.append(" AS `").append(nameAlias).append('`');
		}
	}

	@Override
	public void nameInWhere(StringBuilder sb) {
		sb.append('`').append(nameAlias).append('`');
	}

}
