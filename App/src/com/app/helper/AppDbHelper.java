package com.app.helper;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.app.dao.UserDao;
import com.app.util.Logger;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class AppDbHelper extends OrmLiteSqliteOpenHelper {
	private static String TAG = AppDbHelper.class.getSimpleName();

	// name of the database file
	private static final String DATABASE_NAME = "App.sqlite";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 1;

	// the DAO object we use to access the table
	private Dao<UserDao, Integer> userDao = null;

	public AppDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Logger.i(TAG, "Creating DatabaseHelper object");
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		Logger.i(TAG, "Entering onCreate()");
		try {
			TableUtils.createTable(connectionSource, UserDao.class);
		} catch (SQLException e) {
			Logger.e(TAG, "Can't create database." + e);
			throw new RuntimeException(e);
		} finally {
			Logger.i(TAG, "Exiting onCreate()");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		Logger.i(TAG, "Entering onUpgrade()");
		try {
			TableUtils.createTableIfNotExists(connectionSource, UserDao.class);
		} catch (SQLException e) {
			Logger.e(TAG, "Can't create database." + e);
			throw new RuntimeException(e);
		} finally {
			Logger.i(TAG, "Exiting onUpgrade()");
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our User class. It will
	 * create it or just give the cached value.
	 */
	public Dao<UserDao, Integer> getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(UserDao.class);
		}
		return userDao;
	}

	public void clearUserTable() throws SQLException {
		TableUtils.clearTable(connectionSource, UserDao.class);
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		userDao = null;
	}

}
