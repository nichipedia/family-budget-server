package tech.housemoran.realgood.application.datalayer.api;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tech.housemoran.realgood.application.models.Receipt;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends CrudRepository<Receipt, Long> {
    Optional<Receipt> findById(long id);

    @Query("SELECT e FROM Receipt e WHERE (e.amount <= :amount)")
    List<Receipt> queryByUnderAmount(@Param("amount") float amount);

    @Query(value = "SELECT e FROM Receipt e WHERE MONTH(date) = MONTH(:date) AND YEAR(date) = YEAR(:date)")
    public List<Receipt> queryForRecieptsInMonth(@Param("date") Date month);
}
