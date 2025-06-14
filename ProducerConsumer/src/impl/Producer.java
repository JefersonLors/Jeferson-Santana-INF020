package impl;

public class Producer extends Thread {
        Program program;
        int counter;
        String name;
        long delay;


    public Producer(Program program, String name, long delay) {
           this.program = program;
           this.counter = 0;
           this.name = name;
           this.delay = delay;
        }

        public void run() {
            try {
                while (true) {
                    program.empty.down(); // espera espaço para produzir
                    program.mutex.down(); // entra na seção crítica

                    program.buffer.add(++counter);
                    program.itemCount++;
                    System.out.println("["+ this.name +"]" + " Producing => Buffer [" + (counter - 1) % program.BUFFER_SIZE + "]: Item " + counter);
                    sleep(this.delay);

                    program.mutex.up(); // sai da zona crítica
                    program.full.up(); // sinaliza que há item disponível
                }
            }
           catch (Exception e) {
                e.printStackTrace();
           }
        }
}
    
