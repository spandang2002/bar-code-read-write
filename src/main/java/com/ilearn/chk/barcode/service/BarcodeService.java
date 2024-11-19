package com.ilearn.chk.barcode.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class BarcodeService {

    public BufferedImage generateQRCodeWithText(String barcodeText, String overlayText) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

        // Convert BitMatrix to Image
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Draw overlay text
        Graphics2D graphics = qrImage.createGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 20));

        // Calculate the position for the text
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(overlayText);
        int textHeight = fontMetrics.getHeight();

        // Center the text
        int centerX = (qrImage.getWidth() - textWidth) / 2;
        int centerY = (qrImage.getHeight() / 2) + (textHeight / 4); // adjust for font ascent

        // Add a white background rectangle for better visibility
        graphics.setColor(Color.WHITE);
        graphics.fillRect(centerX - 5, centerY - textHeight + 5, textWidth + 10, textHeight);

        // Draw the text on top of the white rectangle
        graphics.setColor(Color.BLACK);
        graphics.drawString(overlayText, centerX, centerY);
        graphics.dispose();

        return qrImage;
    }

    public String decodeBarcode(MultipartFile file) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode barcode", e);
        }
    }
}


