package me.stevenlol.damien.sql;

import java.sql.SQLException;

public interface Callback<T> {

    void callback(T t) throws SQLException;

}
