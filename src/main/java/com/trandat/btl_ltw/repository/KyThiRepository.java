package com.trandat.btl_ltw.repository;

import com.trandat.btl_ltw.entity.KyThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KyThiRepository extends JpaRepository<KyThi, Long> {
    boolean existsByTen(String kythiname);
    Optional<KyThi> findByTen(String kythiname);
}
