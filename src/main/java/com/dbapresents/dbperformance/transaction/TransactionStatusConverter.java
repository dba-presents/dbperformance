package com.dbapresents.dbperformance.transaction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<TransactionStatusWithId, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TransactionStatusWithId status) {
        if (status == null) {
            return null;
        }
        return status.getId();
    }

    @Override
    public TransactionStatusWithId convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(TransactionStatusWithId.values())
                .filter(status -> status.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
