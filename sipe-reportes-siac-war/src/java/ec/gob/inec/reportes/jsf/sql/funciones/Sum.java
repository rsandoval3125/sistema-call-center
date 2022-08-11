package ec.gob.inec.reportes.jsf.sql.funciones;

import ec.gob.inec.reportes.jsf.sql.sentencias.Select;

public class Sum extends AggregateColumn {

	public Sum(String name) {
		super("SUM", name);
	}

	public Sum(String table, String name) {
		super("SUM", table, name);
	}

	public Sum(Select select, String tableAlias, String name) {
		super("SUM", select, tableAlias, name);
	}

}