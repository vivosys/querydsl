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
package com.mysema.query.jpa;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mysema.query.DefaultQueryMetadata;
import com.mysema.query.JoinType;
import com.mysema.query.QueryMetadata;
import com.mysema.query.jpa.domain.Location;
import com.mysema.query.jpa.domain.QEmployee;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;

public class JPQLSerializerTest {
        
    @Test
    public void FromWithCustomEntityName() {
        JPQLSerializer serializer = new JPQLSerializer(HQLTemplates.DEFAULT);
        EntityPath<Location> entityPath = new EntityPathBase<Location>(Location.class, "entity");
        QueryMetadata md = new DefaultQueryMetadata();
        md.addJoin(JoinType.DEFAULT, entityPath);
        serializer.serialize(md, false, null);
        assertEquals("from Location2 entity", serializer.toString());
    }

    @Test
    public void NormalizeNumericArgs() {
        JPQLSerializer serializer = new JPQLSerializer(HQLTemplates.DEFAULT);
        NumberPath<Double> doublePath = new NumberPath<Double>(Double.class, "doublePath");
        serializer.handle(doublePath.add(1));
        serializer.handle(doublePath.between((float)1.0, 1l));
        serializer.handle(doublePath.lt((byte)1));
        for (Object constant : serializer.getConstantToLabel().keySet()){
            assertEquals(Double.class, constant.getClass());
        }
    }

    @Test
    public void Delete_Clause_Uses_DELETE_FROM() {
        JPQLSerializer serializer = new JPQLSerializer(HQLTemplates.DEFAULT);
        QueryMetadata md = new DefaultQueryMetadata();
        md.addJoin(JoinType.DEFAULT, QEmployee.employee);
        md.addWhere(QEmployee.employee.lastName.isNull());
        serializer.serializeForDelete(md);
        assertEquals("delete from Employee employee\nwhere employee.lastName is null", serializer.toString());
    }
}
 