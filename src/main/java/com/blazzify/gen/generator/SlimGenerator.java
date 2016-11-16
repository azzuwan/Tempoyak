/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazzify.gen.generator;

import com.blazzify.gen.connection.ConnectionFactory;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.jdbc.JdbcDataContext;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import com.blazzify.gen.model.SparkProject;
import com.blazzify.gen.model.Database;
import com.blazzify.gen.writer.SparkWriter;

/**
 *
 * @author Azzuwan Aziz <azzuwan@gmail.com>
 */
public class SlimGenerator implements Generator {
    private SparkProject project;

    public SlimGenerator(SparkProject project) {
        this.project = project;
    }

    @Override
    public void generate() {
        try {
            //Get current execution path
            final String dir = System.getProperty("user.dir");
            System.out.println("Excuting in directory: " + dir);
            
            Database database = this.project.getDatabase();           
            String db = database.getSchema();           
            
            Connection conn = ConnectionFactory.create(database);
            DataContext dataContext = new JdbcDataContext(conn);
            
            List<Schema> schemaList = new ArrayList<>();
            List<Table> tableList = new ArrayList<>();
            List<Column> columnList = new ArrayList<>();
            
            //Traverse database
            Schema[] schemas = dataContext.getSchemas();
            schemaList = Arrays.asList(schemas);
            
            for (Schema schema : schemas) {
                System.out.println("Schema: " + schema.getName());
                
                if (schema.getName().equals(db)) {
                    
                    Table[] tables = schema.getTables();
                    tableList = Arrays.asList(tables);
                    
                    for (Table table : tables) {
                        System.out.println("    Table: " + table.getName());
                        
                        Column[] columns = table.getColumns();
                        columnList = Arrays.asList(columns);
                        for (Column column : columns) {
                            System.out.println("        Column: " + column.getName());
                        }
                    }
                }                
            }

            System.out.println("table list: " + tableList.size());
            
            SparkWriter cw = new SparkWriter(this.project, tableList);
            cw.write();
        } catch (IOException ex) {
            Logger.getLogger(SlimGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the project
     */
    public SparkProject getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(SparkProject project) {
        this.project = project;
    }
}
