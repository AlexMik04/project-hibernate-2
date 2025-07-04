package entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(schema = "movie", name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Short id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_staff_id", nullable = false, referencedColumnName = "staff_id", unique = true)
    private Staff manager;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;



    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Staff> staff = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inventory> inventories = new HashSet<>();

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Customer> customers = new HashSet<>();



    public Store() {
    }

    public Store(Staff manager, Address address) {
        this.manager = manager;
        this.address = Objects.requireNonNull(address, "Address can not be null");
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Staff getManager() {
        return manager;
    }

    public void setManager(Staff manager) {
        this.manager = manager;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Set<Staff> staff) {
        this.staff = staff;
    }

    public void addStaff(Staff s) {
        Objects.requireNonNull(s, "Staff can not be null");

        staff.add(s);
    }

    public Set<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(Set<Inventory> inventories) {
        this.inventories = inventories;
    }

    public void addInventory(Inventory inventory) {
        Objects.requireNonNull(inventory, "Inventory can not be null");

        inventories.add(inventory);
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        Objects.requireNonNull(customer, "Customer can not be null");

        customers.add(customer);
    }
}
