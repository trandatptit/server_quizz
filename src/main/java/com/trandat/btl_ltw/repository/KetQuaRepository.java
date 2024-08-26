package com.trandat.btl_ltw.repository;

import com.trandat.btl_ltw.entity.KetQua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KetQuaRepository extends JpaRepository<KetQua, Long> {

}
