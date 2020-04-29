package tech.housemoran.realgood.application.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.housemoran.realgood.application.datalayer.api.ReceiptService;
import tech.housemoran.realgood.application.models.Receipt;
import tech.housemoran.realgood.application.models.enums.ReceiptType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/receipt")
public class Receipts {
    private static final Logger log = LoggerFactory.getLogger(Receipts.class);

    @Autowired
    private ReceiptService repo;

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceipt(@PathVariable("id") long id) {
        final Optional<Receipt> receipt = repo.getReceipt(id);
        return receipt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{month}/{year}/{type}")
    public ResponseEntity<List<Receipt>> getReceiptsInMonthByType(@PathVariable("month") String month, @PathVariable("year") String year, @PathVariable("type") String typeString) {
        final SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            final Date date = dateParser.parse( "01-" + month + "-" + year + " 23:59:59");
            final ReceiptType type = ReceiptType.toValue(typeString);
            if (type == null) {
                log.warn(String.format("Type %s is not a valid type", typeString));
                return ResponseEntity.badRequest().build();
            }
            final List<Receipt> receipts = repo
                    .getReceiptsByDate(date)
                    .stream()
                    .filter(receipt -> receipt.getType().equalsIgnoreCase(type.toString()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(receipts);
        } catch (ParseException e) {
            log.warn(String.format("Date sent %s-%s is not a valid date for this resource.", month, year));
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{month}/{year}")
    public ResponseEntity<List<Receipt>> getReceiptsInMonth(@PathVariable("month") String month, @PathVariable("year") String year) {
        final SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            final Date date = dateParser.parse( "01-" + month + "-" + year + " 23:59:59");
            final List<Receipt> receipts = repo.getReceiptsByDate(date);
            return ResponseEntity.ok(receipts);
        } catch (ParseException e) {
            log.warn(String.format("Date sent %s-%s is not a valid date for this resource.", month, year));
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{month}/{day}/{year}")
    public ResponseEntity<Receipt> createReceipt(@PathVariable("day") String day, @PathVariable("month") String month, @PathVariable("year") String year, @RequestParam(value = "company") String company, @RequestParam(value = "type") String typeString, @RequestParam(value="description") String description, @RequestParam(value = "amount") float amount) {
        final SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            final Receipt receipt = new Receipt();
            log.info(day);
            final Date date = dateParser.parse(day + "-" + month + "-" + year + " 23:59:59");
            log.info(date.toString());
            final ReceiptType type = ReceiptType.toValue(typeString);
            if (type == null) {
                log.warn(String.format("Type %s is not a valid type", typeString));
                return ResponseEntity.badRequest().build();
            }
            receipt.setType(type.toString());
            receipt.setDescription(description);
            receipt.setCompany(company);
            receipt.setAmount(amount);
            receipt.setDate(date);
            repo.saveReceipt(receipt);
            log.info("Created the receipt!");
            return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
        } catch (ParseException e) {
            log.warn(String.format("Date sent %s-%s-%s is not a valid date for this resource.", month, day, year));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
   }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReceipt(@PathVariable("id") long id) {
        final Optional<Receipt> receipt = repo.getReceipt(id);
        if (receipt.isPresent()) {
            log.info("Deleting receipt with id: " + id);
            repo.deleteReceipt(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
        } else {
            log.warn("Receipt was not found for id: " + id);
            return ResponseEntity.badRequest().build();
        }
    }
}
