public class Concurrente {
    public static void main(String[] args) throws InterruptedException {
        Temperatura temp = new Temperatura();
        Humedad hum = new Humedad();
        Luz luz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();   // ya es un Thread, no hace falta new Thread(...)

        Thread hTemp = new Thread(temp, "sensor-Temperatura");
        Thread hHum  = new Thread(hum,  "sensor-Humedad");
        Thread hLuz  = new Thread(luz,  "sensor-Luz");

        long inicio = System.currentTimeMillis();

        hTemp.start();
        hHum.start();
        hLuz.start();
        riego.start();

        // main espera a los CUATRO
        hTemp.join();
        hHum.join();
        hLuz.join();
        riego.join();

        long total = System.currentTimeMillis() - inicio;

        System.out.println("\n===== INFORME =====");
        System.out.println(temp.informe());
        System.out.println(hum.informe());
        System.out.println(luz.informe());
        System.out.println(riego.informe());
        System.out.println("Tiempo total: " + total + " ms");
    }
}
