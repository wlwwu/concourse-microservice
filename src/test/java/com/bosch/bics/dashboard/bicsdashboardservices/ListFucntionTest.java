package com.bosch.bics.dashboard.bicsdashboardservices;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListFucntionTest{
@Test
public void verify_behaviour(){
    List mock = mock(List.class);
    mock.add(1);
    mock.clear();
    verify(mock).add(1);
    verify(mock).clear();
}
}