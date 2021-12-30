package com.example.taskmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.coroutines.coroutineContext


@Database(entities = [Task::class], version = 3)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskListDao(): TaskListDao
    abstract fun taskDetailDao(): TaskDetailDao

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE task "
                            + " ADD COLUMN category TEXT default ''"
                )
            }
        }

        val MIGRATION_2_3: Migration = object: Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE task " +
                            "ADD COLUMN tags TEXT DEFAULT ''"
                )
            }
        }

        fun getDatabase(context: Context) = instance
            ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build().also { instance = it }
            }

    }

    @RunWith(AndroidJUnit4::class)
    class MigrationTest {
        private val TEST_DB = "migration-test"

        // Array of all migrations
        private val ALL_MIGRATIONS = arrayOf(
            MIGRATION_1_2, MIGRATION_2_3)

        @get:Rule
        val helper: MigrationTestHelper = MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            TaskDatabase::class.java.canonicalName,
            FrameworkSQLiteOpenHelperFactory()
        )

        @Test
        @Throws(IOException::class)
        fun migrateAll() {
            // Create earliest version of the database.
            helper.createDatabase(TEST_DB, 1).apply {
                execSQL("INSERT INTO TEST_DB VALUES(1,'Grocery', 'Buy Milk and Bread',0,0)")
                execSQL("INSERT INTO TEST_DB VALUES(2,'Bill Payment', 'Pay Electricity Bill',2,0)")
                execSQL("INSERT INTO TEST_DB VALUES(3,'Shoes', 'Buy Shoes',1,0)")
                close()
            }

            // Open latest version of the database. Room will validate the schema
            // once all migrations execute.
            Room.databaseBuilder(
                InstrumentationRegistry.getInstrumentation().targetContext,
                TaskDatabase::class.java,
                TEST_DB
            ).addMigrations(*ALL_MIGRATIONS).build().apply {
                openHelper.writableDatabase.close()
            }
        }
    }

}