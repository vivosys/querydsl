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
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.RelationalPathBase;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BeanPath;
import com.mysema.query.types.path.NumberPath;


/**
 * SPlayer is a Querydsl query type for SPlayer
 */
public class SPlayer extends RelationalPathBase<SPlayer> implements RelationalPath<SPlayer> {

    private static final long serialVersionUID = 552776042;

    public static final SPlayer player = new SPlayer("PLAYER");

    public final NumberPath<Long> id = createNumber("ID", Long.class);

    public final PrimaryKey<SPlayer> sql100819184437420 = createPrimaryKey(id);

    public final ForeignKey<SPlayerScores> _fkd5dc571ff51f2004 = new ForeignKey<SPlayerScores>(this, id, "PLAYER_ID");

    public SPlayer(String variable) {
        super(SPlayer.class, forVariable(variable), null, "PLAYER");
    }

    public SPlayer(BeanPath<? extends SPlayer> entity) {
        super(entity.getType(), entity.getMetadata(), null, "PLAYER");
    }

    public SPlayer(PathMetadata<?> metadata) {
        super(SPlayer.class, metadata, null, "PLAYER");
    }

}

