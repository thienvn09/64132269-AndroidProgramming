package Model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
@Entity(tableName = "students")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @NonNull
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name="gender")
    public String gender;
    @ColumnInfo(name="school")
    public String school;
    @ColumnInfo(name="class")
    public String clas;
    @NonNull
    @ColumnInfo(name = "password")
    public String Passhash;

    public User() {
        this.uid = uid;
        this.name = name;
        this.school = school;
        this.Passhash = Passhash;
        this.clas = clas;
        this.gender = gender;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    @NonNull
    public String getPasshash() {
        return Passhash;
    }

    public void setPasshash(@NonNull String passhash) {
        Passhash = passhash;
    }
}
