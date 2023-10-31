package com.stroganov.warehouse.service.transaction;

import com.stroganov.warehouse.domain.dto.transaction.ExelTransactionRowDTO;
import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.domain.model.transaction.Record;
import com.stroganov.warehouse.domain.model.transaction.Transaction;
import com.stroganov.warehouse.domain.model.transaction.TransactionType;
import com.stroganov.warehouse.domain.model.user.User;
import com.stroganov.warehouse.domain.model.warehouse.Stock;
import com.stroganov.warehouse.domain.model.warehouse.Warehouse;
import com.stroganov.warehouse.exception.TransactionServiceException;
import com.stroganov.warehouse.repository.TransactionRepository;
import com.stroganov.warehouse.service.item.ItemService;
import com.stroganov.warehouse.service.user.UserService;
import com.stroganov.warehouse.service.warehouse.StockService;
import com.stroganov.warehouse.service.warehouse.WarehouseService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private Logger logger;

    @Autowired
    private StockService stockService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RecordService recordService;


    @Override
    @Transactional
    public int doTransaction(Set<ExelTransactionRowDTO> rowDTOSet, TransactionType transactionType) throws TransactionServiceException {
        List<Warehouse> currentUserWarehouseList = warehouseService.getCurrentUserWarehouseList();
        if (currentUserWarehouseList.isEmpty()) {
            throw new TransactionServiceException("Current warehouse not found");
        }
        List<ExelTransactionRowDTO> rowDTOList = getRecordsNotPresentInStock(rowDTOSet, currentUserWarehouseList.get(0).getId());
        if (!rowDTOList.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder("Items not present in Stock:");
            for (ExelTransactionRowDTO dto : rowDTOList) {
                stringBuilder.append(dto.toString()).append(";").append("\n");
            }
            logger.debug(stringBuilder.toString());
            throw new TransactionServiceException(stringBuilder.toString());
        }
        for (ExelTransactionRowDTO dto : rowDTOSet) {
            Optional<Stock> optionalStock = stockService.getCurrentStockByItemParamsAndWarehouseId(
                    dto.getModelArticle(), dto.getManufactureName(), dto.getStyleArticle(), currentUserWarehouseList.get(0).getId());
            optionalStock.ifPresent(stock -> {
                if (transactionType.equals(TransactionType.SUPPLY)) {
                    stock.setAmount(stock.getAmount() + dto.getCount());
                }
                if (transactionType.equals(TransactionType.CONSUMPTION)) {
                    stock.setAmount(stock.getAmount() - dto.getCount());
                }
            });
        }
        createNewTransaction(rowDTOSet, transactionType);
        return rowDTOSet.size();
    }

    private void createNewTransaction(Set<ExelTransactionRowDTO> rowDTOSet, TransactionType transactionType) throws TransactionServiceException {
        User currentAuthenticatedUser = (User) userService.getAuthenticatedUser();
        Optional<User> persistUser = userService.findUserByName(currentAuthenticatedUser.getUsername());
        if (persistUser.isEmpty()) {
            String message = "User " + currentAuthenticatedUser.getUsername() + " not found in DB";
            logger.error(message);
            throw new TransactionServiceException(message);
        }
        Transaction transaction = new Transaction(0, LocalDateTime.now(), persistUser.get(), transactionType);
        transactionRepository.save(transaction);
        for (ExelTransactionRowDTO dto : rowDTOSet) {
            Optional<Item> itemOptional = itemService.findItemByModelNameAndStyleArticle(
                    dto.getModelArticle(), dto.getManufactureName(), dto.getStyleArticle());
            Item item = itemOptional.orElseThrow(() -> {
                String message = String.format("Transaction Error: Item with parameters: %s  not found in catalog", dto);
                logger.error(message);
                return new TransactionServiceException(message);
            });
            Record recordObject = new Record(0, item, dto.getCount(), transaction);
            recordService.save(recordObject);
        }
    }

    public List<ExelTransactionRowDTO> getRecordsNotPresentInStock(Iterable<ExelTransactionRowDTO> iterableDTO, int warehouseId) {
        List<ExelTransactionRowDTO> resultList = new ArrayList<>();
        for (ExelTransactionRowDTO dto : iterableDTO) {
            if (!stockService.isStockExist(dto.getModelArticle(), dto.getManufactureName(), dto.getStyleArticle(), warehouseId)) {
                resultList.add(dto);
            }
        }
        return resultList;
    }
}
