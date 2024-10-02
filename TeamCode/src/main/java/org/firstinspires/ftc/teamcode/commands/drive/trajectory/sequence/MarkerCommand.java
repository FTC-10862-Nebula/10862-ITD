package org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.Subsystem;

import java.util.Set;

public class MarkerCommand extends CommandBase {
    enum State {
        WAIT_FOR_START,
        INITIALIZE,
        EXECUTE,
        FINISHED,
    }
    private State state = State.WAIT_FOR_START;
    private final Command command;
    public MarkerCommand(Command command) {
        this.command = command;
    }

    public void start() {
        state = State.INITIALIZE;
    }

    public State getState() {
        return state;
    }

    @Override
    public void initialize() {
        command.initialize();
        state = State.EXECUTE;
    }
    @Override
    public void execute() {
        command.execute();
        if (command.isFinished()) {
            state = State.FINISHED;
        }
    }
    @Override
    public void end(boolean interrupted) {
        command.end(interrupted);
    }
    @Override
    public boolean isFinished() {
        return state == State.FINISHED;
    }

    @Override
    public Set<Subsystem> getRequirements() {
        return command.getRequirements();
    }

    @Override
    public boolean runsWhenDisabled() {
        return command.runsWhenDisabled();
    }
}
