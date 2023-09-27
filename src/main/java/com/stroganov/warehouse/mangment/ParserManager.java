package com.stroganov.warehouse.mangment;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.domain.dto.transaction.FileTypeOptions;
import com.stroganov.warehouse.exception.FileParsingException;
import com.stroganov.warehouse.service.DataStorageHandler;
import com.stroganov.warehouse.utils.parser.DataParser;
import com.stroganov.warehouse.utils.verifier.DataVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Set;

@Component
public class ParserManager {

    @Autowired
    @Qualifier("transactionListVerifierImpl")
    private DataVerifier transactionListVerifierImpl;

    @Autowired
    @Qualifier("transactionParserImpl")
    private DataParser<ExelTransactionRowDTO> exelTransactionRowDTODataParser;

    @Autowired
    private DataStorageHandler<ExelTransactionRowDTO> dataStorageHandler;

    public Set<ExelTransactionRowDTO> parseFile(Path fileUploadedPath, FileTypeOptions fileTypeOptions) throws FileParsingException {
        //TODO - improove nandling options;
        return switch (fileTypeOptions) {
            case BASIC_TEMPLATE ->
                    dataStorageHandler.parseExelFile(fileUploadedPath, exelTransactionRowDTODataParser, transactionListVerifierImpl);
            case NORDIC_TEMPLATE -> null;
        };
    }
}
