package tech.housemoran.realgood.application.datalayer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.housemoran.realgood.application.models.Receipt;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ReceiptService {

    @Autowired
    private ReceiptRepository repo;

    @Transactional
    public Receipt saveReceipt(final Receipt receipt) {
        return repo.save(receipt);
    }

    public List<Receipt> getReceiptsByDate(final Date date) {
        return repo.queryForRecieptsInMonth(date);
    }

    public Optional<Receipt> getReceipt(final long id) {
        return repo.findById(id);
    }

    public void deleteReceipt(final long id) {
        repo.deleteById(id);
    }
}
