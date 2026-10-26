public class Experimentos {
    public static void main(String[] args) throws InterruptedException {

        // ===== EXPERIMENTO A: informe() SIN esperar =====
        System.out.println("===== EXPERIMENTO A =====");
        Temperatura tempA = new Temperatura();
        Thread hA = new Thread(tempA, "sensor-Temperatura");
        hA.start();
        System.out.println("Informe SIN esperar: " + tempA.informe());
        hA.join();
        System.out.println("Informe tras join:   " + tempA.informe());

        // ===== EXPERIMENTO B: run() en vez de start() =====
        System.out.println("\n===== EXPERIMENTO B =====");
        ProgramadorRiego riegoB = new ProgramadorRiego(3);
        long inicioB = System.currentTimeMillis();
        riegoB.run();                         // ← run(), no start()
        long totalB = System.currentTimeMillis() - inicioB;
        System.out.println("Tiempo: " + totalB + " ms");
        System.out.println("Estado del riego: " + riegoB.getState());

        // ===== EXPERIMENTO C: start() dos veces =====
        System.out.println("\n===== EXPERIMENTO C =====");
        ProgramadorRiego riegoC = new ProgramadorRiego();
        riegoC.start();
        riegoC.join();                        // esperamos a que termine
        try {
            riegoC.start();                   // segundo start() sobre el mismo objeto
        } catch (IllegalThreadStateException e) {
            System.out.println("Excepción capturada: " + e);
        }
    }
}
