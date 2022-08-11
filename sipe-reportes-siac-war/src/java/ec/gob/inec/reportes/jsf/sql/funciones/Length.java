package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Length extends AggregateColumn {

	public Length(String name) {
		super("LENGTH", name);
	}

	public Length(String table, String name) {
		super("LENGTH", table, name);
	}

	public Length(Select select, String tableAlias, String name) {
		super("LENGTH", select, tableAlias, name);
	}

}