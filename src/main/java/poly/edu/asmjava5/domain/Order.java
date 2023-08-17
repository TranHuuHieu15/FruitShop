package poly.edu.asmjava5.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

//    @Column(nullable = false)
//    private int customerId;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private short status;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Account username;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails;

    public void setStatus(short status) {
        this.status = status;
    }
}
