package com.mnt.time.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
//import com.mnt.core.report.RQBUtils;

@Controller
public class Reporting {
	
	
	private Connection getConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/timemgmt1?"+
                "user=root&password=root");
        return conn;
	}
	
	private Connection getConnection(String table) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/" + table + "?"+
                "user=root&password=root");
        return conn;
	}
	
	public static String sqlize(String value) {
		return "'" + value + "'";
	}
	
	@ResponseBody
	@RequestMapping(value="/report/constructRPT",method=RequestMethod.GET)
	public void constructRPT() throws Exception {
		Connection connTime = getConnection("timemgmt1");
		Connection connRpt = getConnection("time_report");

        DatabaseMetaData dmd = connTime.getMetaData();
        ResultSet rs = dmd.getTables(null, null, null,
                new String[] {"TABLE" });
        
        while (rs.next()) {
        	String tableName = rs.getString("TABLE_NAME");
        	connRpt.createStatement().executeUpdate("INSERT INTO RPT_TABLE (name) values(" + sqlize(tableName) + ")");
        	
        	
        	ResultSet rsCols = dmd.getColumns(null, null, tableName, null);
            while (rsCols.next()) {
            	String columnName = rsCols.getString("COLUMN_NAME");
            	String type = rsCols.getString("TYPE_NAME");
            	connRpt.createStatement().executeUpdate("INSERT INTO RPT_FIELDS (name,label,type,tableid) values(" +
            			sqlize(columnName) + "," +
            			sqlize(columnName) + "," +
            		    sqlize(type) + "," +
            		    sqlize(tableName)  +
            			")");
            	
            }
            rsCols.close();
            
            ResultSet rsKeys = dmd.getExportedKeys(null, null, tableName);
            while (rsKeys.next()) {
                int keySeq = rsKeys.getInt("KEY_SEQ");
                String name = null;
                String fkTableName = null ;
                
                if (keySeq == 1) {
                    name = rsKeys.getString("FK_NAME");
                    fkTableName = rsKeys.getString("FKTABLE_NAME");
                }

                String fkColumnNames = rsKeys.getString("FKCOLUMN_NAME");
                String PKCOLUMN_NAME = rsKeys.getString("PKCOLUMN_NAME");
                
                connRpt.createStatement().executeUpdate("INSERT INTO RPT_FKS "
                		+ "(name, label, referencedTableName, tableid, referencedKeyNames, foreignKeyNames , reverseLabel) values(" +
                		sqlize(name) + "," + // name
                		sqlize(name) + "," + // label
                		sqlize(fkTableName) + "," + // refTable
                		sqlize(tableName) + "," + //tableid
                		sqlize(fkColumnNames) + ","  + //referencedKeyNames
            			sqlize(PKCOLUMN_NAME) + "," +//fkeyname
            			sqlize(tableName) +
            			")");
                
            }
            rsKeys.close();
            
            
        }
        rs.close();
        connRpt.close();
        connTime.close();

	}
	
	public class RBQColumns {
		public String name;
		public String label;
		public String type;
		//public String editor;
		
		public RBQColumns(String name, String label, String type, String editor) {
			super();
			this.name = name;
			this.label = label;
			this.type = type;
			//this.editor = editor;
		}
		
		public RBQColumns(){}
		
	}
	
	public class  RBQTable {
		public String name;

		public String label;

		public RBQTable(String name, String label) {
			super();
			this.name = name;
			this.label = label;
		}
		
		public RBQTable(){}
		
		public List<RBQColumns> columns = new ArrayList<>();
		public List<RQBFKS> fks = new ArrayList<>();
	    
	}
	
	public class RQBFKS {
			public String name;
			public List<String> foreignKeyNames;
			public String referencedTableName;
			public List<String>  referencedKeyNames;
			public String label;
			public String reverseLabel;
		
	}
	
	public class RQBMeta {
		public List<RBQTable> tables = new ArrayList<>();
		public List types = new ArrayList<>();
	}
	
	@ResponseBody
	@RequestMapping(value="/report/select",method=RequestMethod.GET)
	public List<Map<String,String>> generateSelectFullOfData(@RequestParam String tableName,
			@RequestParam String columnName) throws Exception {
		Connection connTime = getConnection("timemgmt1");
		List<Map<String,String>> list = new ArrayList<>();
		
		ResultSet rs = connTime.createStatement().executeQuery("SELECT DISTINCT(" + columnName + ") FROM " + tableName);
		
		while(rs.next()){
			String value = rs.getString(1);
			
			if(value != null) {
				Map<String,String> map = new HashMap<>();
				map.put("value", value);
				map.put("label", value);
				list.add(map);
			}
		}
		rs.close();
		connTime.close();
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/report/suggest",method=RequestMethod.GET)
	public List<String> generateSuggestFullOfData(@RequestParam String tableName,
			@RequestParam String columnName,@RequestParam String q) throws Exception {
		Connection connTime = getConnection("timemgmt1");
		List<String> list = new ArrayList<>();
		
		PreparedStatement ps = connTime.
				prepareStatement("SELECT DISTINCT(" + columnName + ") FROM " + tableName + 
				" WHERE UPPER('" + columnName + "') LIKE ?");
		ps.setString(1, q);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()){
			String value = rs.getString(1);
			if(value != null) {
				list.add(value);
			}
		}
		rs.close();
		connTime.close();
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/report/MetaDataForRQB",method=RequestMethod.POST)
	public RQBMeta generateMDJSONForRQB() throws Exception {
		Connection connRpt = getConnection("time_report");
		ResultSet resultSetTable = connRpt.createStatement().executeQuery("SELECT name, label FROM RPT_TABLE WHERE isvisible = 1 ");
		RQBMeta meta = new RQBMeta();
        
		while(resultSetTable.next()) {
			String tableName = resultSetTable.getString("name");
			String tableLabel = resultSetTable.getString("label");
			RBQTable table = new RBQTable(tableName, tableLabel);
			
			ResultSet resultSetCol = connRpt.createStatement().executeQuery("SELECT name, label, type, editor FROM RPT_FIELDS WHERE isvisible = 1 AND tableid = " + sqlize(tableName));
			while(resultSetCol.next()) {
				String name = resultSetCol.getString("name");
				String label = resultSetCol.getString("label");
				String type = resultSetCol.getString("type");
				String editor = resultSetCol.getString("editor");
				table.columns.add(new RBQColumns(name,label,type,editor));
			}
			resultSetCol.close();
			
			ResultSet resultSetFKs = connRpt.createStatement().executeQuery("SELECT * FROM RPT_FKS WHERE isvisible = 1 AND tableid = " + sqlize(tableName));
			while(resultSetFKs.next()) {
				RQBFKS rbqfks = new RQBFKS();
				
				rbqfks.referencedTableName = resultSetFKs.getString("referencedTableName");
				ResultSet resultSetEligibleTable = connRpt.createStatement().
				executeQuery("Select name from RPT_TABLE where isvisible = 1 and name =" + 
				sqlize(rbqfks.referencedTableName));
				if(resultSetEligibleTable.next() && !rbqfks.referencedTableName.equalsIgnoreCase(tableName)) {
					rbqfks.name = resultSetFKs.getString("name");
					rbqfks.label = resultSetFKs.getString("label");
					rbqfks.foreignKeyNames = Lists.newArrayList(resultSetFKs.getString("foreignKeyNames"));
					rbqfks.referencedKeyNames = Lists.newArrayList(resultSetFKs.getString("referencedKeyNames"));
					rbqfks.reverseLabel = resultSetFKs.getString("name").concat(".reverse");
					table.fks.add(rbqfks);
				}
				resultSetEligibleTable.close();
			}
			resultSetFKs.close();
			
			meta.tables.add(table);
		}
		
		resultSetTable.close();
		
       
        meta.types.add(type("CHAR", "TEXT", stringOps()));
        meta.types.add(type("VARCHAR", "TEXT", stringOps()));
        meta.types.add(type("LONGTEXT", "TEXT", stringOps()));
        meta.types.add(type("TEXT", "TEXT", stringOps()));
        
        meta.types.add(type("DATETIME", "DATE", dateOps()));
        meta.types.add(type("DATE", "DATE", dateOps()));
        meta.types.add(type("TIMESTAMP", "DATE", dateOps()));
        
        meta.types.add(type("BIGINT", "NUMBER", numberOps()));
        meta.types.add(type("INT", "NUMBER", numberOps()));
        meta.types.add(type("BIT", "NUMBER", numberOps()));
        
        meta.types.add(type("FLOAT", "NUMBER", numberOps()));
        meta.types.add(type("DOUBLE", "NUMBER", numberOps()));
        meta.types.add(type("DECIMAL", "NUMBER", numberOps()));
        
        meta.types.add(type("SELECT", "SELECT", selectOps()));
        meta.types.add(type("SUGGEST", "SUGGEST", suggestOps()));
        
        
        return meta; 
	}

	/*@ResponseBody
	@RequestMapping(value="/report/search",method=RequestMethod.GET)
	public Map<String,Object> seachReport(HttpServletRequest req) throws Exception {
		Connection conn = getConnection();
		
		String sql = req.getParameter("sql");
        
        sql = RQBUtils.cleanUpSelectPhase(sql);
        
        Map<String,String> tableNames = new RQBUtils(sql).getTableName();
        
        
        
        String[] args = req.getParameterValues("arg");

        StringBuilder selectPhase = new StringBuilder();
        List<SlickGridCol> columns = new ArrayList<>();
        
        int count = 0;
         for(String key : tableNames.keySet()) {
            ResultSet rsMdRepTab = conn.createStatement().executeQuery("SELECT field, label from MD_REPORT_COL x WHERE "
            		+ "x.table = '" + key + "'");	
            while (rsMdRepTab.next()) {
         	   String field = rsMdRepTab.getString("field");
         	   String label = rsMdRepTab.getString("label");
         	   selectPhase.append(tableNames.get(key) + "." + field + " AS " + label);
         	   selectPhase.append(" ,");
         	   columns.add(new SlickGridCol(field, label, field));
            }
            if(count == 0) selectPhase.append(tableNames.get(key) + ".id"  + " AS id ," );
            else selectPhase.append(tableNames.get(key) + ".id"  + " AS  " + key + "id ," );
            count ++;
            rsMdRepTab.close();
         }
         
         
         String newSql = selectPhase.append("id ").toString();
         if(newSql.length() != 0 ) {
           newSql = newSql.substring(0, newSql.length() - 2);
         
           sql = sql.replaceFirst("[*]", newSql);
         }
         
         System.out.println("===============" + sql); 
         PreparedStatement st = conn.prepareStatement(sql);
         if (args != null) {
             for (int i = 0; i < args.length; i++) {
            	 
            	 // This is raw method of checking if input is date or not.
            	 try {
            	  SimpleDateFormat parserSDF = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss Z ", Locale.ENGLISH);
            	  Date date = parserSDF.parse(args[i]);
            	  java.sql.Date date2 = new java.sql.Date(date.getTime());
            	  st.setDate(i + 1, date2);
            	 } catch(Exception e) {
            		 st.setString(i + 1, args[i]);
            	 }
            	 
                 
             }
         }

         ResultSet rs = st.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         List<Map<String,Object>> data = new ArrayList<>();
         int numColumns = rsmd.getColumnCount();
         
         while (rs.next()) {
         	Map<String,Object> cell = new HashMap<String, Object>();
         	for (int i = 1; i < numColumns + 1; i++) {
         		String key = rsmd.getColumnName(i);
         		Object value = rs.getObject(i);
         		cell.put(key, value);
         	}
         	data.add(cell);
         }
         
         Map<String,Object> map = new HashMap<>();
         map.put("data", data);
         map.put("columns", columns);
         return map;

	}*/
	
	public static class SlickGridCol {
    	public String id;
    	public String name;
    	public String field;
		public SlickGridCol(String id, String name, String field) {
			super();
			this.id = id;
			this.name = name;
			this.field = field;
		}
		public SlickGridCol(){}
    	
    }
	
    private Map op(String name, String label) {
        return op(name, label, "ONE");
    }

    private Map op(String name, String label, String card)
             {
    	Map op = new HashMap();
        op.put("name", name);
        op.put("label", label);
        op.put("cardinality", card);
        return op;
    }

    private List stringOps()  {
        List ops = new ArrayList();
        ops.add(op("=", "is"));
        ops.add(op("LIKE", "like"));
        return ops;
    }
    
    private List selectOps()  {
    	List ops = new ArrayList();
        ops.add(op("IN", "IN","MULTI"));
        return ops;
    }
    
    private List suggestOps()  {
    	List ops = new ArrayList();
        ops.add(op("=", "is"));
        ops.add(op("IN", "IN","MULTI"));
        ops.add(op("LIKE", "like"));
        return ops;
    }

    private List numberOps()  {
    	List ops = new ArrayList();
        ops.add(op("=", "is"));
        ops.add(op("<>", "is not"));
        ops.add(op("<", "less than"));
        ops.add(op(">", "greater than"));
        return ops;
    }
    
    private List dateOps()  {
    	List ops = new ArrayList();
        ops.add(op("<", "less than"));
        ops.add(op(">", "greater than"));
        return ops;
    }
    
    private Map type(String name, String editor, List ops)
             {
    	
        Map obj = new HashMap();
        obj.put("name", name);
        obj.put("editor", editor);
        obj.put("operators", ops);
        return obj;
    }

}
