package com.stroganov.warehouse.utils;

import com.stroganov.warehouse.exception.DataVerificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class DataVerificationImplTest {
    @Mock
    Logger logger;

    @InjectMocks
    private DataVerification dataVerification = new DataVerificationImpl();

    private final Map<Integer, List<Object>> mapForTest = new HashMap<>();
    private final List<Object> rightObjectList = List.of("B12", "30", "34 1/2", "42", "Base cabinet", "T100", "Shaker White", "NWS", "Nordic company", "$200", 1989.12);
    private final List<Object> wrongCountParametersObjectList = List.of("B12", "30", "34 1/2", "42", "Base cabinet", "T100", "Shaker White", "NWS", "Nordic company", "$200");
    private final List<Object> wrongParameterSizeObjectList = List.of("И12", "30", "34 1/2", "12346789011", "Base cabinet", "T100", "Shaker White", "NWS", "Nordic company", "$200", "$300");

    @Test
    void when_verify_then_return_true() throws DataVerificationException {
        // given
        mapForTest.put(1, rightObjectList);
        //when
        boolean result = dataVerification.verify(mapForTest);
        //then
        //Assertions.assertThat(result).isTrue();
        Assertions.assertTrue(result);
    }

    @Test
    void when_verify_then_return_throw_exception() {
        // given
        mapForTest.put(1, wrongCountParametersObjectList);
        //when
        //then
        DataVerificationException exception = Assertions.assertThrows(DataVerificationException.class, () -> {
            dataVerification.verify(mapForTest);
        }, "DataVerificationException was expected");
    }

    @Test
    void when_verify_wrong_parameter_size_then_return_throw_exception() {
        // given
        mapForTest.put(1, wrongParameterSizeObjectList);
        //when
        //then
        DataVerificationException exception = Assertions.assertThrows(DataVerificationException.class, () -> {
            dataVerification.verify(mapForTest);
        }, "DataVerificationException was expected");
    }
}