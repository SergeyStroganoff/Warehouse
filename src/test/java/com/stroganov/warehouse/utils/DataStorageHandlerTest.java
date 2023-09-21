package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.domain.model.item.Item;
import com.stroganov.warehouse.exception.DataVerificationException;
import com.stroganov.warehouse.exception.FileExtensionError;
import com.stroganov.warehouse.exception.FileParsingException;
import com.stroganov.warehouse.exception.NoSuchSheetException;
import com.stroganov.warehouse.service.DataStorageHandler;
import com.stroganov.warehouse.utils.parser.DataItemParserImpl;
import com.stroganov.warehouse.utils.parser.DataParser;
import com.stroganov.warehouse.utils.verifier.DataVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataStorageHandlerTest {
    private final List<String> rightObjectList = List.of("B12", "30", "34 1/2", "42", "Base cabinet", "T100", "Shaker White", "NWS", "Nordic company", "$200.12", Double.toString(1989.12));
    private final Map<Integer, List<String>> mockedMap = new HashMap<>();
    @InjectMocks
    private DataStorageHandler<Item> handler;
    @Mock
    private ExelFileReaderImpl exelFileReader;
    @Mock
    private DataVerifier dataVerifier;

    private final DataParser<Item> dataParser = new DataItemParserImpl();

    private final Path path = Paths.get("C:");

    @Test
    void when_parseExelFile_withItemList_then_get_ItemList_Test() throws FileParsingException, IOException, FileExtensionError, NoSuchSheetException, DataVerificationException {
        //give
        mockedMap.put(1, rightObjectList);
        //itemParser.setSheetName("List");

        when(exelFileReader.readExelTable(anyString(), any())).thenReturn(mockedMap);
        when(dataVerifier.dataVerify(anyMap())).thenReturn(Boolean.TRUE);
        //when
        org.assertj.core.api.Assertions.assertThat(exelFileReader.readExelTable(anyString(), anyString())).isNotEmpty();
        Set<Item> itemSet = handler.parseExelFile(path, dataParser, dataVerifier);
        List<Item> parsedItemList = itemSet.stream().toList();
        //then
        org.assertj.core.api.Assertions.assertThat(itemSet).isNotEmpty();
        Item gotten = parsedItemList.get(0);
        Assertions.assertEquals(rightObjectList.get(0), gotten.getModel().getArticle());
    }

    @Test
    void when_parseExelFile_withDeliveryList_then_get_ItemList_Test() throws FileParsingException, IOException, FileExtensionError, NoSuchSheetException, DataVerificationException {
        //give
        mockedMap.put(1, rightObjectList);
        //itemParser.setSheetName("List");
        when(exelFileReader.readExelTable(anyString(), any())).thenReturn(mockedMap);
        when(dataVerifier.dataVerify(anyMap())).thenReturn(Boolean.TRUE);
        //when
        org.assertj.core.api.Assertions.assertThat(exelFileReader.readExelTable(anyString(), anyString())).isNotEmpty();
        Set<Item> itemSet = handler.parseExelFile(path, dataParser, dataVerifier);
        List<Item> parsedItemList = itemSet.stream().toList();
        //then
        org.assertj.core.api.Assertions.assertThat(itemSet).isNotEmpty();
        Item gotten = parsedItemList.get(0);
        Assertions.assertEquals(rightObjectList.get(0), gotten.getModel().getArticle());
    }
}