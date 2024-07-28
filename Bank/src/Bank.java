import java.sql.*;
import java.util.ArrayList;

class Bank {
    private int id;
    private double saldo;
    private ArrayList<Transaksi> riwayatTransaksi;

    public Bank(int id) {
        this.id = id;
        this.saldo = fetchSaldoFromDatabase();
        this.riwayatTransaksi = fetchTransaksiFromDatabase();
    }

    public double cekSaldo() {
        return saldo;
    }

    public void inputSaldo(double jumlah, String tanggal) {
        saldo += jumlah;
        updateSaldoInDatabase();
        Transaksi transaksi = new Transaksi("Setoran", jumlah, tanggal);
        riwayatTransaksi.add(transaksi);
        transaksi.saveToDatabase(id);
    }

    public void transfer(Bank penerima, double jumlah, boolean antarBank, String tanggal) {
        double biaya = antarBank ? 6500 : 0;
        if (saldo >= jumlah + biaya) {
            saldo -= (jumlah + biaya);
            updateSaldoInDatabase();
            penerima.terimaTransfer(jumlah, tanggal);
            Transaksi transaksi = new Transaksi("Transfer Keluar", jumlah, tanggal);
            riwayatTransaksi.add(transaksi);
            transaksi.saveToDatabase(id);
            if (antarBank) {
                Transaksi biayaTransaksi = new Transaksi("Biaya Transfer", biaya, tanggal);
                riwayatTransaksi.add(biayaTransaksi);
                biayaTransaksi.saveToDatabase(id);
            }
        } else {
            System.out.println("Saldo tidak mencukupi untuk transfer.");
        }
    }

    public void terimaTransfer(double jumlah, String tanggal) {
        saldo += jumlah;
        updateSaldoInDatabase();
        Transaksi transaksi = new Transaksi("Transfer Masuk", jumlah, tanggal);
        riwayatTransaksi.add(transaksi);
        transaksi.saveToDatabase(id);
    }

    public void rekapTransaksi(String bulan) {
        System.out.println("Rekap Transaksi Bulan " + bulan + ":");
        for (Transaksi transaksi : riwayatTransaksi) {
            if (transaksi.tanggal.startsWith(bulan)) {
                System.out.println(transaksi);
            }
        }
    }

    private double fetchSaldoFromDatabase() {
        String sql = "SELECT saldo FROM Bank WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void updateSaldoInDatabase() {
        String sql = "UPDATE Bank SET saldo = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, saldo);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Transaksi> fetchTransaksiFromDatabase() {
        ArrayList<Transaksi> transaksiList = new ArrayList<>();
        String sql = "SELECT jenis, jumlah, tanggal FROM Transaksi WHERE bank_id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String jenis = rs.getString("jenis");
                double jumlah = rs.getDouble("jumlah");
                String tanggal = rs.getString("tanggal");
                transaksiList.add(new Transaksi(jenis, jumlah, tanggal));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transaksiList;
    }
}
