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
package com.mysema.query.types.expr;

import java.util.Date;

import javax.annotation.Nullable;

import com.mysema.query.types.Ops;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathImpl;

/**
 * DateExpression represents Date expressions
 * The date representation is compatible with the Gregorian calendar.
 *
 * @param <T> expression type
 *
 * @author tiwe
 * @see <a href="http://en.wikipedia.org/wiki/Gregorian_calendar">Gregorian calendar</a>
 */
@SuppressWarnings({"unchecked"})
public abstract class DateExpression<T extends Comparable> extends TemporalExpression<T> {

    private static final DateExpression<Date> CURRENT_DATE = currentDate(Date.class);

    private static final long serialVersionUID = 6054664454254721302L;

    /**
     * Get an expression representing the current date as a EDate instance
     *
     * @return
     */
    public static DateExpression<Date> currentDate() {
        return CURRENT_DATE;
    }

    /**
     * Get an expression representing the current date as a EDate instance
     *
     * @return
     */
    public static <T extends Comparable> DateExpression<T> currentDate(Class<T> cl) {
        return DateOperation.create(cl, Ops.DateTimeOps.CURRENT_DATE);
    }

    @Nullable
    private volatile NumberExpression<Integer> dayOfMonth, dayOfWeek, dayOfYear;

    @Nullable
    private volatile DateExpression min, max;

    @Nullable
    private volatile NumberExpression<Integer> week, month, year, yearMonth;

    public DateExpression(Class<? extends T> type) {
        super(type);
    }

    @Override
    public DateExpression<T> as(Path<T> alias) {
        return DateOperation.create((Class<T>)getType(), Ops.ALIAS, this, alias);
    }

    @Override
    public DateExpression<T> as(String alias) {
        return as(new PathImpl<T>(getType(), alias));
    }

    /**
     * Get a day of month expression (range 1-31)
     *
     * @return
     */
    public NumberExpression<Integer> dayOfMonth(){
        if (dayOfMonth == null) {
            dayOfMonth = NumberOperation.create(Integer.class, Ops.DateTimeOps.DAY_OF_MONTH, this);
        }
        return dayOfMonth;
    }

    /**
     * Get a day of week expression (range 1-7 / SUN-SAT)
     * <p>NOT supported in JDOQL and not in Derby</p>
     *
     * @return
     */
    public NumberExpression<Integer> dayOfWeek() {
        if (dayOfWeek == null) { 
            dayOfWeek = NumberOperation.create(Integer.class, Ops.DateTimeOps.DAY_OF_WEEK, this);
        }
        return dayOfWeek;
    }

    /**
     * Get a day of year expression (range 1-356)
     * <p>NOT supported in JDOQL and not in Derby</p>
     *
     * @return
     */
    public NumberExpression<Integer> dayOfYear() {
        if (dayOfYear == null) {
            dayOfYear = NumberOperation.create(Integer.class, Ops.DateTimeOps.DAY_OF_YEAR, this);
        }
        return dayOfYear;
    }

    /**
     * Get the maximum value of this expression (aggregation)
     *
     * @return max(this)
     */
    public DateExpression<T> max(){
        if (max == null) {
            max = DateOperation.create(getType(), Ops.AggOps.MAX_AGG, this);
        }
        return max;
    }

    /**
     * Get the minimum value of this expression (aggregation)
     *
     * @return min(this)
     */
    public DateExpression<T> min(){
        if (min == null) {
            min = DateOperation.create(getType(), Ops.AggOps.MIN_AGG, this);
        }
        return min;
    }

    /**
     * Get a month expression (range 1-12 / JAN-DEC)
     *
     * @return
     */
    public NumberExpression<Integer> month(){
        if (month == null) {
            month = NumberOperation.create(Integer.class, Ops.DateTimeOps.MONTH, this);
        }
        return month;
    }

    /**
     * Get a week expression
     *
     * @return
     */
    public NumberExpression<Integer> week() {
        if (week == null) {
            week = NumberOperation.create(Integer.class, Ops.DateTimeOps.WEEK,  this);
        }
        return week;
    }

    /**
     * Get a year expression
     *
     * @return
     */
    public NumberExpression<Integer> year(){
        if (year == null) {
            year = NumberOperation.create(Integer.class, Ops.DateTimeOps.YEAR, this);
        }
        return year;
    }

    /**
     * Get a year / month expression
     *
     * @return
     */
    public NumberExpression<Integer> yearMonth(){
        if (yearMonth == null) {
            yearMonth = NumberOperation.create(Integer.class, Ops.DateTimeOps.YEAR_MONTH, this);
        }
        return yearMonth;
    }
}
