public class Fase1_Secuencial {
    public static void main(String[] args) {
        Temperatura temp = new Temperatura();
        Humedad hum = new Humedad();
        Luz luz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();

        long inicio = System.currentTimeMillis();

        temp.run();
        hum.run();
        luz.run();
        riego.run();      // run(), NO start()

        long total = System.currentTimeMillis() - inicio;

        System.out.println("\n INFORME");
        System.out.println(temp.informe());
        System.out.println(hum.informe());
        System.out.println(luz.informe());
        System.out.println(riego.informe());
        System.out.println("Tiempo total: " + total + " ms");
    }
}