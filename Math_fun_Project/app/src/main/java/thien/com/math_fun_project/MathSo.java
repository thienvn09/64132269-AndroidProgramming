package thien.com.math_fun_project;

public class MathSo {
    public int soA;
    public int soB;
    public String Dau;
    public double ketqua;

    public MathSo(int soA, int soB, String dau, double ketqua) {
        this.soA = soA;
        this.soB = soB;
        this.Dau = dau;
        this.ketqua = ketqua;

    }

    public int getSoA() {
        return soA;
    }

    public void setSoA(int soA) {
        this.soA = soA;
    }

    public int getSoB() {
        return soB;
    }

    public void setSoB(int soB) {
        this.soB = soB;
    }

    public String getDau() {
        return Dau;
    }

    public void setDau(String dau) {
        Dau = dau;
    }

    public double getKetqua() {
        return ketqua;
    }

    public void setKetqua(double ketqua) {
        this.ketqua = ketqua;
    }
}
