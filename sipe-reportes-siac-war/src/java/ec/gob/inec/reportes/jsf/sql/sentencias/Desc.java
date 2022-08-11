package ec.gob.inec.reportes.jsf.sql.sentencias;

public class Desc extends Sort {
	public Desc(Column column) {
		super(column, DESC);
	}

	public Desc(String column) {
		super(column, DESC);
	}
}