package impl;

public class Consumer extends Thread {
        Program program;
        String name;
        long delay;

        public Consumer(Program program, String name, long delay) {
            this.program = program;
            this.name = name;
            this.delay = delay;
        }

        public void run() {
           try {
               while (true) {
                   program.full.down();  // espera haver item
                   program.mutex.down(); // entra na zona crítica

                   int item = (Integer) program.buffer.get(0);
                   program.buffer.remove(0);
                   program.itemCount--;
                   System.out.println("["+ this.name + "] consuming => Item " + item);

                   sleep(this.delay);

                   program.mutex.up(); // sai da zona crítica
                   program.empty.up(); // avisa que há espaço
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
}
