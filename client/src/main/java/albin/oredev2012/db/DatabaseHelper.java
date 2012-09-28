package albin.oredev2012.db;

import java.sql.SQLException;

import albin.oredev2012.model.Session;
import albin.oredev2012.model.Speaker;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "oredev.db";
	private static final int DATABASE_VERSION = 3;

	private RuntimeExceptionDao<Speaker, String> speakerDao = null;
	private RuntimeExceptionDao<Session, String> sessionDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Speaker.class);
			TableUtils.createTable(connectionSource, Session.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Speaker.class, true);
			TableUtils.dropTable(connectionSource, Session.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It
	 * will create it or just give the cached value.
	 */
	public RuntimeExceptionDao<Speaker, String> getSpeakerDao() {
		if (speakerDao == null) {
			speakerDao = getRuntimeExceptionDao(Speaker.class);
		}
		return speakerDao;
	}
	
	public RuntimeExceptionDao<Session, String> getSessionDao() {
		if (sessionDao == null) {
			sessionDao = getRuntimeExceptionDao(Session.class);
		}
		return sessionDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		speakerDao = null;
		sessionDao = null;
	}
}