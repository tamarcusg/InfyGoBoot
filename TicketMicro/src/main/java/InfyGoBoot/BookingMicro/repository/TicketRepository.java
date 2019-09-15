package InfyGoBoot.BookingMicro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import InfyGoBoot.BookingMicro.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Double> {

}
