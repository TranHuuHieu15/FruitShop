package poly.edu.asmjava5.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
public class HistoryOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long HistoryOrderId;
    private String imame;
    private String productName;
    private String username;
    private int quantity;
    private double total;
    private short status;

    public HistoryOrder(String imame,String productName, String username, int quantity, double total, short status) {
        this.imame = imame;
        this.productName = productName;
        this.username = username;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
    }
}
