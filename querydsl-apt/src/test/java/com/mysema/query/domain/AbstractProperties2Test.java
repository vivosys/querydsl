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
package com.mysema.query.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import org.junit.Test;

import com.mysema.query.annotations.QueryEntity;

public class AbstractProperties2Test {

    public abstract class GenericEntity<K extends Serializable & Comparable<K>, E extends GenericEntity<K, ?>> {

        public abstract K getId();

        public abstract void setId(K id);

    }

    public abstract class AbstractEntity<P extends AbstractEntity<P>> extends GenericEntity<Integer, P> {

        private Integer id;

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public void setId(Integer id) {
            this.id = id;
        }

    }

    @QueryEntity
    public class User extends AbstractEntity<User> {

    }
    
    @Test
    public void GenericEntity() {
        assertNotNull(QAbstractProperties2Test_GenericEntity.genericEntity.id);
    }

    @Test
    public void AbstractEntity() {
        assertNotNull(QAbstractProperties2Test_AbstractEntity.abstractEntity.id);
        assertEquals(QAbstractProperties2Test_GenericEntity.class, QAbstractProperties2Test_AbstractEntity.abstractEntity._super.getClass());
    }
    
    @Test
    public void User() {
        assertNotNull(QAbstractProperties2Test_User.user.id);
        assertEquals(QAbstractProperties2Test_AbstractEntity.class, QAbstractProperties2Test_User.user._super.getClass());
    }

}
