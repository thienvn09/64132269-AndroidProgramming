package Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.User;

@Database(entities = {User.class},version = 2,exportSchema = false)
public abstract  class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    private static volatile AppDatabase INSTANCE;
    private static final int Number_of_threads = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(Number_of_threads);
    public static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "kid_edu_user_db_v4") //  Room handle migration
                            .fallbackToDestructiveMigration() // Quan trọng khi thay đổi schema và tăng version
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
