package com.ilearn.chk.barcode.controller;


import com.google.zxing.WriterException;
import com.ilearn.chk.barcode.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/barcode")
public class BarcodeController {

    @Autowired
    private BarcodeService barcodeService;

    @PostMapping("/decode")
    public ResponseEntity<String> decodeBarcode(@RequestParam("file") MultipartFile file) {
        try {
            String decodedText = barcodeService.decodeBarcode(file);
            return ResponseEntity.ok(decodedText);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to decode barcode: " + e.getMessage());
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateBarcode(@RequestParam String text) {
        try {
            BufferedImage barcodeImage = barcodeService.generateQRCodeWithText(text, "spandan");

            // Write the image to a local file
            File outputfile = new File("generated_barcode.png");
            ImageIO.write(barcodeImage, "png", outputfile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(barcodeImage, "png", baos);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=barcode.png");

            return ResponseEntity.ok().headers(headers).body(baos.toByteArray());
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}



