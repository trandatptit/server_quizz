package com.trandat.btl_ltw.repository;

import com.trandat.btl_ltw.entity.CauHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CauHoiRepository extends JpaRepository<CauHoi, Long> {
    boolean existsByDeBai(String deBai);
    Optional<CauHoi> findByDeBai(String deBai);

}
