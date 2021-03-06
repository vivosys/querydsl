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

import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.SimplePath;
import com.mysema.query.types.path.StringPath;


/**
 * SSysdepends is a Querydsl query type for SSysdepends
 */
public class SSysdepends extends com.mysema.query.sql.RelationalPathBase<SSysdepends> {

    private static final long serialVersionUID = 584370123;

    public static final SSysdepends sysdepends = new SSysdepends("SYSDEPENDS");

    public final SimplePath<Object> dependentfinder = createSimple("DEPENDENTFINDER", Object.class);

    public final StringPath dependentid = createString("DEPENDENTID");

    public final SimplePath<Object> providerfinder = createSimple("PROVIDERFINDER", Object.class);

    public final StringPath providerid = createString("PROVIDERID");

    public SSysdepends(String variable) {
        super(SSysdepends.class, forVariable(variable), "SYS", "SYSDEPENDS");
    }

    public SSysdepends(Path<? extends SSysdepends> entity) {
        super(entity.getType(), entity.getMetadata(), "SYS", "SYSDEPENDS");
    }

    public SSysdepends(PathMetadata<?> metadata) {
        super(SSysdepends.class, metadata, "SYS", "SYSDEPENDS");
    }

}

