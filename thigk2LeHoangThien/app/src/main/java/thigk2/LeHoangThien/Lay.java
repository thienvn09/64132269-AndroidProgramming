package thigk2.LeHoangThien;

public class Lay {
    public String title;
    public String time;
    public String hinh;
    public String LinkHinh;

    public Lay(String title, String linkHinh, String hinh, String time) {
        this.title = title;
        LinkHinh = linkHinh;
        this.hinh = hinh;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkHinh() {
        return LinkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        LinkHinh = linkHinh;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
