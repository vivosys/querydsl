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

import com.mysema.query.sql.ForeignKey;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BeanPath;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;


/**
 * SNamelistNames is a Querydsl query type for SNamelistNames
 */
public class SNamelistNames extends RelationalPathBase<SNamelistNames> implements RelationalPath<SNamelistNames> {

    private static final long serialVersionUID = -1506785162;

    public static final SNamelistNames namelistNames = new SNamelistNames("NAMELIST_NAMES");

    public final StringPath element = createString("ELEMENT");

    public final NumberPath<Long> namelistId = createNumber("NAMELIST_ID", Long.class);

    public final ForeignKey<SNamelist> fkd6c82d72b8406ca4 = new ForeignKey<SNamelist>(this, namelistId, "ID");

    public SNamelistNames(String variable) {
        super(SNamelistNames.class, forVariable(variable), null, "NAMELIST_NAMES");
    }

    public SNamelistNames(BeanPath<? extends SNamelistNames> entity) {
        super(entity.getType(), entity.getMetadata(), null, "NAMELIST_NAMES");
    }

    public SNamelistNames(PathMetadata<?> metadata) {
        super(SNamelistNames.class, metadata, null, "NAMELIST_NAMES");
    }

}

