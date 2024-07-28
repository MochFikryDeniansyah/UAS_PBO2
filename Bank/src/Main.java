import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bankSaya = new Bank(1); // Assume bank ID 1 for this example
        Bank bankLain = new Bank(2); // Assume bank ID 2 for the other bank

        boolean selesai = false;

        while (!selesai) {
            System.out.println("Selamat datang di Aplikasi Bank Sederhana");
            System.out.println("1. Cek Saldo Bank");
            System.out.println("2. Input Saldo Bank");
            System.out.println("3. Rekap Transaksi Perbulan");
            System.out.println("4. Transfer Antar Bank");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline

            switch (pilihan) {
                case 1:
                    System.out.println("Saldo saat ini: " + bankSaya.cekSaldo());
                    break;
                case 2:
                    System.out.print("Masukkan jumlah saldo yang ingin ditambahkan: ");
                    double jumlahSetoran = scanner.nextDouble();
                    scanner.nextLine(); // Konsumsi newline
                    System.out.print("Masukkan tanggal (yyyy-mm-dd): ");
                    String tanggalSetoran = scanner.nextLine();
                    bankSaya.inputSaldo(jumlahSetoran, tanggalSetoran);
                    break;
                case 3:
                    System.out.print("Masukkan bulan untuk rekap transaksi (yyyy-mm): ");
                    String bulan = scanner.nextLine();
                    bankSaya.rekapTransaksi(bulan);
                    break;
                case 4:
                    System.out.print("Masukkan jumlah transfer: ");
                    double jumlahTransfer = scanner.nextDouble();
                    scanner.nextLine(); // Konsumsi newline
                    System.out.print("Masukkan tanggal (yyyy-mm-dd): ");
                    String tanggalTransfer = scanner.nextLine();
                    System.out.print("Apakah ini transfer antar bank? (true/false): ");
                    boolean antarBank = scanner.nextBoolean();
                    scanner.nextLine(); // Konsumsi newline
                    bankSaya.transfer(bankLain, jumlahTransfer, antarBank, tanggalTransfer);
                    break;
                case 5:
                    selesai = true;
                    System.out.println("Terima kasih telah menggunakan Aplikasi Bank Sederhana.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }

        scanner.close();
    }
}
