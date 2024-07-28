import java.util.ArrayList;
import java.util.List;

public class Akun {
    private String nomorAkun;
    private String namaPemilikAkun;
    private double saldo;
    private String namaBank;
    private List<Transaksi> riwayatTransaksi;

    public Akun(String nomorAkun, String namaPemilikAkun, double saldoAwal, String namaBank) {
        this.nomorAkun = nomorAkun;
        this.namaPemilikAkun = namaPemilikAkun;
        this.saldo = saldoAwal;
        this.namaBank = namaBank;
        this.riwayatTransaksi = new ArrayList<>();
        tambahTransaksi("Saldo awal", saldoAwal);
    }

    public String getNomorAkun() {
        return nomorAkun;
    }

    public String getNamaPemilikAkun() {
        return namaPemilikAkun;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public List<Transaksi> getRiwayatTransaksi() {
        return riwayatTransaksi;
    }

    public void setor(double jumlah) {
        if (jumlah > 0) {
            saldo += jumlah;
            tambahTransaksi("Setor", jumlah);
            System.out.println("Setoran berhasil. Saldo baru: " + saldo);
        } else {
            System.out.println("Jumlah setoran tidak valid.");
        }
    }

    public void tarik(double jumlah) {
        if (jumlah > 0 && jumlah <= saldo) {
            saldo -= jumlah;
            tambahTransaksi("Tarik", jumlah);
            System.out.println("Penarikan berhasil. Saldo baru: " + saldo);
        } else {
            System.out.println("Jumlah penarikan tidak valid.");
        }
    }

    public void transfer(Akun akunTujuan, double jumlah, double biaya) {
        if (jumlah + biaya <= saldo) {
            saldo -= (jumlah + biaya);
            akunTujuan.setor(jumlah);
            tambahTransaksi("Transfer ke " + akunTujuan.getNomorAkun(), jumlah + biaya);
            akunTujuan.tambahTransaksi("Transfer dari " + this.nomorAkun, jumlah);
            System.out.println("Transfer berhasil. Saldo baru: " + saldo);
        } else {
            System.out.println("Saldo tidak mencukupi untuk transfer.");
        }
    }

    private void tambahTransaksi(String tipe, double jumlah) {
        riwayatTransaksi.add(new Transaksi(tipe, jumlah));
    }
}