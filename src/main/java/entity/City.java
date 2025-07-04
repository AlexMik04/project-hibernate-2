package entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(schema = "movie", name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Integer id;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;



    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();



    public City() {
    }

    public City(String city, Country country) {
        this.city = Objects.requireNonNull(city, "City can not be null");
        this.country = Objects.requireNonNull(country, "Country can not be null");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        Objects.requireNonNull(address, "Address can not be null");

        addresses.add(address);
    }
}
