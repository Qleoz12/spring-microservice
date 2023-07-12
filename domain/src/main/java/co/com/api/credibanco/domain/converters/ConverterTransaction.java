package co.com.api.credibanco.domain.converters;


import co.com.api.credibanco.domain.entity.transaction.Transaction;
import co.com.api.credibanco.domain.entity.transaction.TransactionResultVO;

public class ConverterTransaction {

    private ConverterTransaction() {
        throw new IllegalStateException("Utility class");
    }

    public static TransactionResultVO convertTransactionToTransactionData(Transaction transaction) {
        TransactionResultVO transactionResultVO = new TransactionResultVO();
        if (transaction != null) {
            transactionResultVO.setTransactionId(transaction.getId().toString());
            transactionResultVO.setCardId(transaction.getCard().getCardId());
            transactionResultVO.setTransactionDate(transaction.getTransactionDate());
            transactionResultVO.setIsValid(transaction.getIsValid());
            transactionResultVO.setPrice(transaction.getPrice());
        }
        return transactionResultVO;
    }

}
