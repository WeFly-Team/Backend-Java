package com.example.wefly_app.repository;

import com.example.wefly_app.entity.Airport;
import com.example.wefly_app.entity.ETicket;
import com.example.wefly_app.request.checkin.ETicketDTO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ETicketRepository extends PagingAndSortingRepository<ETicket, Long>, JpaSpecificationExecutor<ETicket> {
    @Query(value = "select et from ETicket et WHERE et.bookCode = :bookCode")
    ETicket getETicketByBookCode (@Param("bookCode") String bookCode);

    @Query("SELECT new com.example.wefly_app.request.checkin.ETicketDTO(" +
            "et.bookCode, fs.departureDate, fs.arrivalDate, f.flightCode, fc.seatClass, al.name, " +
            "dep.name, dep.iata, dep.city, dep.province, arr.name, arr.iata, arr.city, arr.province, " +
            "f.departureTime, f.arrivalTime) " +
            "FROM ETicket et " +
            "JOIN et.transaction t " +
            "JOIN et.transactionDetail td " +
            "JOIN td.flightClass fc " +
            "JOIN fc.flightSchedule fs " +
            "JOIN fs.flight f " +
            "JOIN f.airline al " +
            "JOIN f.departureAirport dep " +
            "JOIN f.arrivalAirport arr " +
            "WHERE t.id =:transactionId")
    ETicketDTO findFlightDetailsByTransactionId(@Param("transactionId") Long transactionId);
}
