package ec.gob.inec.reportes.jsf.sql.sentencias;

public class MultiClause extends Clause {
	private Clause clause;

	public MultiClause(Clause clause) {
		this.clause = clause;
	}

	@Override
	public MultiClause and(Clause clause) {
		super.and(clause);
		return this;
	}

	@Override
	public MultiClause or(Clause clause) {
		super.or(clause);
		return this;
	}

	@Override
	public void subSQL(StringBuilder sql) {
		if (clause.isMulti()) {
			sql.append('(');
			clause.subSQL(sql);
			sql.append(')');
		} else {
			clause.subSQL(sql);
		}
	}

}