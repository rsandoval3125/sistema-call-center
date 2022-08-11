package ec.gob.inec.reportes.jsf.sql.sentencias;

public class Asc extends Sort {
	public Asc(Column column) {
		super(column, ASC);
	}

	public Asc(String column) {
		super(column, ASC);
	}
}