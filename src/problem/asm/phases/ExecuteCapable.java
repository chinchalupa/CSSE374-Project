package problem.asm.phases;

/**
 * Created by Jeremy on 2/14/2016.
 * ExecuteCapable interfaces for handling how phases are drawn.
 */
public interface ExecuteCapable {

    /**
     * Executes a phase who's implementation is provided by the class.
     */
    public void execute();

    /**
     * Gets a string that is what is being executed.
     * @return - The string describing the execution.
     */
    public String getExecutionString();
}
