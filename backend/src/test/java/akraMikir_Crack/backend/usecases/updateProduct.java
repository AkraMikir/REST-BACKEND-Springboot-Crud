
import akraMikir_Crack.backend.dtos.ProdukRequest;
import akraMikir_Crack.backend.dtos.ProdukResponse;
import akraMikir_Crack.backend.repositories.ProdukRepository;
import akraMikir_Crack.backend.exceptions.ProductNotFoundException;
import akraMikir_Crack.backend.exceptions.ProductValidationException;
import akraMikir_Crack.backend.models.MasterProduk;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

@Transactional

public ProdukResponse updateProduk (Long id, ProdukRequest data) {

// Mencari produk yang akan diupdate berdasarkan id
    Optional <MasterProduk> optionalProduk = ProdukRepository.findById(id);

    if (optionalProduk.isEmpty()) {

        throw new ProductNotFoundException("Produk dengan id" + id + "id tidak di temukan");

    }

    MasterProduk produk = optionalProduk.get();

// Validasi input data sebelum melakukan update

    if (data==null) {

        throw new ProductValidationException("Data produk tidak boleh null");

    }

    if (data.getNamaProduk() == null || data.getNamaProduk().isEmpty()) {

        throw new ProductValidationException("Nama produk tidak boleh kosong");

    }

    if (data.getHarga() == null || data.getHarga().compareTo(BigDecimal.ZERO) <= 0) {

        throw new ProductValidationException("Harga produk harus lebih dari 0");

        if (data.getKategori() == null || data.getKategori().isEmpty()) {

            throw new ProductValidationException("Kategori produk tidak boleh kosong");

        }

        if (data.getStok() == null || data.getStok() < 0) {

            throw new ProductValidationException("Stok produk harus 8 atau lebih");

        }

// Update data produk
        produk.setNamaProduk(data.getNamaProduk());
        produk.setHarga(data.getHarga());
        produk.setKategori(data.getKategori());
        produk.setStok(data.getStok());
        MasterProduk updatedProduk = (MasterProduk) ProdukRepository.save(produk);

        return ProdukResponse.builder()
                .id(updatedProduk.getId())
                .namaProduk(updatedProduk.getNamaProduk())
                .harga(updatedProduk.getHarga()).build()
                .kategori(updatedProduk.getKategori())
                .stok(updatedProduk.getStok())
                .build();
    }
    return null;
}

