package co.com.api.credibanco.domain.service;

import co.com.api.credibanco.domain.entity.transaction.TransacctionPurchaseVO;
import co.com.api.credibanco.domain.entity.transaction.Transaction;
import co.com.api.credibanco.domain.entity.transaction.TransactionVO;
import co.com.api.credibanco.domain.exception.rules.RuleException;

import java.util.UUID;

public interface TransactionService extends ReadService<Transaction, TransactionVO, UUID> {


    public <T extends TransactionVO> TransactionVO purchase(TransacctionPurchaseVO transactionVO) throws RuleException;

    public TransactionVO anulation(TransactionVO transactionVO) throws RuleException;


}
