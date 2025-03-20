import akraMikir_Crack.backend.dtos.ProdukResponse;
import akraMikir_Crack.backend.dtos.ProdukRequest;
import akraMikir_Crack.backend.exceptions.ProductValidationException;
import akraMikir_Crack.backend.models.MasterProduk;

import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@Transactional

public ProdukResponse tambahProduk (ProdukRequest data) {

// Validasi input data sebelum menyimpan ke database

    if (data == null) {
        throw new ProdukValidationException("Data produk tidak boleh null");
    }

    if (data.getNamaProduk() == null || data.getNamaProduk().isEmpty()) {
        throw new ProdukValidationException("Nama produk tidak boleh kosong");
    }

    if (data.getHarga() == null || data.getHarga().compareTo(BigDecimal.ZERO) <= 0) {
        throw new ProdukValidationException("Harga produk harus lebih dari 0");
    }

    if (data.getKategori() == null || data.getKategori().isEmpty()) {
        throw new ProdukValidationException("Kategori produk tidak boleh kosong");
    }

    if (data.getStok() == null || data.getStok() < 0) {
        throw new ProdukValidationException("Stok produk harus 0 atau lebih");
    }

    MasterProduk prosesProduk = MasterProdukbuilder()
            .namaProduk(data.getNamaProduk())
            .harga(data.getHarga())
            .kategori(data.getKategori())
            .stok(data.getStok())
            .build();

    MasterProduk save = produkRepository.save(prosesProduk);
    return ProdukResponse.builder()
            .id(save.getId())
            .namaProduk(save.getNamaProduk())
            .harga(save.getHarga())
            .kategori(save.getKategori())
            .stok(save.getStok())
            .build();
}

