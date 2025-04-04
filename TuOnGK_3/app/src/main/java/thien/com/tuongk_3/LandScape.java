package thien.com.tuongk_3;

public class LandScape {
    public String Title;
    public String LinkHinh;

    public LandScape(String title, String linkHinh) {
        Title = title;
        LinkHinh = linkHinh;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLinkHinh() {
        return LinkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        LinkHinh = linkHinh;
    }
}
