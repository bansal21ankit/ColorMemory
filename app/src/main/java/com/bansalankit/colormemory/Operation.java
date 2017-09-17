package com.bansalankit.colormemory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>03 Jul 2017</b></i>
 * <br><i>Modified Date : <b>03 Jul 2017</b></i>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Operation {
    String type();
}
