package akraMikir_Crack.backend.services;

import akraMikir_Crack.backend.dtos.ProdukRequest;
import akraMikir_Crack.backend.dtos.ProdukResponse;

import java.util.List;

public interface ProdukService {

    // method untuk tambah produk
    ProdukResponse tambahProduk(ProdukRequest data);

    // method untuk update produk
    ProdukResponse updateProduk(Long id, ProdukRequest data);

    // method untuk Ambil Semua Data produk
    List<ProdukResponse> ambilSemuaData();

    // method untuk delete produk
    void deleteProduk(Long id);
}
