package akraMikir_Crack.backend.usecases;

import akraMikir_Crack.backend.dtos.ProdukRequest;
import akraMikir_Crack.backend.dtos.ProdukResponse;
import akraMikir_Crack.backend.repositories.ProdukRepository;
import akraMikir_Crack.backend.services.ProdukService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductUsecase implements ProdukService {
    private ProdukRepository ProdukRepository;

    @Override
    public ProdukResponse tambahProduk(ProdukRequest data) {
        return null;
    }

    @Override
    public ProdukResponse updateProduk(Long id, ProdukRequest data) {
        return null;
    }

    @Override
    public List<ProdukResponse> ambilSemuaData() {
        return List.of();
    }

    @Override
    public void deleteProduk(Long id) {

    }
}
