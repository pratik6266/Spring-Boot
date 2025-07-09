package com.project.journalApp.service;

import com.project.journalApp.entity.User;
import com.project.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @ParameterizedTest
    @CsvSource({
            "Raj1",
            "Raj2",
            "Pratik1"
    })
    public void testFindByUsername(String username){
        when(userRepository.findByUsername(username)).thenReturn(new User()); 
        assertNotNull(userRepository.findByUsername(username));
    }
}
