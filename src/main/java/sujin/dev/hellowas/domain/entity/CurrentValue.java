package sujin.dev.hellowas.domain.entity;

import jakarta.persistence.Embeddable;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class CurrentValue {

   private BigDecimal amount;
   private Currency currency;
}
