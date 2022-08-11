package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Min extends AggregateColumn {

	public Min(String name) {
		super("MIN", name);
	}

	public Min(String table, String name) {
		super("MIN", table, name);
	}

	public Min(Select select, String tableAlias, String name) {
		super("MIN", select, tableAlias, name);
	}

}