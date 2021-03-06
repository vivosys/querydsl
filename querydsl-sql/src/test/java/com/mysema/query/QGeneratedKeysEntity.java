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
package com.mysema.query;

import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

//@Table("GENERATED_KEYS")
public class QGeneratedKeysEntity extends RelationalPathBase<QGeneratedKeysEntity>{

    private static final long serialVersionUID = 2002306246819687158L;

    public final NumberPath<Integer> id = createNumber("ID", Integer.class);

    public final StringPath name = createString("NAME");

    public QGeneratedKeysEntity(String name) {
        super(QGeneratedKeysEntity.class, PathMetadataFactory.forVariable(name), null, "GENERATED_KEYS");
    }

}