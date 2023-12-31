package frc.robot.dashboard;

import edu.wpi.first.math.Pair;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Widget which displays a queue of messages on shuffleboard.
 */
public class DashboardMessageDisplay extends CommandBase {

	private List<Pair<Object, Integer>> cooldownList = new ArrayList<Pair<Object, Integer>>();
	private Queue<String> messages;
	private int cooldown;

	/**
	 * @param length number of messages
	 * @param cooldown the number of scheduler cycles before an object can send another message
	 */
	public DashboardMessageDisplay(int length, int cooldown) {
		this.cooldown = cooldown;
		messages = new ArrayBlockingQueue<String>(length);
		for (int i = 0; i < length; i++) { // initializes the queue to empty strings
			messages.offer("");
		}
	}

	/**
	 * Add a message to the display.
	 * There is a cooldown so that an object sending a message every scheduler cycle doesn't instantly fill the message display
	 * You need to pass the calling object in order for this to work, but java doesn't allow for enforcing it,
	 * so please do pass in the calling object, or everything will break.
	 *
	 * @param message The message to be displayed
	 * @param caller Please put "this" here, as this is how the cooldown function works
	 */
	public void addMessage(String message, Object caller) {
		boolean onCooldown = false;
		for (Pair<Object, Integer> p : cooldownList) {
			if (caller == p.getFirst()) onCooldown = true; //calling object is on cooldown
		}
		if (!onCooldown) {
			messages.remove();
			messages.add(message);
			cooldownList.add(new Pair<>(caller, 0));
		}
	}

	public void execute() {
		//for each item in the list of cooldowns
		for (int i = 0; i < cooldownList.size(); i++) {
			//gets the item and increments the time by 1
			Pair<Object, Integer> item = cooldownList.get(i);
			int time = item.getSecond();
			cooldownList.set(i, new Pair<>(item.getFirst(), time + 1));
			//removes the item if the time is up
			if (time >= cooldown) {
				cooldownList.remove(i);
				i--;
			}
		}
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.addStringArrayProperty(
			"Driver Messages",
			() -> {
				String[] messageArr = new String[messages.size()];
				messages.toArray(messageArr);
				return messageArr;
			},
			null
		);
	}
}
