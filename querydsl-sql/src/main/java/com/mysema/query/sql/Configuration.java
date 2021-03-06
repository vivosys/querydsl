/*
 * Copyright 2011, Mysema Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mysema.query.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;

import com.mysema.query.sql.types.Type;
import com.mysema.query.types.Path;

/**
 * Configuration for SQLQuery instances
 * 
 * @author tiwe
 *
 */
public class Configuration {
    
    public static final Configuration DEFAULT = new Configuration(SQLTemplates.DEFAULT);
    
    private final JDBCTypeMapping jdbcTypeMapping = new JDBCTypeMapping();
    
    private final JavaTypeMapping javaTypeMapping = new JavaTypeMapping();
    
    private final SQLTemplates templates;

    public Configuration(SQLTemplates templates) {       
        this.templates = templates;
    }

    public SQLTemplates getTemplates() {
        return templates;
    }
    
    /**
     * Get the java type for the given jdbc type, table name and column name
     * 
     * @param sqlType
     * @param tableName
     * @param columnName
     * @return
     */
    public Class<?> getJavaType(int sqlType, String tableName, String columnName) {
        Type<?> type = javaTypeMapping.getType(tableName, columnName);
        if (type != null) {
            return type.getReturnedClass();
        } else {
            return jdbcTypeMapping.get(sqlType);
        }
    }
    
    /**
     * @param <T>
     * @param rs
     * @param path
     * @param i
     * @param clazz
     * @return
     * @throws SQLException
     */
    @Nullable    
    public <T> T get(ResultSet rs, @Nullable Path<?> path, int i, Class<T> clazz) throws SQLException {        
        Type<T> type = getType(path, clazz);
        return type.getValue(rs, i);
    }
    
    /**
     * @param <T>
     * @param stmt
     * @param path
     * @param i
     * @param value
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public <T> int set(PreparedStatement stmt, Path<?> path, int i, T value) throws SQLException{
        Type<T> type = getType(path, (Class)value.getClass());
        type.setValue(stmt, i, value);
        return type.getSQLTypes().length;        
    }

    @SuppressWarnings("unchecked")
    private <T> Type<T> getType(@Nullable Path<?> path, Class<T> clazz) {
        if (path != null && path.getMetadata().getParent() instanceof RelationalPath) {
            String table = ((RelationalPath)path.getMetadata().getParent()).getTableName();
            String column = path.getMetadata().getExpression().toString();
            Type<T> type = (Type)javaTypeMapping.getType(table, column);
            if (type != null) {
                return type;
            }                        
        }
        return javaTypeMapping.getType(clazz);
    }
    
    /**
     * Register the given Type to be used
     * 
     * @param type
     */
    public void register(Type<?> type) {
        jdbcTypeMapping.register(type.getSQLTypes()[0], type.getReturnedClass());
        javaTypeMapping.register(type);
    }

    /**
     * Register the given Type for the given table and column
     * 
     * @param table
     * @param column
     * @param type
     */
    public void register(String table, String column, Type<?> type) {
        javaTypeMapping.setType(table, column, type);
    }

}
