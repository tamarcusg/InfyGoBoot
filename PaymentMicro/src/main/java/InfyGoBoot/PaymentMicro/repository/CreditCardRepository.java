package InfyGoBoot.PaymentMicro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import InfyGoBoot.PaymentMicro.entity.CreditCardDetails;

public interface CreditCardRepository extends JpaRepository<CreditCardDetails, String> {

}
