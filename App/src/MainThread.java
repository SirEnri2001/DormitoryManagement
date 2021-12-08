package src;

public class MainThread {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Main.main(args);
            }
        }).start();
    }

}
