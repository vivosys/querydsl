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

package com.mysema.query.scala;

import com.mysema.query.types._
import com.mysema.query.types.{ Projections => ProjectionsFactory }
import java.util.{ Arrays }

object Projections extends Projections

/**
 * @author tiwe
 *
 */
trait Projections {
  
  /**
   * Create a FactoryExpressions for the given type and arguments
   *  
   * @param expressions
   * @param mf
   * @return
   */
  def create[T](expressions: Ex[_]*)(implicit mf: Manifest[T]) = {
    val clazz = mf.erasure.asInstanceOf[Class[T]]
    try {
      clazz.getConstructor()
      ProjectionsFactory.fields[T](clazz, expressions:_*)
    } catch { case e: NoSuchMethodException => {
      ProjectionsFactory.constructor[T](clazz, expressions:_*)   
    }}    
  }
  
  implicit def tuple2Expr[T1,T2](t: (_ <: Ex[T1], _ <: Ex[T2])) = new Tu2Ex[T1,T2](t._1, t._2) 

  implicit def tuple3Expr[T1,T2,T3](t: (_ <: Ex[T1], _ <: Ex[T2], _ <: Ex[T3])) = {
    new Tu3Ex[T1,T2,T3](t._1, t._2, t._3) 
  }

  implicit def tuple4Expr[T1,T2,T3,T4](t: (_ <: Ex[T1], _ <: Ex[T2], _ <: Ex[T3], _ <: Ex[T4])) = {
    new Tu4Ex[T1,T2,T3,T4](t._1, t._2, t._3, t._4)
  }
  
  implicit def tuple5Expr[T1,T2,T3,T4,T5](t: (_ <: Ex[T1], _ <: Ex[T2], _ <: Ex[T3], _ <: Ex[T4], _<: Ex[T5])) = {
    new Tu5Ex[T1,T2,T3,T4,T5](t._1, t._2, t._3, t._4, t._5)
  }
     
}

class Tuple2Expression[T1,T2](args: Ex[_]*) 
extends FactoryExpressionBase[(T1,T2)](classOf[Tuple2[T1,T2]], args:_*) {  
  def newInstance(args: AnyRef*): (T1,T2) = {
    (args(0).asInstanceOf[T1], args(1).asInstanceOf[T2]) 
  }     
}

class Tuple3Expression[T1,T2,T3](args: Ex[_]*) 
extends FactoryExpressionBase[Tuple3[T1,T2,T3]](classOf[Tuple3[T1,T2,T3]], args:_*) {
  
  def newInstance(args: AnyRef*): (T1,T2,T3) = {
    (args(0).asInstanceOf[T1], args(1).asInstanceOf[T2], args(2).asInstanceOf[T3])
  }   
}

class Tuple4Expression[T1,T2,T3,T4](args: Ex[_]*) 
extends FactoryExpressionBase[Tuple4[T1,T2,T3,T4]](classOf[Tuple4[T1,T2,T3,T4]], args:_*) {
  
  def newInstance(args: AnyRef*): (T1,T2,T3,T4) = {
    (args(0).asInstanceOf[T1], args(1).asInstanceOf[T2], args(2).asInstanceOf[T3], args(3).asInstanceOf[T4])
  }   
}

class Tuple5Expression[T1,T2,T3,T4,T5](args: Ex[_]*) 
extends FactoryExpressionBase[Tuple5[T1,T2,T3,T4,T5]](classOf[Tuple5[T1,T2,T3,T4,T5]], args:_*) {
  
  def newInstance(args: AnyRef*): (T1,T2,T3,T4,T5) = {
    (args(0).asInstanceOf[T1], args(1).asInstanceOf[T2], args(2).asInstanceOf[T3], args(3).asInstanceOf[T4], args(4).asInstanceOf[T5])
  }   
}

abstract class FactoryExpressionBase[T](cl: Class[T], args: Ex[_]*) extends ExpressionBase[T](cl) with FactoryExpression[T] {
  
  def accept[R,C](v: Visitor[R,C], context: C): R = v.visit(this, context)
  
  def getArgs(): java.util.List[Ex[_]] = Arrays.asList(args:_*)
  
  def apply(i: Int): Ex[_] = args(i)
  
} 
