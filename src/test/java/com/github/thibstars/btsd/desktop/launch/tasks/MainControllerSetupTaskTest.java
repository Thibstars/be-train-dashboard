package com.github.thibstars.btsd.desktop.launch.tasks;

import com.github.thibstars.btsd.desktop.launch.CountDownLatchContext;
import com.github.thibstars.btsd.desktop.main.MainController;
import javax.swing.JScrollPane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Thibault Helsmoortel
 */
@ExtendWith(MockitoExtension.class)
class MainControllerSetupTaskTest {

    private MainControllerSetupTask mainControllerSetupTask;

    @Mock
    private CountDownLatchContext countDownLatchContext;

    @Mock
    private CountDownLatchContext dependentCountDownLatchContext;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private ControllersSetupTask controllersSetupTask;

    @BeforeEach
    void setUp() {
        this.mainControllerSetupTask = new MainControllerSetupTask(
                countDownLatchContext,
                dependentCountDownLatchContext,
                controllersSetupTask
        );
    }

    @Test
    void shouldRun() throws InterruptedException {
        Controllers controllers = Mockito.mock(Controllers.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(controllers.stationsController().getStationsTableScrollPane()).thenReturn(new JScrollPane());
        Mockito.when(controllersSetupTask.getCreatable()).thenReturn(controllers);

        Thread thread = new Thread(mainControllerSetupTask);
        thread.start();
        thread.join();

        MainController result = mainControllerSetupTask.getCreatable();

        Assertions.assertNotNull(result, "Result must not be null.");

        Mockito.verify(controllersSetupTask).getCreatable();

        Mockito.verify(dependentCountDownLatchContext).await();
        Mockito.verify(countDownLatchContext).countDown();
    }
}