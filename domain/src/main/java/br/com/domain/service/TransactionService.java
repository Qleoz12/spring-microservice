package br.com.domain.service;

import br.com.domain.entity.transaction.TransacctionPurchaseVO;
import br.com.domain.entity.transaction.Transaction;
import br.com.domain.entity.transaction.TransactionVO;

import java.util.UUID;

public interface TransactionService extends ReadService<Transaction, TransactionVO, UUID> {


    public <T extends TransactionVO> TransactionVO purchase(TransacctionPurchaseVO transactionVO) throws Exception;

    public TransactionVO anulation(TransactionVO transactionVO) throws Exception;


}
