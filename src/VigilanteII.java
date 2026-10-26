public class VigilanteII {

    private static void mostrarEstados(String titulo, Thread... hilos) {
        StringBuilder sb = new StringBuilder(titulo + ": ");
        for (Thread t : hilos) {
            sb.append(t.getName()).append("=").append(t.getState()).append("  ");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) throws InterruptedException {
        Temperatura temp = new Temperatura();
        Humedad hum = new Humedad();
        Luz luz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();

        Thread hTemp = new Thread(temp, "sensor-Temperatura");
        Thread hHum  = new Thread(hum,  "sensor-Humedad");
        Thread hLuz  = new Thread(luz,  "sensor-Luz");

        Vigilante vigilante = new Vigilante(hTemp, hHum, hLuz, riego);
        vigilante.setDaemon(true);          // ← ANTES de start(): la JVM no espera a este hilo

        mostrarEstados("ANTES de arrancar", hTemp, hHum, hLuz, riego);

        vigilante.start();
        hTemp.start();
        hHum.start();
        hLuz.start();
        riego.start();

        hTemp.join();
        hHum.join();
        hLuz.join();
        riego.join();

        mostrarEstados("DESPUÉS de esperar", hTemp, hHum, hLuz, riego);
        // main termina → como el vigilante es demonio, el programa acaba solo
    }
}
