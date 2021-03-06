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
package com.mysema.query.jpa.domain.sql;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import java.util.Date;

import com.mysema.query.sql.ForeignKey;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BeanPath;
import com.mysema.query.types.path.DateTimePath;
import com.mysema.query.types.path.NumberPath;


/**
 * SStatuschange is a Querydsl query type for SStatuschange
 */
public class SStatuschange extends RelationalPathBase<SStatuschange> implements RelationalPath<SStatuschange> {

    private static final long serialVersionUID = 1953690091;

    public static final SStatuschange statuschange = new SStatuschange("STATUSCHANGE");

    public final NumberPath<Long> id = createNumber("ID", Long.class);

    public final DateTimePath<Date> timestamp = createDateTime("TIMESTAMP", Date.class);

    public final PrimaryKey<SStatuschange> sql100819184439140 = createPrimaryKey(id);

    public final ForeignKey<SItemStatuschange> _fkc2c9ebee2f721e35 = new ForeignKey<SItemStatuschange>(this, id, "STATUSCHANGES_ID");

    public SStatuschange(String variable) {
        super(SStatuschange.class, forVariable(variable), null, "STATUSCHANGE");
    }

    public SStatuschange(BeanPath<? extends SStatuschange> entity) {
        super(entity.getType(), entity.getMetadata(), null, "STATUSCHANGE");
    }

    public SStatuschange(PathMetadata<?> metadata) {
        super(SStatuschange.class, metadata, null, "STATUSCHANGE");
    }
    
}

