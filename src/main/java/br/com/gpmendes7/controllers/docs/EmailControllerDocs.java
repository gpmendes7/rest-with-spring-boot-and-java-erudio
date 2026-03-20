package br.com.gpmendes7.controllers.docs;

import br.com.gpmendes7.data.dto.request.EmailRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface EmailControllerDocs {

    @Operation(summary = "Send an e_Mail",
            description = "Send an e_mail by providing details, subject and body",
            tags = {"e_Mail"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<String> sendEmail(EmailRequestDTO emailRequestDTO);

    @Operation(summary = "Send an e_Mail with Attachment",
            description = "Send an e_mail with Attachment by providing details, subject and body",
            tags = {"e_Mail"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<String> sendEmailWithAttachment(String emailRequestJson, MultipartFile multipartFile);

}
