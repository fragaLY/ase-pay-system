package by.vk.ase.pay.ledger.api;

import by.vk.ase.pay.ledger.api.payload.LedgerResponse;
import by.vk.ase.pay.ledger.api.payload.TransactionPayload;
import by.vk.ase.pay.ledger.repository.Status;
import by.vk.ase.pay.ledger.service.LedgerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
public record LedgerApi(LedgerService service) {

  @PostMapping
  public ResponseEntity<LedgerResponse> transfer(@Valid @RequestBody TransactionPayload payload) {
    var response = service.transfer(payload);
    var status =
        response.status().equals(Status.COMPLETED) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    return ResponseEntity.status(status).body(response);
  }

}
