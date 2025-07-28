package com.gustavosdaniel.tickets.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gustavosdaniel.tickets.ticket.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_CODE_WIDTH = 300;
    private static final int QR_CODE_HEIGHT = 300;

    private final QrCodeRepository qrCodeRepository;
    private final QRCodeWriter qrCodeWriter;

    @Override
    public QrCode generateQrCode(Ticket ticket) {
        try {

            UUID uniqueId =  UUID.randomUUID();

            String qrCodeImage =  generateQrCodeImage(uniqueId);

            QrCode qrCode = new QrCode();
            qrCode.setId(uniqueId);
            qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
            qrCode.setValue(qrCodeImage);
            qrCode.setTicket(ticket);

           return qrCodeRepository.saveAndFlush(qrCode); // Útil quando você precisa do ID gerado ou garantir que a operação foi completada

        } catch (IOException | WriterException  exception) {
            throw new QrCodeGenerationException("Failed to generate QR code", exception);
        }

    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {

        QrCode qrCode = qrCodeRepository
                .findByTicketIdAndTicketPurchaserId(userId, ticketId)
                .orElseThrow(QrCodeNotFoundException::new);

        try {
            return Base64.getDecoder().decode(qrCode.getValue());
        }catch (IllegalArgumentException ex) {

            log.error("Invalid base64 QR code for ticket ID {}", ticketId, ex);
            throw new QrCodeGenerationException();
        }
    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {

       BitMatrix bitMatrix = qrCodeWriter.encode(
               uniqueId.toString(),
               BarcodeFormat.QR_CODE,
               QR_CODE_WIDTH,
               QR_CODE_HEIGHT
       );

        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            ImageIO.write(qrCodeImage, "png", outputStream);

            byte[] imageBytes = outputStream.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }

    }
}
