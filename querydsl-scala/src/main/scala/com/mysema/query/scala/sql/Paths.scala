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

package com.mysema.query.scala.sql;

import com.mysema.scala.ReflectionUtils._
import com.mysema.query.scala._
import com.mysema.query.sql._
import com.mysema.query.types._
import com.mysema.query.types.PathMetadataFactory._
import java.util.ArrayList;
import java.lang.reflect._
import scala.reflect.BeanProperty
import scala.collection.JavaConversions.mapAsJavaMap

/**
 * Implementation of RelationsPathImpl for Scala
 * 
 * @author tiwe
 *
 */
class RelationalPathImpl[T](md: PathMetadata[_], schema: String, table: String)(implicit val mf: Manifest[T]) 
  extends BeanPath[T](mf.erasure.asInstanceOf[Class[T]], md) with RelationalPath[T] {
    
  type JList[X] = java.util.List[X]
  
  private var primaryKey: PrimaryKey[T] = _
  
  @BeanProperty
  val columns: JList[Path[_]] = new ArrayList[Path[_]]

  @BeanProperty
  val foreignKeys: JList[ForeignKey[_]] = new ArrayList[ForeignKey[_]]
  
  @BeanProperty
  val inverseForeignKeys: JList[ForeignKey[_]] = new ArrayList[ForeignKey[_]]
  
  @BeanProperty
  lazy val projection: FactoryExpression[T] = RelationalPathUtils.createProjection(this)
  
  def this(variable: String, schema: String, table: String)(implicit mf: Manifest[T]) = this(forVariable(variable), schema, table)(mf)
  
  override def add[P <: Path[_]](p: P): P = { columns.add(p); p }
  
  def createPrimaryKey(cols: Path[_]*): PrimaryKey[T] = {
    primaryKey = new PrimaryKey[T](this, cols:_*)
    primaryKey
  }
  
  def createForeignKey[F](local: Path[_], foreign: String) = {
    val foreignKey = new ForeignKey[F](this, local, foreign)
    foreignKeys.add(foreignKey)
    foreignKey
  }
  
  def createInvForeignKey[F](local: Path[_], foreign: String) = {
    val foreignKey = new ForeignKey[F](this, local, foreign)
    inverseForeignKeys.add(foreignKey)
    foreignKey
  }
  
  def getPrimaryKey = primaryKey
  
  def getSchemaName = schema
  
  def getTableName = table;
  
}