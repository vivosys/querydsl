/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.types.path;

import com.mysema.query.types.Visitor;
import com.mysema.query.types.expr.EBoolean;

/**
 * PBoolean represents boolean path expressions
 * 
 * @author tiwe
 * @see java.lang.Boolean
 * 
 */
@SuppressWarnings("serial")
public class PBoolean extends EBoolean implements Path<Boolean> {

    private final Path<Boolean> pathMixin;

    public PBoolean(Path<?> parent, String property) {
        this(PathMetadataFactory.forProperty(parent, property));
    }

    public PBoolean(PathMetadata<?> metadata) {
        this.pathMixin = new PathMixin<Boolean>(this, metadata);
    }
    
    public PBoolean(String var) {
        this(PathMetadataFactory.forVariable(var));
    }
    
    @Override
    public void accept(Visitor v) {
        v.visit(this);        
    } 

    @Override
    public EBoolean asExpr() {
        return this;
    }
    
    @Override
    public boolean equals(Object o) {
        return pathMixin.equals(o);
    }
    
    @Override
    public PathMetadata<?> getMetadata() {
        return pathMixin.getMetadata();
    }

    @Override
    public Path<?> getRoot() {
        return pathMixin.getRoot();
    }

    @Override
    public int hashCode() {
        return pathMixin.hashCode();
    }

    @Override
    public EBoolean isNotNull() {
        return pathMixin.isNotNull();
    }
    
    @Override
    public EBoolean isNull() {
        return pathMixin.isNull();
    }
    
}