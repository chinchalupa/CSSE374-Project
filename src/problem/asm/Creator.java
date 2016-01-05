package problem.asm;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Creator implements MouseListener{
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Creature creature = new Creature();
        }
        System.out.println(Creature.numCreated());
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

class Creature {
    private static long numCreated = 0;

    public Creature() {
        numCreated++;
    }

    public static long numCreated() {
        return numCreated;
    }
}
