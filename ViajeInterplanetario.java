import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ViajeInterplanetario {

    // Distancias desde la Tierra a los planetas en millones de kilómetros
    private static final Map<Integer, String> planetas = new HashMap<>();
    private static final Map<String, Double> distancias = new HashMap<>();
    static {
        planetas.put(1, "Mercurio");
        planetas.put(2, "Venus");
        planetas.put(3, "Marte");
        planetas.put(4, "Jupiter");
        planetas.put(5, "Saturno");
        planetas.put(6, "Urano");
        planetas.put(7, "Neptuno");

        distancias.put("Mercurio", 77.0);
        distancias.put("Venus", 41.0);
        distancias.put("Marte", 78.0);
        distancias.put("Jupiter", 628.0);
        distancias.put("Saturno", 1275.0);
        distancias.put("Urano", 2724.0);
        distancias.put("Neptuno", 4351.0);
    }

    // Consumo de combustible en litros por millón de kilómetros
    private static final double CONSUMO_COMBUSTIBLE = 1000.0;

    // Consumo de oxígeno en litros por millón de kilómetros
    private static final double CONSUMO_OXIGENO = 500.0;

    // Velocidad de la nave en km/h
    private static final double VELOCIDAD_NAVE = 28000.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean reiniciarViaje = true;
        while (reiniciarViaje) {
            System.out.println("Bienvenido al simulador de viaje interplanetario!");

            // 1. Seleccionar destino interplanetario
            System.out.println("Selecciona un planeta destino:");
            for (Map.Entry<Integer, String> entry : planetas.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue());
            }
            int opcion = scanner.nextInt();
            String destino = planetas.get(opcion);

            if (destino == null) {
                System.out.println("Opción no válida.");
                continue;
            }

            double distancia = distancias.get(destino);
            double tiempoViaje = (distancia * 1000000) / VELOCIDAD_NAVE; // Tiempo en horas

            // 2. Calcular distancia y tiempo de viaje
            System.out.println("\nDistancia a " + destino + ": " + distancia + " millones de km");
            System.out.println("Tiempo estimado de viaje: " + tiempoViaje + " horas");

            // 3. Gestionar recursos de la nave
            double combustibleNecesario = distancia * CONSUMO_COMBUSTIBLE;
            double oxigenoNecesario = distancia * CONSUMO_OXIGENO;

            System.out.println("\nRecursos necesarios para el viaje:");
            System.out.println("- Combustible: " + combustibleNecesario + " litros");
            System.out.println("- Oxígeno: " + oxigenoNecesario + " litros");

            System.out.println("\nIngresa la cantidad de combustible disponible (litros):");
            double combustibleDisponible = scanner.nextDouble();

            System.out.println("Ingresa la cantidad de oxígeno disponible (litros):");
            double oxigenoDisponible = scanner.nextDouble();

            // Permitir al usuario modificar los recursos
            System.out.println("\n¿Deseas agregar más combustible? (s/n)");
            if (scanner.next().equalsIgnoreCase("s")) {
                System.out.println("Ingresa la cantidad adicional de combustible (litros):");
                combustibleDisponible += scanner.nextDouble();
            }

            System.out.println("¿Deseas agregar más oxígeno? (s/n)");
            if (scanner.next().equalsIgnoreCase("s")) {
                System.out.println("Ingresa la cantidad adicional de oxígeno (litros):");
                oxigenoDisponible += scanner.nextDouble();
            }

            if (combustibleDisponible < combustibleNecesario || oxigenoDisponible < oxigenoNecesario) {
                System.out.println("No tienes suficientes recursos para el viaje.");
                continue;
            }

            // 4. Simular eventos aleatorios durante el viaje
            System.out.println("\nIniciando viaje a " + destino + "...");

            double distanciaRecorrida = 0.0;
            boolean viajeExitoso = true;

            while (distanciaRecorrida < distancia) {
                System.out.println("\n--- Estado del viaje ---");
                double porcentaje = (distanciaRecorrida / distancia) * 100;
                System.out.println("Progreso: " + String.format("%.2f", porcentaje) + "%");
                System.out.println("Combustible restante: " + combustibleDisponible + " litros");
                System.out.println("Oxígeno restante: " + oxigenoDisponible + " litros");

                // Evento aleatorio
                if (random.nextInt(10) < 3) { // 30% de probabilidad de evento
                    int evento = random.nextInt(3);
                    switch (evento) {
                        case 0:
                            System.out.println("¡Falla en el sistema! Consumo de combustible aumentado.");
                            combustibleDisponible -= 100;
                            break;
                        case 1:
                            System.out.println("¡Asteroide detectado! Desvío necesario, tiempo de viaje aumentado.");
                            tiempoViaje += 10;
                            break;
                        case 2:
                            System.out.println("¡Fuga de oxígeno! Consumo de oxígeno aumentado.");
                            oxigenoDisponible -= 50;
                            break;
                    }
                }

                // Avanzar en el viaje
                double avance = random.nextDouble() * 10; // Avance aleatorio entre 0 y 10 millones de km
                distanciaRecorrida += avance;
                combustibleDisponible -= avance * CONSUMO_COMBUSTIBLE;
                oxigenoDisponible -= avance * CONSUMO_OXIGENO;

                if (combustibleDisponible <= 0 || oxigenoDisponible <= 0) {
                    System.out.println("\n¡Recursos agotados! Nave explotó, tripulantes murieron.");
                    viajeExitoso = false;
                    break;
                }

                try {
                    Thread.sleep(1000); // Pausa de 1 segundo para simular el tiempo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 5. Monitorear el estado del viaje
            if (viajeExitoso) {
                System.out.println("\n¡Has llegado a " + destino + "!");
                System.out.println("Combustible restante: " + combustibleDisponible + " litros");
                System.out.println("Oxígeno restante: " + oxigenoDisponible + " litros");
            }

            // Preguntar si desea reiniciar el viaje
            System.out.println("\n¿Deseas seleccionar otro destino? (s/n)");
            reiniciarViaje = scanner.next().equalsIgnoreCase("s");
        }

        System.out.println("Gracias por usar el simulador de viaje interplanetario. ¡Hasta la próxima!");
    }
}