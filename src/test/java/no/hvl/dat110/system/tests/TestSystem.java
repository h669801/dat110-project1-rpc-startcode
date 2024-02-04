package no.hvl.dat110.system.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import system.controller.Controller;
import system.display.DisplayDevice;
import system.sensor.SensorDevice;
import java.util.concurrent.atomic.AtomicBoolean;

class TestSystem {

	@Test
	void test() {

		System.out.println("System starting ...");

		AtomicBoolean failure = new AtomicBoolean(false);
		
		Thread displaythread = new Thread() {

			public void run() {
				
				try {
					DisplayDevice.main(null);
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}
			}
			
		};
		
		Thread sensorthread = new Thread() {
			
			public void run() {
				
				try {
				SensorDevice.main(null);
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}
			}
			
		};
		
		
		Thread controllerthread = new Thread() {
			
			public void run() {
				
				try {
				Controller.main(null);
				} catch (Exception e) {
					e.printStackTrace();
					failure.set(true);
				}
			}
			
		};

		try {
			
			displaythread.start();
			sensorthread.start();
		
			// let the servers start first
			Thread.sleep(2000);
			
			controllerthread.start();
			
			displaythread.join();
			sensorthread.join();
			controllerthread.join();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} finally {
			System.out.println("System stopping ...");
			
			if (failure.get()) {
				fail();
			}
		}
		
		// we check only termination here
		assertTrue(true);
			
	
	}

}
