package Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import Model.User;

@Dao
public interface UserDao{
    // đăng ký
    @Insert
    void register(User user);
    // truy vấn đăng nhập
    @Query("SELECT * FROM students WHERE name = :nameParam AND password = :passwordHashParam LIMIT 1")
    User dangnhap(String nameParam, String passwordHashParam);
    // Phương thức mới để lấy người dùng gần nhất (dựa trên ID lớn nhất)
    @Query("SELECT * FROM students ORDER BY uid DESC LIMIT 1")
    User getLastRegisteredUser();
}
