package akraMikir_Crack.backend.controllers;

import akraMikir_Crack.backend.dtos.ProdukRequest;
import akraMikir_Crack.backend.dtos.ProdukResponse;
import akraMikir_Crack.backend.services.ProdukService;
import akraMikir_Crack.backend.utils.ResponseStatus;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/produk")
@AllArgsConstructor
@Slf4j

public class ProductController {
    private ProdukService produkService;

    @PostMapping(value = "/post")
    public ResponseEntity<ResponseStatus<ProdukResponse>> tambahProduk(@Valid @RequestBody ProdukRequest request, Errors errors)
    {
        ResponseStatus<ProdukResponse> responseData = new ResponseStatus<>();
        if (errors.hasErrors()) {
            for (ObjectError error:errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setStatusCode(HttpStatus.OK);
        responseData.setData(produkService.tambahProduk(request));
        log.info("SUCCESSFULLY INSERT - PRODUK WITH DATA: " + request);
        return ResponseEntity.ok(responseData);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ResponseStatus<ProdukResponse>> updateProduk(@Valid @PathVariable(value ="id") Long id, @RequestBody ProdukRequest request, Errors errors){

        ResponseStatus<ProdukResponse> responseData = new ResponseStatus<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setStatusCode(HttpStatus.OK);
        responseData.setData(produkService.updateProduk(id, request));
        responseData.getMessages().add("Update Product Dengan ID:" + id + "| Berhasil di update.");
        log.info("SUCCESSFULLY INSERT - PRODUK WITH DATA: " + request);
        return ResponseEntity.ok(responseData);
    }

    @GetMapping(value = "/index")
    public ResponseEntity<ResponseStatus<List<ProdukResponse>>> ambilSemuaData(){
        List<ProdukResponse> produkResponses = produkService.ambilSemuaData();
        ResponseStatus<List<ProdukResponse>> responseData = new ResponseStatus<>();
        if (produkResponses.isEmpty()){
            responseData.setStatus(true);
            responseData.setStatusCode(HttpStatus.OK);
            responseData.getMessages().add("Data tidak ditemukan");
            responseData.setData(Collections.emptyList());
        } else {
            responseData.setStatus(true);
            responseData.setStatusCode(HttpStatus.OK);
            responseData.getMessages().add("Data ditemukan, menampilkan data dari database");
            responseData.setData(produkResponses);
        }
        log.info("SUCCESFULLY GET ALL DATA - ADMINISTRATOR");
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteData(@PathVariable(value = "id") Long id){
        produkService.deleteProduk(id);
        String message = "Produk dengan ID: " + id + " | Berhasil di hapus.";
        log.info(message);

    }
}