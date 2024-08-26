package com.trandat.btl_ltw.repository;

import com.trandat.btl_ltw.entity.DeThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeThiRepository extends JpaRepository<DeThi, Long> { //, PagingAndSortingRepository<DeThi, Long>
    boolean existsDeThiByTen(String ten);
    Optional<DeThi> findDeThiByTen(String ten);

    Optional<List<DeThi>> findDeThisByTrangThai(Boolean trangThai);
    Optional<List<DeThi>> findDeThisByTrangThaiFalseAndLichThiBefore(LocalDateTime now);

}
