package Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import Model.User;

@Dao
public interface UserDao{
    @Insert
    void register(User user);
    @Query("SELECT * FROM students WHERE name = :name AND password = :Passhash LIMIT 1")
    User Dangky(String name, String Passhash);
    // truy vấn đăng nhập
    @Query("SELECT * FROM students WHERE name = :name AND password = :passwordHash LIMIT 1")
    User dangnhap(String name, String passwordHash);
}
