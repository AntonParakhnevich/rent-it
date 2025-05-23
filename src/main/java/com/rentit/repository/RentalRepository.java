package com.rentit.repository;

import com.rentit.model.Rental;
import com.rentit.model.RentalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    Page<Rental> findByRenterId(Long renterId, Pageable pageable);
    Page<Rental> findByItemOwnerId(Long ownerId, Pageable pageable);
    
    @Query("SELECT r FROM Rental r WHERE r.item.id = :itemId AND r.status = :status")
    List<Rental> findByItemIdAndStatus(Long itemId, RentalStatus status);
    
    @Query("SELECT r FROM Rental r WHERE r.item.id = :itemId AND r.status IN :statuses")
    List<Rental> findByItemIdAndStatusIn(Long itemId, List<RentalStatus> statuses);
    
    @Query("SELECT r FROM Rental r WHERE r.item.id = :itemId AND " +
           "((r.startDate <= :endDate AND r.endDate >= :startDate) OR " +
           "(r.startDate >= :startDate AND r.startDate <= :endDate))")
    List<Rental> findOverlappingRentals(Long itemId, LocalDateTime startDate, LocalDateTime endDate);
} 