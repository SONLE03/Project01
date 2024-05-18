package com.clothing.warrantyservice.repository;

import com.clothing.warrantyservice.model.Warranty;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;
@Repository
public interface WarrantyRepository extends JpaRepository<Warranty, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Warranty w SET w.warrantyStatus = 'EXPIRED' " +
            "WHERE w.endDate < :currentDate AND w.warrantyStatus != 'EXPIRED'")
    void updateExpiredWarranties(Date currentDate);
}
