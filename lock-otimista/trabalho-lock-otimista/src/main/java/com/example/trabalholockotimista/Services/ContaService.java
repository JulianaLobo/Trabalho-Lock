package com.example.trabalholockotimista.Services;

import com.example.trabalholockotimista.Entities.Conta;
import com.example.trabalholockotimista.Repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    public Conta sacar(String numeroConta, BigDecimal valor) throws Exception {
        Conta conta = getContaByNumero(numeroConta);

        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new Exception("Saldo insuficiente.");
        }

        conta.setSaldo(conta.getSaldo().subtract(valor));
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta depositar(String numeroConta, BigDecimal valor) {
        Conta conta = getContaByNumero(numeroConta);

        BigDecimal novoSaldo = conta.getSaldo().add(valor);
        conta.setSaldo(novoSaldo);

        return contaRepository.save(conta);
    }

    private Conta getContaByNumero(String numeroConta) throws Exception {
        Conta conta = contaRepository.findByNumeroConta(numeroConta);
        if (conta == null) {
            throw new Exception("Conta não encontrada");
        }
        return conta;
    }
}
