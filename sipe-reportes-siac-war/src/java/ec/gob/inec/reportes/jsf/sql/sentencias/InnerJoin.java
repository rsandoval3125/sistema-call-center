package ec.gob.inec.reportes.jsf.sql.sentencias;

import java.util.Collection;
import java.util.List;

public class InnerJoin extends Join {

	public InnerJoin(Column column, Column pattern) {
		super(" INNER JOIN ", column, pattern);
	}
        
	/*public InnerJoin(List<InnerJoin> columns) {
		super(" INNER JOIN ", columns);
	}*/

	public InnerJoin and(Clause clause) {
		return (InnerJoin) super.and(clause);
	}

}