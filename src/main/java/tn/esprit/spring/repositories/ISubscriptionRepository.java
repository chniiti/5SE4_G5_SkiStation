package tn.esprit.spring.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.query.Param;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ISubscriptionRepository extends CrudRepository<Subscription, Long> {

    @Query("select s from Subscription s where s.typeSub = :typeS order by s.startDate")
    Set<Subscription> findByTypeSubOrderByStartDateAsc(@Param("typeS") TypeSubscription typeSub);

    List<Subscription> getSubscriptionsByStartDateBetween(LocalDate date1, LocalDate date2);

    @Query("select distinct s from Subscription s where s.endDate <= CURRENT_TIME order by s.endDate")
    List<Subscription> findDistinctOrderByEndDateAsc();


    @Query("SELECT COALESCE(SUM(s.price)/COUNT(s.numSub), 0.0) FROM Subscription s WHERE s.typeSub = :typeSub")
    Float recurringRevenueByTypeSubEquals(@Param("typeSub") TypeSubscription typeSub);



}
