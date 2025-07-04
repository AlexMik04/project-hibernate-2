package entity;

import dto.RentalInfoDTO;
import jakarta.persistence.*;
import util.SqlQueries;

import java.time.LocalDateTime;
import java.util.*;

@NamedNativeQuery(
        name = "RentalInfoDTOMapping",
        query = SqlQueries.RENTAL_INFO_DTO_SQL,
        resultSetMapping = "RentalInfoDTOMapping"
)
@SqlResultSetMapping(
        name = "RentalInfoDTOMapping",
        classes = @ConstructorResult(
                targetClass = RentalInfoDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "customer_first_name", type = String.class),
                        @ColumnResult(name = "customer_last_name", type = String.class),
                        @ColumnResult(name = "film_title", type = String.class),
                        @ColumnResult(name = "return_date", type = LocalDateTime.class),
                        @ColumnResult(name = "staff_first_name", type = String.class),
                        @ColumnResult(name = "staff_last_name", type = String.class),
                        @ColumnResult(name = "city", type = String.class),
                        @ColumnResult(name = "country", type = String.class)
                }
        )
)
@Entity
@Table(schema = "movie", name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Integer id;

    @Column(name = "rental_date", nullable = false)
    private LocalDateTime rentalDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;



    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Payment> payments = new HashSet<>();



    public Rental() {
    }

    public Rental(Inventory inventory, Customer customer, Staff staff) {
        this.inventory = Objects.requireNonNull(inventory, "Inventory can not be null");
        this.customer = Objects.requireNonNull(customer, "Customer can not be null");
        this.staff = Objects.requireNonNull(staff, "Staff can not be null");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void addPayment(Payment payment) {
        Objects.requireNonNull(payment, "Payment can not be null");

        payments.add(payment);
    }
}
