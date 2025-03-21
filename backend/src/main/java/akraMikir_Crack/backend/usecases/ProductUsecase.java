package akraMikir_Crack.backend.usecases;

import akraMikir_Crack.backend.dtos.ProdukRequest;
import akraMikir_Crack.backend.dtos.ProdukResponse;
import akraMikir_Crack.backend.exceptions.ProductNotFoundException;
import akraMikir_Crack.backend.exceptions.ProductValidationException;
import akraMikir_Crack.backend.models.MasterProduk;
import akraMikir_Crack.backend.repositories.ProdukRepository;
import akraMikir_Crack.backend.services.ProdukService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public abstract class ProductUsecase implements ProdukService {
    private ProdukRepository produkRepository;

    @Transactional
    public ProdukResponse tambahProduk(ProdukRequest data) {
        if (data == null) {
            throw new ProductValidationException("Data produk tidak boleh null");
        }
        if (data.getNamaProduk() == null || data.getNamaProduk().isEmpty()) {
            throw new ProductValidationException("Data produk tidak boleh kosong");
        }
        if (data.getHarga() == null || data.getHarga().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException("Harga produk harus lebih dari 0");
        }
        if (data.getKategori() == null || data.getKategori().isEmpty()) {
            throw new ProductValidationException("Kategori produk tidak boleh kosong");
        }
        if (data.getStok() == null || data.getStok() < 0) {
            throw new ProductValidationException("Stok produk harus 0 atau lebih");
        }

        MasterProduk prosesProduk = MasterProduk.builder()
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


    @Transactional
    public ProdukResponse updateProduk(long id, ProdukRequest data) {
        Optional<MasterProduk> optionalProduk = produkRepository.findById(id);
        if (optionalProduk.isEmpty()) {
            throw new ProductNotFoundException("Produk dengan id" + id + "Tidak ditemukan");
        }
        MasterProduk produk = optionalProduk.get();
        if (data == null) {
            throw new ProductValidationException("Data produk tidak boleh null");
        }
        if (data.getNamaProduk() == null || data.getNamaProduk().isEmpty()) {
            throw new ProductValidationException("Nama produk tidak boleh kosong");
        }
        if (data.getHarga() == null || data.getHarga().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException("Harga produk harus lebih dari 0");
        }
        if (data.getKategori() == null || data.getKategori().isEmpty()) {
            throw new ProductValidationException("Kategori produk tidak boleh kosong");
        }
        if (data.getStok() == null || data.getStok() < 0) {
            throw new ProductValidationException("Stok produk harus 0 atau lebih");
        }
        produk.setNamaProduk(data.getNamaProduk());
        produk.setHarga(data.getHarga());
        produk.setKategori(data.getKategori());
        produk.setStok(data.getStok());
        MasterProduk updatedProduk = produkRepository.save(produk);
        return ProdukResponse.builder()
                .id(updatedProduk.getId())
                .namaProduk(updatedProduk.getNamaProduk())
                .harga(updatedProduk.getHarga())
                .kategori(updatedProduk.getKategori())
                .stok(updatedProduk.getStok())
                .build();

    }

    public List<ProdukResponse> ambilSemuaData(){
        List<MasterProduk> daftarProduk = produkRepository.findAll();
        List<ProdukResponse> daftarResponsProduk = new ArrayList<>();

        for(MasterProduk produk : daftarProduk){
            ProdukResponse responsProduk = ProdukResponse.builder()
                    .id(produk.getId())
                    .namaProduk(produk.getNamaProduk())
                    .harga(produk.getHarga())
                    .kategori(produk.getKategori())
                    .stok(produk.getStok())
                    .build();
            daftarResponsProduk.add(responsProduk);
        }
        return daftarResponsProduk;
    }
    @Transactional
    public void deleteProduk(long id){
        if(!produkRepository.existsById(id)){
            throw new ProductNotFoundException("Produk dengan ID" + id + "Tidak ditemukan");
        }
        produkRepository.deleteById(id);
    }

}