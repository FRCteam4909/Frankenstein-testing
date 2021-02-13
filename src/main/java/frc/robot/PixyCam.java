package frc.robot;

import java.util.ArrayList;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class PixyCam extends SubsystemBase{
    
    private Pixy2 pixy;
    //Don't remove/rename the variables under
    private Block biggestBlock;
    private int numloops = 0;
    private static final int FRAME_WIDTH = 300; //TODO get values
    private static final int FRAME_HEIGHT = 200;
    

    // public void initialize() {
    //     System.out.println("This is inside the Pixy initialize() method");
    //     pixy = Pixy2.createInstance(new SPILink());
    //     pixy.init();
    //     // Enable both LEDS
    //     pixy.setLamp((byte) 1, (byte) 1);
    // }

    public PixyCam() {
        super();
        System.out.println("Constructing Pixy");
        pixy = Pixy2.createInstance(new SPILink());
        System.out.println(pixy.init());
        // Enable both LEDS
        System.out.println(pixy.setLamp((byte) 1, (byte) 1));
    }

    /**
     * @param criteria Choose between getting the biggest block based on width," "height," or "area."
     * @return Returns the biggest block. This can be used to determine proximity.
     * If "default" or any other string is passed, the first block.
     */
    public Block getBiggestBlock(String criteria) { //

        // Checks if blocks are on the screen
        int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1, 10); 
        if (blockCount <= 0) {
            return null;
        }

        // Get all blocks
        pixy.getCCC().getBlocks();
        ArrayList<Block> blocks = pixy.getCCC().getBlockCache();
        
        // Sort based on the selected criteria
        switch (criteria) {
            case "width":
                blocks.sort((Block b1, Block b2) -> b1.getWidth() - b2.getWidth());
                break;
            case "height":
                blocks.sort((Block b1, Block b2) -> b1.getHeight() - b2.getHeight());
                break;
            case "area":
                blocks.sort((Block b1, Block b2) -> (b1.getHeight()*b1.getWidth()) - (b2.getHeight()*b2.getWidth()));
                break;
            default:
                break;
        }
        
        //The first block in the list contains the biggest value after the sort.
        return blocks.get(0);
    }


    @Override
    public void periodic(){
        if(++numloops == RobotConstants.PIXY_REFRESH_DIVISOR){
            biggestBlock = this.getBiggestBlock("width"); //TODO for now.
            numloops = 0;
        }
        //System.out.println("Biggest block: " + biggestBlock);
    }

    /**
     * @return Returns deviation from center. Can be used for determining the x-position of a block.
     */
    public int getDeviationX() {
        int deviationFromCenter = - FRAME_WIDTH/2 + biggestBlock.getX();   //TODO figure out where the zero for the x-axis is.
        return deviationFromCenter;
    }

    /**
     * @return Returns deviation from center. Can be used for determining the y-position of a block.
     */
    public int getDeviationY() {
        int deviationFromCenter = biggestBlock.getY();   //TODO figure out where the zero for the y-axis is.
        return deviationFromCenter;
    }

    /**
     * @return Returns whether or not something is detected in frame.
     */
    public boolean getDetected() {
        boolean detected;
        if (biggestBlock == null) {
            detected = false;
        } else {
            detected = true;
        }
        return detected;
    }

    /**
     * @return Returns width of biggest block
     */
    public int getBlockWidth() {
        int blockWidth = biggestBlock.getWidth();
        return blockWidth;
    }

    /**
     * @return Returns height of biggest block
     */
    public int getBlockHeight() {
        int blockHeight = biggestBlock.getHeight();
        return blockHeight;
    }

} 