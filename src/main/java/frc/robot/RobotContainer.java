package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.components.TalonFXComponent;
import frc.robot.utility.ArrayListSelector;
import java.util.ArrayList;

public class RobotContainer {

	private SendableChooser<Command> autonChooser = new SendableChooser<Command>();

	Orchestra orchestra;
	int songParseTime = 10;

	TalonFXComponent[] controllers = {
		new TalonFXComponent(9),
		new TalonFXComponent(2),
		new TalonFXComponent(3),
		new TalonFXComponent(4),
		new TalonFXComponent(5),
		new TalonFXComponent(6),
		new TalonFXComponent(7),
		new TalonFXComponent(8),
	};
	ArrayListSelector<String> songs = new ArrayListSelector<String>();

	public RobotContainer() {
		configureAuto();
		createInstruments();
		loadSongs();
	}

	public void configureAuto() {
		autonChooser.setDefaultOption("No Auto", null);
	}

	public void createInstruments() {
		ArrayListSelector<TalonFX> instruments = new ArrayListSelector<TalonFX>();

		for (int i = 0; i < controllers.length; ++i) {
			instruments.add(controllers[i]);
		}

		orchestra = new Orchestra(instruments);
	}

	public void loadSongs() {
		songs.add("song1.chrp");
		songs.add("output.chrp");
		orchestra.loadMusic(songs.get(0));
	}

	public Command getAutonomousCommand() {
		return autonChooser.getSelected();
	}

	public void teleopInit() {
		Command auton = autonChooser.getSelected();
		if (auton != null) {
			auton.cancel();
		}
	}

	public void teleopPeriodic() {
		if (songParseTime > 0) {
			songParseTime--;
		} else {
			if (!orchestra.isPlaying()) {
				songParseTime = 10;
				orchestra.loadMusic(songs.get(songs.nextIndex()));
			}
			orchestra.play();
		}
	}
}
