package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Max extends AggregateColumn {

	public Max(String name) {
		super("MAX", name);
	}

	public Max(String table, String name) {
		super("MAX", table, name);
	}

	public Max(Select select, String tableAlias, String name) {
		super("MAX", select, tableAlias, name);
	}

}