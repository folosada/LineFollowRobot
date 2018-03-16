import lejos.nxt.*;


public class Main {
	
	static long tempoInicioMotorB = 0;	
	static LightSensor sensor;
	static final int VALOR_SENSOR = 40;
	static final int TEMPO_LACUNA = 250;
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Button.waitForAnyPress();
		sensor = new LightSensor(SensorPort.S2);
		Motor.B.setSpeed(180);
		Motor.C.setSpeed(180);
		while (!Button.ESCAPE.isDown()) {
			System.out.println(sensor.readValue());
			
			if (sensor.readValue() < VALOR_SENSOR) { // Black
				moveMotorC();
			} else { // White
				moveMotorB();
			}
				
		}
	}
	
	private static void moveMotorC() {
		System.out.println("Motor C");
		tempoInicioMotorB = 0;
		Motor.C.forward();				 									
		Motor.B.flt();						
	}
	
	private static void moveMotorB() throws InterruptedException {
		System.out.println("Motor B");
		Motor.B.forward();		
		if (tempoInicioMotorB == 0) {
			tempoInicioMotorB = System.currentTimeMillis(); 
		}
		if (System.currentTimeMillis() - tempoInicioMotorB < TEMPO_LACUNA) { 
			System.out.println("Menor que 500ms");
			Motor.C.flt();				
		} else {
			System.out.println("Maior que 500ms");
			Motor.C.setSpeed(130);
			Motor.B.setSpeed(130);
			while (sensor.readValue() > VALOR_SENSOR) {
				System.out.println("LACUNA");
				Motor.C.forward();
				Thread.sleep(70);
				Motor.C.flt();
				Motor.B.forward();
				Thread.sleep(70);
				Motor.B.flt();
			}
			Motor.C.setSpeed(180);
			Motor.B.setSpeed(180);
		}
	}

}
