/*
 * The MIT License
 *
 * Copyright 2016 Azzuwan Aziz <azzuwan@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.blazzify.gen.writer;

import com.blazzify.gen.model.ExpressProject;
import com.blazzify.gen.model.GoProject;
import com.blazzify.gen.model.Project;
import com.blazzify.gen.model.SparkProject;
import java.util.List;
import org.apache.metamodel.schema.Table;

/**
 *
 * @author Azzuwan Aziz <azzuwan@gmail.com>
 */
public class WriterFactory {
    public static Writer createWriter(Project project, List<Table> tables){
        Writer writer = null;
        String framework = project.getFramework();
        switch(framework){
            
            case "spark":
                writer = new DefaultWriter<SparkProject>((SparkProject) project, tables);
                break;
                
            case "go":
                writer = new DefaultWriter<GoProject>((GoProject) project, tables);
                break;
                
            case "express":
                writer = new DefaultWriter<ExpressProject>((ExpressProject) project, tables);
                
            default:
                throw new UnsupportedOperationException("Code writer for "+ framework+" is not supported yet.");                
                
        }
        
        return writer;
    }
}
