package com.financia.financia.mapper;

import com.financia.financia.dto.TransactionDTO;
import com.financia.financia.model.Transaction;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    // Mappa SOLO i campi non null dal DTO all'entità esistente
    void updateTransactionFromDTO(TransactionDTO dto, @MappingTarget Transaction entity);
}
