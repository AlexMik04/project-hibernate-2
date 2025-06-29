package entity.film;

import dto.FilmInfoDTO;
import entity.Inventory;
import entity.Language;
import entity.film.converter.RatingConverter;
import entity.film.converter.SpecialFeaturesConverter;
import entity.film.converter.YearConverter;
import entity.film_actor.FilmActor;
import entity.film_category.FilmCategory;
import jakarta.persistence.*;
import util.SqlQueries;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

@NamedNativeQuery(
        name = "FilmInfoDTOMapping",
        query = SqlQueries.FILM_INFO_DTO_SQL,
        resultSetMapping = "RentalInfoDTOMapping"
)
@SqlResultSetMapping(
        name = "FilmInfoDTOMapping",
        classes = @ConstructorResult(
                targetClass = FilmInfoDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "release_year", type = Year.class),
                        @ColumnResult(name = "description", type = String.class),
                        @ColumnResult(name = "rating", type = Rating.class),
                        @ColumnResult(name = "language", type = Language.class),
                        @ColumnResult(name = "category", type = String.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                }
        )
)
@Entity
@Table(schema = "movie", name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Integer id;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Convert(converter = YearConverter.class)
    @Column(name = "release_year", columnDefinition = "YEAR")
    private Year releaseYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @Column(name = "rental_duration", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Short rentalDuration = 3;

    @Column(name = "rental_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal rentalRate = BigDecimal.valueOf(4.99);

    @Column(name = "length", columnDefinition = "SMALLINT UNSIGNED")
    private Integer length;

    @Column(name = "replacement_cost", nullable = false, precision = 5, scale = 2)
    private BigDecimal replacementCost = BigDecimal.valueOf(19.99);

    @Convert(converter = RatingConverter.class)
    @Column(name = "rating")
    private Rating rating = Rating.G;

    @Convert(converter = SpecialFeaturesConverter.class)
    @Column(name = "special_features")
    private Set<String> specialFeatureSet = new HashSet<>();

    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;



    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilmCategory> filmCategories = new HashSet<>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilmActor> filmActors = new HashSet<>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inventory> inventories = new HashSet<>();



    public Film() {
    }

    public Film(String title, Language language) {
        this.title = Objects.requireNonNull(title, "Title can not be null");
        this.language = Objects.requireNonNull(language, "Language can not be null");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(Language originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Short getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Short rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Set<String> getSpecialFeatureSet() {
        return specialFeatureSet;
    }

    public void setSpecialFeatureSet(Set<String> specialFeatureSet) {
        this.specialFeatureSet = specialFeatureSet;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<FilmCategory> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(Set<FilmCategory> filmCategories) {
        this.filmCategories = filmCategories;
    }

    public void addFilmCategory(FilmCategory filmCategory) {
        Objects.requireNonNull(filmCategory, "FilmCategory can not be null");

        filmCategories.add(filmCategory);
    }

    public Set<FilmActor> getFilmActors() {
        return filmActors;
    }

    public void setFilmActors(Set<FilmActor> filmActors) {
        this.filmActors = filmActors;
    }

    public void addFilmActor(FilmActor filmActor) {
        Objects.requireNonNull(filmActor, "FilmActor can not be null");

        filmActors.add(filmActor);
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
}
