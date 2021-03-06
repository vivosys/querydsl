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
package com.mysema.query.codegen;

import static com.mysema.codegen.Symbols.UNCHECKED;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Named;

import com.mysema.codegen.CodeWriter;
import com.mysema.codegen.model.ClassType;
import com.mysema.codegen.model.Type;
import com.mysema.codegen.model.TypeCategory;
import com.mysema.codegen.model.Types;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.expr.ComparableExpression;
import com.mysema.query.types.path.*;

/**
 * EmbeddableSerializer is a Serializer implementation for embeddable types
 *
 * @author tiwe
 *
 */
public final class EmbeddableSerializer extends EntitySerializer {
    
    @Inject
    public EmbeddableSerializer(TypeMappings typeMappings, @Named("keywords") Collection<String> keywords) {
        super(typeMappings, keywords);
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    protected void introClassHeader(CodeWriter writer, EntityType model) throws IOException {
        Type queryType = typeMappings.getPathType(model, model, true);

        TypeCategory category = model.getOriginalCategory();
        Class<? extends Path> pathType;
        if (model.getProperties().isEmpty()) {
            switch(category){
                case COMPARABLE : pathType = ComparablePath.class; break;
                case ENUM: pathType = EnumPath.class; break;
                case DATE: pathType = DatePath.class; break;
                case DATETIME: pathType = DateTimePath.class; break;
                case TIME: pathType = TimePath.class; break;
                case NUMERIC: pathType = NumberPath.class; break;
                case STRING: pathType = StringPath.class; break;
                case BOOLEAN: pathType = BooleanPath.class; break;
                default : pathType = BeanPath.class;
            }
        } else {
            pathType = BeanPath.class;
        }

        for (Annotation annotation : model.getAnnotations()){
            writer.annotation(annotation);
        }
        
        writer.line("@Generated(\"", getClass().getName(), "\")");

        if (category == TypeCategory.BOOLEAN || category == TypeCategory.STRING) {
            writer.beginClass(queryType, new ClassType(pathType));
        } else {
            writer.beginClass(queryType, new ClassType(category,pathType, model));
        }

        // TODO : generate proper serialVersionUID here
        writer.privateStaticFinal(Types.LONG_P, "serialVersionUID", String.valueOf(model.hashCode()));
    }

//    @Override
//    protected void constructorsForVariables(CodeWriter writer, EntityType model) {
//        // no root constructors
//    }

//    @Override
//    protected void introDefaultInstance(CodeWriter writer, EntityType model) {
//        // no default instance
//    }

    @Override
    protected void introFactoryMethods(CodeWriter writer, EntityType model) throws IOException {
        // no factory methods
    }

    @Override
    protected void introImports(CodeWriter writer, SerializerConfig config, EntityType model) throws IOException {
        writer.staticimports(PathMetadataFactory.class);
        
        Type queryType = typeMappings.getPathType(model, model, true);
        if (!model.getPackageName().isEmpty()
            && !queryType.getPackageName().equals(model.getPackageName()) 
            && !queryType.getSimpleName().equals(model.getSimpleName())) {
            String fullName = model.getFullName();
            String packageName = model.getPackageName();
            if (fullName.substring(packageName.length()+1).contains(".")) {
                fullName = fullName.substring(0, fullName.lastIndexOf('.'));
            }
            writer.importClasses(fullName);
        }
        
        introDelegatePackages(writer, model);

        List<Package> packages = new ArrayList<Package>();
        packages.add(PathMetadata.class.getPackage());
        packages.add(SimplePath.class.getPackage());
//        if ((model.hasLists() && config.useListAccessors())
//                || !model.getDelegates().isEmpty()
//                || (model.hasMaps() && config.useMapAccessors())){
//            packages.add(ComparableExpression.class.getPackage());
//        }
        
        if (isImportExprPackage(model)) {
            packages.add(ComparableExpression.class.getPackage());
        }
        
        writer.imports(packages.toArray(new Package[packages.size()]));
        
        writer.imports(Generated.class);
    }

}
