package com.chen.mingz.common.tree.annotation;

import java.lang.annotation.*;

/**
 * Created by chenmingzhi on 18/5/24.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface TreeLeaf {

    String name() default "";

    int order() default 0;

    boolean serialize() default true;
}
