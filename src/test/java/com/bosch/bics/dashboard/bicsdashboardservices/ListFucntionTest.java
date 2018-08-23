package com.bosch.bics.dashboard.bicsdashboardservices;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.Test;

public class ListFucntionTest {
    @Test
    public void verify_behaviour() {
        List mock = mock(List.class);
        mock.add(1);
        mock.clear();
        verify(mock).add(1);
        verify(mock).clear();
    }
}