import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Transaksi {
    String jenis;
    double jumlah;
    String tanggal;

    public Transaksi(String jenis, double jumlah, String tanggal) {
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
    }

    public Transaksi(String tipe, double jumlah) {
    }

    @Override
    public String toString() {
        return "Jenis: " + jenis + ", Jumlah: " + jumlah + ", Tanggal: " + tanggal;
    }

    public void saveToDatabase(int bankId) {
        String sql = "INSERT INTO Transaksi (bank_id, jenis, jumlah, tanggal) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bankId);
            stmt.setString(2, jenis);
            stmt.setDouble(3, jumlah);
            stmt.setString(4, tanggal);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
