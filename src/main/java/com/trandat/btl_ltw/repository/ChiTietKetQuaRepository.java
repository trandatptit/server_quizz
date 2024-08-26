package com.trandat.btl_ltw.repository;

import com.trandat.btl_ltw.entity.ChiTietKetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietKetQuaRepository extends JpaRepository<ChiTietKetQua, Long> {

}
