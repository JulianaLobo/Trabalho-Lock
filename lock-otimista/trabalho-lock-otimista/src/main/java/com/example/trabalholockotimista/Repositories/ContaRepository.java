package com.example.trabalholockotimista.Repositories;

import com.example.trabalholockotimista.Entities.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    @Lock(LockModeType.OPTIMISTIC)
    Conta findByNumeroConta(String numeroConta);
}
