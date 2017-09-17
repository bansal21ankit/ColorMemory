package com.bansalankit.colormemory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>03 Jul 2017</b></i>
 * <br><i>Modified Date : <b>03 Jul 2017</b></i>
 */
public abstract class DatabaseModel {
    private Table mTable;
    private Field[] mColumns;

    public DatabaseModel() {
        mTable = getClass().getAnnotation(Table.class);
        mColumns = getClass().getFields();
    }

    public long insert() {
        // TODO Add Table creation and insertion commands.
        return 0; // FIXME Return insertion ID
    }

    public boolean delete() {
        // TODO Create a delete query
        return false;
    }
}
