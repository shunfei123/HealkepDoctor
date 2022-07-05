package com.example.healkepdoctor;

import android.content.Context;
import android.os.Handler;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.healkepdoctor.View.doctormain.helper.patientListHelper;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.healkepdoctor", appContext.getPackageName());
        Handler handler = new Handler();
        patientListHelper helper = new patientListHelper(handler);
        helper.getPatientInfo();
    }
}