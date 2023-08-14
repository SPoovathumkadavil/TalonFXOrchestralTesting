package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.music.Orchestra;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.components.TalonFXComponent;
import java.util.ArrayList;

public class RobotContainer {

  private SendableChooser<Command> autonChooser = new SendableChooser<Command>();

  String[] songs;
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

  public RobotContainer() {
    configureAuto();
  }

  public void configureAuto() {
    autonChooser.setDefaultOption("No Auto", null);
  }

  public void createInstruments() {
    ArrayList<TalonFX> instruments = new ArrayList<TalonFX>();

    for (int i = 0; i < controllers.length; ++i) {
      instruments.add(controllers[i]);
    }

    orchestra = new Orchestra(instruments);
  }

  public void loadSong() {
    songs = new String[] { "song1.chrp" };
    orchestra.loadMusic(songs[0]);
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
      orchestra.play();
    }
  }
}
