package bootcamp.bankApi.models;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Column(name = "account_number")
    private String accountNumber;

    @NonNull
    @Column(name = "balance")
    private float balance;

    @NonNull
    @Column(name = "customer_id")
    private int customerId;

}
