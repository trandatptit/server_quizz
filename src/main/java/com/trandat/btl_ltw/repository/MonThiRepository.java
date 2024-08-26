package com.trandat.btl_ltw.repository;

import com.trandat.btl_ltw.entity.MonThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonThiRepository extends JpaRepository<MonThi, Long> {
    boolean existsByTenMon(String tenMon);
    Optional<MonThi> findByTenMon(String tenMon);
}
