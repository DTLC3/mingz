package com.chen.mingz.common.tree.annotation;

import java.lang.annotation.*;

/**
 * Created by chenmingzhi on 18/5/24.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TreeCore {

    String id();

    String parentId();

    String child() default "children";

    String order() default "";


}
