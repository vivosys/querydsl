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
package com.mysema.query.types;

import java.util.Locale;

import org.apache.commons.collections15.Transformer;

/**
 * Converters provides expression converters for lower case, upper case and prefix/suffix conversions
 * 
 * @author tiwe
 *
 */
public final class Converters {
    
    private final String escape1, escape2;
    
    /**
     * Create a new Converters instance
     * 
     * @param escape escape character to be used
     */
    public Converters(char escape){
        escape1 = escape + "%";
        escape2 = escape + "_";
    }
    
    public final Transformer<Expression<String>,Expression<String>> toLowerCase = 
        new Transformer<Expression<String>,Expression<String>>() {
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>) {
                return ConstantImpl.create(arg.toString().toLowerCase(Locale.ENGLISH));
            } else {
                return new OperationImpl<String>(String.class, Ops.LOWER, arg);
            }
        }
    };

    public final Transformer<Expression<String>,Expression<String>> toUpperCase = 
        new Transformer<Expression<String>,Expression<String>>() {
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>){
                return ConstantImpl.create(arg.toString().toUpperCase(Locale.ENGLISH));
            } else {
                return new OperationImpl<String>(String.class, Ops.UPPER, arg);
            }
        }
    };

    public final Transformer<Expression<String>,Expression<String>> toStartsWithViaLike = 
        new Transformer<Expression<String>,Expression<String>>() {
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>) {
                return ConstantImpl.create(escapeForLike((Constant<String>)arg) + "%"); 
            } else {
                return new OperationImpl<String>(String.class, Ops.CONCAT, arg, ConstantImpl.create("%"));
            }
        }
    };

    public final Transformer<Expression<String>,Expression<String>> toStartsWithViaLikeLower = 
        new Transformer<Expression<String>,Expression<String>>(){
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>) {
                return ConstantImpl.create(escapeForLike((Constant<String>)arg).toLowerCase(Locale.ENGLISH) + "%"); 
            } else {
                Expression<String> concated = new OperationImpl<String>(String.class, Ops.CONCAT, arg, ConstantImpl.create("%"));
                return new OperationImpl<String>(String.class, Ops.LOWER, concated);
            }
        }
    };

    public final Transformer<Expression<String>,Expression<String>> toEndsWithViaLike =
        new Transformer<Expression<String>,Expression<String>>(){
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>) {
                return ConstantImpl.create("%" + escapeForLike((Constant<String>)arg)); 
            } else {
                return new OperationImpl<String>(String.class, Ops.CONCAT, ConstantImpl.create("%"), arg);
            }
        }
    };

    public final Transformer<Expression<String>,Expression<String>> toEndsWithViaLikeLower = 
        new Transformer<Expression<String>,Expression<String>>(){
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>) {
                return ConstantImpl.create("%" + escapeForLike((Constant<String>)arg).toLowerCase(Locale.ENGLISH)); 
            } else {
                Expression<String> concated = new OperationImpl<String>(String.class, Ops.CONCAT, ConstantImpl.create("%"), arg);
                return new OperationImpl<String>(String.class, Ops.LOWER, concated);
            }
        }
    };

    public final Transformer<Expression<String>,Expression<String>> toContainsViaLike = 
        new Transformer<Expression<String>,Expression<String>>(){
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>) {
                return ConstantImpl.create("%" + escapeForLike((Constant<String>)arg) + "%"); 
            } else {
                Expression<String> concated = new OperationImpl<String>(String.class, Ops.CONCAT, ConstantImpl.create("%"), arg);
                return new OperationImpl<String>(String.class, Ops.CONCAT, concated, ConstantImpl.create("%"));
            }
        }
    };

    public final Transformer<Expression<String>,Expression<String>> toContainsViaLikeLower = 
        new Transformer<Expression<String>,Expression<String>>(){
        @Override
        public Expression<String> transform(Expression<String> arg) {
            if (arg instanceof Constant<?>) {
                return ConstantImpl.create("%" + escapeForLike((Constant<String>)arg).toLowerCase(Locale.ENGLISH) + "%"); 
            } else {
                Expression<String> concated = new OperationImpl<String>(String.class, Ops.CONCAT, ConstantImpl.create("%"), arg);
                concated = new OperationImpl<String>(String.class, Ops.CONCAT, concated, ConstantImpl.create("%"));
                return new OperationImpl<String>(String.class, Ops.LOWER, concated);
            }
        }
    };
    
    public String escapeForLike(Constant<String> expr) {
        String str = expr.getConstant();
        if (str.contains("%") || str.contains("_")) {
            str = str.replace("%", escape1).replace("_", escape2);
        }
        return str;
    }

}
