package ec.gob.inec.reportes.jsf.sql.sentencias;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class Join implements SQLString {

    private Clause clause;

    String type;
    Column column;
    Column pattern;
    private List<InnerJoin> columns;

    Join(String type, Column column, Column pattern) {
        this.type = type;
        this.column = column;
        this.pattern = pattern;
        clause = Clause.equal(column, pattern);
    }

    /*Join(String type, List<InnerJoin> joins) {
        this.type = type;
        this.columns = joins;
        for (int i = 0; i < columns.size(); i++) {
            clause = Clause.equal(columns.get(i).column, columns.get(i).pattern);
        }
    }*/

    @Override
    public void toSQL(StringBuilder sb) {
        sb.append(type);
        pattern.tableInFrom(sb);
        sb.append(" ON ");
        clause.toSQL(sb);
    }

    public void toSQLJoinIgual(StringBuilder sb, int condicion) {
        if (condicion == 0) {
            sb.append(type);
            pattern.tableInFrom(sb);
            sb.append(" ON ");
            sb.append("(");
            clause.toSQL(sb);
            //sb.append(" AND ");
        } else {
            sb.append(" AND ");
            clause.toSQL(sb);
        }        
    }
    
    /*public void cerrarParentesis(StringBuilder sb, int condicion1, int condicion2){
        if(condicion1 != condicion2){
            sb.append(")");
        }
    }*/
    
    public Join and(Clause clause) {
        this.clause.and(clause);
        return this;
    }
}
