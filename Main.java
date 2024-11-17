import java.text.DecimalFormat;
import java.util.Scanner;

class Menu {
    String nama;
    double harga;
    String kategori;

    public Menu(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}

public class Main {
    public static Menu[] menuList = {
        new Menu("Nasi Goreng", 15000, "Makanan"),
        new Menu("Seafood", 50000, "Makanan"),
        new Menu("Pecel", 15000, "Makanan"),
        new Menu("Bakso", 20000, "Makanan"),
        new Menu("Air mineral", 7000, "Minuman"),
        new Menu("Jus Buah", 12000, "Minuman"),
        new Menu("Es Teh", 8000, "Minuman"),
        new Menu("Kopi Latte", 21000, "Minuman"),
    };

    private static final DecimalFormat format = new DecimalFormat("#,###.00");
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMenu() {
        System.out.println("Makanan: ");
        for (Menu menu : menuList) {
            if (menu.kategori.equals("Makanan")) {
                System.out.println(menu.nama + " - Rp " + format.format(menu.harga));
            }
        }
        System.out.println("\nMinuman: ");
        for (Menu menu : menuList) {
            if (menu.kategori.equals("Minuman")) {
                System.out.println(menu.nama + " - Rp " + format.format(menu.harga));
            }
        }
    }

    public static void showMenuUtama() {
        boolean running = true;

        while (running) {
            System.out.println("=== MENU UTAMA ===");
            System.out.println("1. Pemesanan Menu");
            System.out.println("2. Pengelolaan Menu (khusus Pemilik Resto)");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1/2/3): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showPemesanan();
                    break;
                case "2":
                    showManajemenMenu();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silahkan input lagi.");
            }
        }
    }

    public static void showPemesanan() {
        System.out.println("== DAFTAR MENU SUNG RESTO ==");
        displayMenu();

        // Start taking orders
        String[] orderedItems = new String[menuList.length];
        int[] quantities = new int[menuList.length];
        takeOrder(orderedItems, quantities, 0);

        double totalCost = calculateTotalCost(orderedItems, quantities);
        printReceipt(orderedItems, quantities, totalCost);
    }

    public static void showManajemenMenu() {
        boolean running = true;

        while (running) {
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih menu (1/2/3/4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    tambahMenu();
                    break;
                case "2":
                    ubahHargaMenu();
                    break;
                case "3":
                    hapusMenu();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silahkan input lagi.");
            }
        }
    }

    public static void tambahMenu() {
        System.out.println("=== TAMBAH MENU ===");
        System.out.println("MENU YANG TERSEDIA SAAT INI");
        displayMenu();
        System.out.print("Masukkan nama menu: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga menu: ");
        double harga = scanner.nextDouble();
        scanner.nextLine(); // untuk menghilangkan enter yang tersisa di input
        System.out.print("Masukkan kategori menu (Makanan/Minuman): ");
        String kategori = scanner.nextLine();

        Menu[] newMenus = new Menu[menuList.length + 1];
        System.arraycopy(menuList, 0, newMenus, 0, menuList.length);
        newMenus[menuList.length] = new Menu(nama, harga, kategori);
        menuList = newMenus;

        System.out.println("Menu berhasil ditambahkan!");
    }

    public static void ubahHargaMenu() {
        System.out.println("=== UBAH HARGA MENU ===");
        System.out.println("MENU YANG TERSEDIA SAAT INI");
        displayMenu();
        System.out.print("Masukkan nama menu yang ingin diubah: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan harga baru: ");
        double harga = scanner.nextDouble();
        scanner.nextLine(); // untuk menghilangkan enter yang tersisa di input
        
        System.out.println("Anda yakin ingin mengubah harga menu ini? (Ya/Tidak)");
        String jawaban = scanner.nextLine();

        if (jawaban.equalsIgnoreCase("Ya")) {
            for (Menu menu : menuList) {
                if (menu.getNama().equalsIgnoreCase(nama)) {
                    menu.setHarga(harga);
                    System.out.println("Harga menu berhasil diubah!");
                    return;
                }
            }
            System.out.println("Menu tidak ditemukan.");
        } else {
            System.out.println("Harga menu tidak diubah!");
        }
    }

    public static void hapusMenu() {
        System.out.println("=== HAPUS MENU ===");
        System.out.println("MENU YANG TERSEDIA SAAT INI");
        displayMenu();
        System.out.print("Masukkan nama menu yang ingin dihapus: ");
        String nama = scanner.nextLine();

        System.out.println("Anda yakin ingin menghapus menu ini? (Ya/Tidak)");
        String jawaban = scanner.nextLine();

        if (jawaban.equalsIgnoreCase("Ya")) {
            Menu[] newMenus = new Menu[menuList.length - 1];
            int j = 0;
            for (Menu menu : menuList) {
                if (!menu.getNama().equalsIgnoreCase(nama)) {
                    newMenus[j] = menu;
                    j++;
                }
            }
            menuList = newMenus;
            System.out.println("Menu berhasil dihapus!");
        } else {
            System.out.println("Hapus menu dibatalkan.");
        }
    }

    public static double calculateTotalCost(String[] orderedItems, int[] quantities) {
        double totalCost = 0;
        for (int i = 0; i < orderedItems.length; i++) {
            for (Menu menu : menuList) {
                if (orderedItems[i] != null && orderedItems[i].equalsIgnoreCase(menu.getNama())) {
                    totalCost += menu.getHarga() * quantities[i];
                }
            }
        }
        return totalCost;
    }

    public static double applyDiscount(double totalCost) {
        if (totalCost > 100000) {
            return totalCost * 0.1; // DISKON 10%
        }
        return 0;
    }

    public static void printReceipt(String[] orderedItems, int[] quantities, double totalCost) {
        double serviceFee = 20000; // biaya pelayanan
        
        System.out.println("\n<< Struk Pesanan >>");
        for (int i = 0; i < orderedItems.length; i++) {
            if (orderedItems[i] != null) {
                for (Menu menu : menuList) {
                    if (menu.nama.equalsIgnoreCase(orderedItems[i])) {
                        System.out.println(menu.nama + " Rp " + format.format(menu.harga) + " x " + quantities[i] + " = Rp " + format.format(menu.harga * quantities[i]));
                    }
                }
            }
        }

        System.out.println("\nTotal Biaya Pesanan: Rp " + format.format(totalCost));

 double kopiLatteDiscount = 0;
        int kopiLatteCount = 0;
        for (int i = 0; i < orderedItems.length; i++) {
            if (orderedItems[i] != null && orderedItems[i].equalsIgnoreCase("Kopi Latte")) {
                kopiLatteCount += quantities[i];
            }
        }

        if (kopiLatteCount > 1) {
            kopiLatteDiscount = (kopiLatteCount / 2) * 25000; // Beli 1 Gratis 1
            System.out.println("Promo: Beli 1 Gratis 1 untuk Kopi Latte!");
            System.out.println("Potongan Promo Kopi Latte: Rp " + format.format(kopiLatteDiscount));
        }

        double generalDiscount = applyDiscount(totalCost);
        if (generalDiscount > 0) {
            System.out.println("Diskon 10%: Potongan sebesar Rp " + format.format(generalDiscount));
        }

        double subtotalAfterDiscounts = totalCost - generalDiscount - kopiLatteDiscount;
        if (totalCost > 100000) {
            System.out.println("Total Setelah Diskon: Rp " + format.format(subtotalAfterDiscounts));
        }

        double ppn = subtotalAfterDiscounts * 0.1;
        System.out.println("PPN (10%): Rp " + format.format(ppn));

        System.out.println("Biaya Pelayanan: Rp " + format.format(serviceFee));

        double grandTotal = subtotalAfterDiscounts + ppn + serviceFee;
        System.out.println("Total yang harus dibayar: Rp " + format.format(grandTotal));
    
        System.out.println("TERIMA KASIH TELAH BERKUNJUNG!");
    }

    public static void main(String[] args) {
        showMenuUtama();
    }

    private static void takeOrder(String[] orderedItems, int[] quantities, int itemCount) {
        System.out.print("Pesanan " + (itemCount + 1) + ": ");
        String itemNama = scanner.nextLine();

        if (itemNama.equalsIgnoreCase("Selesai")) {
            return;
        }

        boolean itemFound = false;
        for (Menu menu : menuList) {
            if (menu.nama.equalsIgnoreCase(itemNama)) {
                orderedItems[itemCount] = menu.nama;
                System.out.print("Jumlah: ");
                quantities[itemCount] = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                itemCount++;
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            System.out.println("Menu tidak ditemukan. Silakan coba lagi.");
        }

        takeOrder(orderedItems, quantities, itemCount);
    }
}