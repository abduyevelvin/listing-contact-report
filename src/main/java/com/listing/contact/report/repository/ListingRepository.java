package com.listing.contact.report.repository;

import com.listing.contact.report.dto.SellerTypeAverageDTO;
import com.listing.contact.report.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    @Query("SELECT new com.listing.contact.report.dto.SellerTypeAverageDTO(l.sellerType, avg(l.price)) " +
            "FROM Listing AS l GROUP BY l.sellerType")
    List<SellerTypeAverageDTO> getAvgOfSellerType();

    @Query(value = "SELECT top 5 l.id, l.make, l.price, l.mileage, COUNT(l.id) \n" +
            "FROM listing l JOIN contact c \n" +
            "ON l.id=c.listing_id \n" +
            "WHERE MONTH(dateadd(s, c.contact_date/1000, '1970-01-01'))=?1 \n" +
            "AND YEAR(dateadd(s, c.contact_date/1000, '1970-01-01'))=?2 \n" +
            "GROUP BY l.id \n" +
            "ORDER BY COUNT(l.id) DESC", nativeQuery = true)
    List<Object[]> getTopContactedListing(Integer month, Integer year);

    @Query(value = "SELECT top 3 make, COUNT(make) * 100 / (select count(*) from listing) AS make_percent \n" +
            "FROM listing \n" +
            "GROUP BY make \n" +
            "ORDER BY make_percent DESC, make", nativeQuery = true)
    List<Object[]> getDistributionOfMake();

    @Query(value = "SELECT AVG(price) FROM listing AS l \n" +
            "JOIN (SELECT TOP 30 PERCENT listing_id, COUNT(*) \n" +
            "FROM contact \n" +
            "GROUP BY listing_id \n" +
            "ORDER BY COUNT(*) DESC \n" +
            ") AS ll \n" +
            "ON l.id = ll.listing_id", nativeQuery = true)
    Double getTopThirtyPercent();
}
