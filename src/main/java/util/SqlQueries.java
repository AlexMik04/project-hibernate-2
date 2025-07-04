package util;

public final class SqlQueries {
    private SqlQueries() {}

    public static final String RENTAL_INFO_DTO_SQL =
            "SELECT r.rental_id AS id, " +
                    "c.first_name AS customer_first_name, " +
                    "c.last_name AS customer_last_name, " +
                    "f.title AS film_title, " +
                    "r.return_date AS return_date, " +
                    "s.first_name AS staff_first_name, " +
                    "s.last_name AS staff_last_name, " +
                    "c2.city AS city, " +
                    "c3.country AS country " +

                    "FROM rental r " +

                    "LEFT JOIN movie.customer c ON c.customer_id = r.customer_id " +
                    "LEFT JOIN movie.payment p ON r.rental_id = p.rental_id " +
                    "LEFT JOIN movie.staff s ON s.staff_id = p.staff_id " +
                    "LEFT JOIN movie.inventory i ON i.inventory_id = r.inventory_id " +
                    "LEFT JOIN movie.film f ON f.film_id = i.film_id " +
                    "LEFT JOIN movie.store s2 ON s.staff_id = s2.manager_staff_id " +
                    "LEFT JOIN movie.address a ON a.address_id = s2.address_id " +
                    "LEFT JOIN movie.city c2 ON c2.city_id = a.city_id " +
                    "LEFT JOIN movie.country c3 ON c3.country_id = c2.country_id " +

                    "ORDER BY r.last_update DESC";


    public static final String FILM_INFO_DTO_SQL =
            "SELECT f.film_id AS id, " +
                    "f.title AS title, " +
                    "f.release_year AS release_year, " +
                    "f.description AS description, " +
                    "f.rating AS rating, " +
                    "l.name AS language, " +
                    "c.name AS category, " +
                    "a.first_name AS first_name, " +
                    "a.last_name AS last_name " +

                    "FROM film f " +

                    "LEFT JOIN movie.inventory i ON f.film_id = i.film_id " +
                    "LEFT JOIN movie.film_actor fa ON f.film_id = fa.film_id " +
                    "LEFT JOIN movie.actor a ON a.actor_id = fa.actor_id " +
                    "LEFT JOIN movie.film_category fc ON f.film_id = fc.film_id " +
                    "LEFT JOIN movie.category c ON c.category_id = fc.category_id " +
                    "LEFT JOIN movie.language l ON l.language_id = f.language_id " +

                    "ORDER BY f.last_update DESC";

    public static final String CUSTOMER_INFO_DTO_SQL =
            "SELECT c.customer_id AS id, " +
                    "c.first_name AS first_name, " +
                    "c.last_name AS last_name, " +
                    "c.email AS email, " +
                    "c.active AS active, " +
                    "c2.city AS city, " +
                    "c3.country AS country " +

                    "FROM customer c " +

                    "LEFT JOIN movie.address a ON a.address_id = c.address_id " +
                    "LEFT JOIN movie.city c2 ON c2.city_id = a.city_id " +
                    "LEFT JOIN movie.country c3 ON c3.country_id = c2.country_id " +

                    "ORDER BY c.last_update DESC";

}
