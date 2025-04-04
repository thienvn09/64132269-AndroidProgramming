package thien.com.luyentapgk_lan2;

public class LandScape {
    public String Ten;
    public String LinkHinh;

    public LandScape(String ten, String linkHinh) {
        Ten = ten;
        LinkHinh = linkHinh;
    }

    public String getLinkHinh() {
        return LinkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        LinkHinh = linkHinh;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }
}
