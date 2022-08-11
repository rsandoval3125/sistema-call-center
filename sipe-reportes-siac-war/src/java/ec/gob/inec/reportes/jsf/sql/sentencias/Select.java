package ec.gob.inec.reportes.jsf.sql.sentencias;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import ec.gob.inec.reportes.jsf.sql.funciones.Having;
import java.util.List;

/**
 * 
 */
public class Select implements SQLString {

    private LinkedHashSet<Column> columns = new LinkedHashSet<>();
    private List<InnerJoin> joinsColumnas = new ArrayList<>();
    private LinkedList<Join> joins;
    private ArrayList<Column> groupBy;
    private Clause where;
    private Clause and;
    private Clause or;
    private Having having;
    private Sort orderBy;
    private int limitOffset = 0, limit = 0;
    private List<Clause> lstAnd = new ArrayList<>();
    private List<Clause> lstOr = new ArrayList<>();

    public Column[] getColumns() {
        Column[] out = new Column[columns.size()];
        return columns.toArray(out);
    }

    public Select() {
    }

    public Select addColumn(String column) {
        columns.add(new Column(column));
        return this;
    }

    public Select addColumn(Column column) {
        columns.add(column);
        return this;
    }

    public Select addColumn(Collection<Column> columns) {
        this.columns.addAll(columns);
        return this;
    }

    public Select addColumn(Column... column) {
        Collections.addAll(columns, column);
        return this;
    }

    private void initJoins() {
        if (joins == null) {
            joins = new LinkedList<>();
        }
    }

    public Select join(Join join) {
        initJoins();
        joins.add(join);
        return this;
    }

    public Select innerJoin(Column src, Column pattern) {
        initJoins();
        joins.add(new InnerJoin(src, pattern));
        return this;
    }

    public Select innerJoin(List<InnerJoin> columns) {
        initJoins();
        this.joins.addAll(columns);
        return this;
    }

    public Select leftJoin(Column src, Column pattern) {
        initJoins();
        joins.add(new LeftJoin(src, pattern));
        return this;
    }

    public Select rightJoin(Column src, Column pattern) {
        initJoins();
        joins.add(new RightJoin(src, pattern));
        return this;
    }

    public Select fullJoin(Column src, Column pattern) {
        initJoins();
        joins.add(new FullJoin(src, pattern));
        return this;
    }

    /**
     * @see {@link #Clause}
     * @see {@link #MultiClause}
     */
    public Select where(Clause clause) {
        this.where = clause;
        return this;
    }

    public Select groupBy(String column) {
        if (groupBy == null) {
            groupBy = new ArrayList<>();
        }
        groupBy.add(new Column(column));
        return this;
    }

    public Select groupBy(String... columns) {
        if (groupBy == null) {
            groupBy = new ArrayList<>();
        }
        for (String column : columns) {
            groupBy.add(new Column(column));
        }
        return this;
    }

    public Select groupBy(Column column) {
        if (groupBy == null) {
            groupBy = new ArrayList<>();
        }
        groupBy.add(column);
        return this;
    }

    public Select groupBy(Column... columns) {
        if (groupBy == null) {
            groupBy = new ArrayList<>();
        }
        Collections.addAll(groupBy, columns);
        return this;
    }

    public Select groupBy(Collection<Column> columns) {
        if (groupBy == null) {
            groupBy = new ArrayList<>();
        }
        groupBy.addAll(columns);
        return this;
    }

    public boolean removeGroupBy(Column column) {
        return groupBy != null && groupBy.remove(column);
    }

    public void having(Having having) {
        this.having = having;
        addColumn(having.getColumn());
    }

    /**
     * @see {@link #Sort}
     * @see {@link #Asc}
     * @see {@link #Desc}
     */
    public Select orderBy(Sort orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Select limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Select limit(int offset, int limit) {
        this.limitOffset = offset;
        this.limit = limit;
        return this;
    }

    public Select and(Clause clause) {
        this.and = clause;
        lstAnd.add(and);
        return this;
    }

    public Select or(Clause clause) {
        this.or = clause;
        lstOr.add(or);
        return this;
    }

    @Override
    public void toSQL(StringBuilder sql) {
        sql.append("SELECT ");
        // columns
        columns(sql);
        from(sql);
        // join on
        if (joins != null) {
            /*for (Join join : joins) {
				join.toSQL(sql);
			}*/
            //int k=0;
            boolean condicion = false;
            for (int i = 0, j = 0; i < joins.size(); i++) {
                if ((joins.size() - 1) != i) {
                    if (joins.get(i).column.table.equals(joins.get(i + 1).column.table)) {
                        joins.get(i).toSQLJoinIgual(sql, j);
                        condicion = true;
                        j++;
                    } else {
                        if (condicion) {
                            joins.get(i).toSQLJoinIgual(sql, j);
                            sql.append(")");
                            j = 0;
                        } else {
                            joins.get(i).toSQL(sql);
                        }
                        condicion = false;
                    }
                } else {
                    if (condicion) {
                        joins.get(i).toSQLJoinIgual(sql, j);
                        sql.append(")");
                        j = 0;
                    } else {
                        joins.get(i).toSQL(sql);
                    }
                    condicion = false;
                }
            }
            if (condicion) {
                sql.append(")");
            }
        }
        // where
        if (where != null) {
            sql.append(" WHERE ");
            where.toSQL(sql);
        }
        // and
        if (!lstAnd.isEmpty()) {
            for (Clause clAnd : lstAnd) {
                sql.append(" AND ");
                clAnd.toSQL(sql);
            }
        }
        // or
        if (!lstOr.isEmpty()) {
            for (Clause clOr : lstOr) {
                sql.append(" OR ");
                clOr.toSQL(sql);
            }
        }
        // group by
        groupBy(sql);
        // having
        if (having != null) {
            having.toSQL(sql);
        }
        // order by
        if (orderBy != null) {
            orderBy.toSQL(sql);
        }
        // limit
        if (limit > 0) {
            sql.append(" LIMIT ");
            if (limitOffset > 0) {
                sql.append(limitOffset).append(',');
            }
            sql.append(limit);
        }
    }

    private void columns(StringBuilder sql) {
        boolean f = true;
        for (Column column : columns) {
            if (column.name == null) {
                continue;
            }
            if (f) {
                f = false;
            } else {
                sql.append(", ");
            }
            column.nameInColumn(sql);
        }
    }

    private void from(StringBuilder sql) {
        // table
        LinkedHashMap<String, Column> tables = new LinkedHashMap<>();
        if (joins != null && joins.size() > 0) {
            Column src = joins.get(0).column;
            tables.put(src.tableName(), src);
        }
        for (Column column : columns) {// add all-table
            String table = column.tableName();
            if (table != null) {
                tables.put(column.tableName(), column);
            }
        }
        if (joins != null) {
            for (Join join : joins) {// remove join-table
                tables.remove(join.pattern.tableName());
            }
        }
        // from
        sql.append(" FROM ");
        boolean f = true;
        for (Column table : tables.values()) {
            if (f) {
                f = false;
            } else {
                sql.append(", ");
            }
            table.tableInFrom(sql);
        }
    }

    private void groupBy(StringBuilder sql) {
        if (groupBy == null) {
            return;
        }
        sql.append(" GROUP BY ");
        boolean f = true;
        for (Column li : groupBy) {
            if (f) {
                f = false;
            } else {
                sql.append(", ");
            }
            li.nameInWhere(sql);
        }
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        toSQL(sql);
        sql.append(';');
        return sql.toString();
    }

}
