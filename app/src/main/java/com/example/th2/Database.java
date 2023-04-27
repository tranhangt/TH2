package com.example.th2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "android.db";
    private final static int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table task(" +
                "id integer primary key autoincrement, " +
                "name text, " +
                "content text, " +
                "date text, " +
                "status text, " +
                "collaborate int)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertTask(Task task) {
        String sql = "insert into task(name, content, date, status, collaborate) values(?,?,?,?,?)";
        String[] args = {task.getName(), task.getContent(), task.getDate(), task.getStatus(), String.valueOf(task.getColab())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql, args);
    }

    public List<Task> getAll() {
        List<Task> list = new ArrayList<>();
        String sql = "select m.id, m.name, m.content, m.date, m.status, m.collaborate " +
                "from task m";
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql, null);
        while (rs != null && rs.moveToNext()) {
            int collaborate = 0;
            if (rs.getInt(5) == 1) collaborate = 1;
            list.add(new Task(rs.getInt(0), rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), collaborate));
        }
        assert rs != null;
        rs.close();
        return list;
    }
//    public List<Task> getAll() {
//        List<Task> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor rs = sqLiteDatabase.query(
//                "task",
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//        while ((rs != null) && (rs.moveToNext())) {
//            int id = rs.getInt(0);
//            String name = rs.getString(1);
//            String content = rs.getString(2);
//            String date = rs.getString(3);
//            String status = rs.getString(4);
//            int isColab = rs.getInt(5);
//            list.add(new Task(id, name, content, date, status, isColab));
//        }
//        return list;
//    }

    public void updateTask(Task task) {
        String sql = "UPDATE task SET name = ?, content=? ,date=? ,status=?, collaborate=? WHERE id = ?";
        String[] args = {task.getName(), task.getContent(), task.getDate(), task.getStatus(), String.valueOf(task.getColab()), String.valueOf(task.getId())};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }

    public void deleteTask(int id) {
        String sql = "DELETE FROM task WHERE id = ?";
        String[] args = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }

    public List<Task> searchItemBykey(String key) {
        List<Task> list = new ArrayList<>();
        String sql = "select t.id,t.name,t.content,t.date,t.status,t.collaborate " +
                "from task t " +
                "where t.name like ? or t.content like ?";
        String[] agrs = {"%" + key + "%", "%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.rawQuery(sql, agrs);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int collaborate = rs.getInt(5);
            list.add(new Task(id, name, content, date, status, collaborate));
        }
        rs.close();
        return list;
    }

//    public void updateSpending(Spending spending) {
//        String sql = "UPDATE spending SET name = ?, description=? ,price=? ,date=? WHERE id = ?";
//        String[] args = {spending.getName(), spending.getDescription(), String.valueOf(spending.getPrice()), spending.getDate(), String.valueOf(spending.getId())};
//        SQLiteDatabase st = getWritableDatabase();
//        st.execSQL(sql, args);
//    }

//    public Spending getItemById(int id) {
//        String whereClause = "id = ?";
//        String[] whereArgs = {Integer.toString(id)};
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor rs = sqLiteDatabase.query("spending",
//                null, whereClause, whereArgs,
//                null, null, null);
//        if (rs != null && rs.moveToFirst()) {
//            String name = rs.getString(1);
//            String description = rs.getString(2);
//            double price = rs.getDouble(3);
//            String date = rs.getString(4);
//            rs.close();
//            return new Spending(id, name, description, price, date);
//        }
//        return null;
//    }

//    public List<Spending> getAllToDay() {
//        List<Spending> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        String where = "date like ?";
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        String[] args = {sdf.format(new Date())};
//        Cursor rs = sqLiteDatabase.query(
//                "spending",
//                null,
//                where,
//                args,
//                null,
//                null,
//                null
//        );
//        while ((rs != null) && (rs.moveToNext())) {
//            int id = rs.getInt(0);
//            String name = rs.getString(1);
//            String description = rs.getString(2);
//            double price = rs.getDouble(3);
//            String date = rs.getString(4);
//            list.add(new Spending(id, name, description, price, date));
//        }
//        return list;
//    }

//    public List<Spending> getByDateFromTo(String from,String to) {
//        List<Spending> list = new ArrayList<>();
//        String whereClause = "date BETWEEN ? AND ?";
//        String[] whereArgs = { from.trim(),to.trim()};
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        Cursor rs = sqLiteDatabase.query("spending",
//                null, whereClause, whereArgs,
//                null, null, null);
//        while ((rs != null) && (rs.moveToNext())) {
//            int id= rs.getInt(0);
//            String name = rs.getString(1);
//            String description = rs.getString(2);
//            double price = rs.getDouble(3);
//            String date = rs.getString(4);
//            list.add(new Spending(id,name,description,price,date));
//        }
//        return list;
//    }

    public List<Task> searchByName(String key) {
        List<Task> list = new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("task",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int collaborate = rs.getInt(5);
            list.add(new Task(id, name, content, date, status, collaborate));
        }
        return list;
    }

    public List<Task> searchByStatus(String key) {
        List<Task> list = new ArrayList<>();
        String whereClause = "status like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("task",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int collaborate = rs.getInt(5);
            list.add(new Task(id, name, content, date, status, collaborate));
        }
        return list;
    }
}
