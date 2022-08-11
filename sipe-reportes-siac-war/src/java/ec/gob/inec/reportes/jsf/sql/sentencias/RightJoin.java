package ec.gob.inec.reportes.jsf.sql.sentencias;

public class RightJoin extends Join {

	public RightJoin(Column column, Column pattern) {
		super(" RIGHT JOIN ", column, pattern);
	}

	public RightJoin and(Clause clause) {
		return (RightJoin) super.and(clause);
	}

}
