package uom.ics22116.atmservice.transaction;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<String> register(
            @Valid @RequestBody TransactionRequest transactionRequest
    ) {
        transactionService.createTransaction(transactionRequest);

        return ResponseEntity.ok("Transaction successful");
    }

}
