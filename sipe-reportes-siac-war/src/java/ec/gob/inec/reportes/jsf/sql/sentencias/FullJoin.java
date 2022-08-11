package ec.gob.inec.reportes.jsf.sql.sentencias;

public class FullJoin extends Join {

	public FullJoin(Column column, Column pattern) {
		super(" FULL JOIN ", column, pattern);
	}

	public FullJoin and(Clause clause) {
		return (FullJoin) super.and(clause);
	}

}