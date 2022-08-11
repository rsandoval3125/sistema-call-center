package ec.gob.inec.reportes.jsf.sql.sentencias;

public class LeftJoin extends Join {

	public LeftJoin(Column column, Column pattern) {
		super(" LEFT JOIN ", column, pattern);
	}

	public LeftJoin and(Clause clause) {
		return (LeftJoin) super.and(clause);
	}

}