package entity;

import java.awt.*;
import java.util.*;

public class Perabotan extends Objek implements BisaDibeli {
    private Point kiriAtas;

    public void setDimensi(Dimension dimensi) {
        this.dimensi = dimensi;
    }

    private Dimension dimensi;
    private int harga;
    private boolean dipakai;

    public Perabotan(int id) {
        super(id);
        if (id == 5) {
            dimensi = new Dimension(4, 1);
            harga = 50;
        } else if (id == 6) {
            dimensi = new Dimension(4, 2);
            harga = 100;
        } else if (id == 7) {
            dimensi = new Dimension(5, 2);
            harga = 150;
        } else if (id == 4) {
            dimensi = new Dimension(1, 1);
            harga = 50;
        } else if (id == 2) {
            dimensi = new Dimension(2, 1);
            harga = 100;
        } else if (id == 3) {
            dimensi = new Dimension(1, 1);
            harga = 200;
        } else if (id == 1) {
            dimensi = new Dimension(3, 3);
            harga = 50;
        } else if (id == 10) {
            dimensi = new Dimension(1, 1);
            harga = 10;
        } else if (id == 8) {
            dimensi = new Dimension(3, 2);
            harga = 70;
        } else if (id == 9) {
            dimensi = new Dimension(2, 1);
            harga = 90;
        }
        kiriAtas = null;
        dipakai = false;
    }

    public Point getKiriAtas() {
        return kiriAtas;
    }

    public Dimension getDimensi() {
        return dimensi;
    }

    public int getHarga() {
        return harga;
    }

    public boolean getDipakai() {
        return dipakai;
    }

    public void setKiriAtas(Point p) {
        kiriAtas = p;
    }

    public void setDipakai(boolean dipakai) {
        this.dipakai = dipakai;
    }

    public void rotate() {
        dimensi.setSize(dimensi.getHeight(), dimensi.getWidth());
    }

    @Override
    public void beli(Sim sim) {
        System.out.println("Masukkan kuantitas " + super.getNama() + " yang ingin dibeli.");
        boolean valid = false;
        Scanner sc = new Scanner(System.in);
        while (!valid) {
            try {
                int kuantitas = Integer.parseInt(sc.nextLine());
                if (kuantitas <= 0) {
                    throw new IllegalArgumentException("Kuantitas harus lebih besar dari 0.");
                }
                valid = true;
                int totalHarga = kuantitas * getHarga();
                if (sim.getUang() < totalHarga) {
                    System.out.println("Uang tidak cukup untuk membeli " + String.valueOf(kuantitas) + " " + super.getNama() + ".");
                } else {
                    sim.setUang(sim.getUang() - totalHarga);
                    sim.getInventory().addItem(this);
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        sc.close();
    }
}