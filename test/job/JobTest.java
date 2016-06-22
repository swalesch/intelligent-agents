package job;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.google.common.collect.Lists;

import drone.movement.DroneVector;

public class JobTest {
	@Test
	public void createJsonStringFromJob() {
		List<Tuple<Integer, DroneVector>> itemLocation = Lists.newArrayList();
		itemLocation.add(new Tuple<Integer, DroneVector>(1, new DroneVector(23, 10)));
		itemLocation.add(new Tuple<Integer, DroneVector>(1, new DroneVector(23, 65)));
		itemLocation.add(new Tuple<Integer, DroneVector>(3, new DroneVector(12, 10)));

		Job job = new Job(1, new DroneVector(2, 4), itemLocation);

		job.jobToJasonString();
		assertThat(job.jobToJasonString()).isEqualTo(
				"{\"jobID\":1,\"messageType\":\"Job\",\"destination\":{\"x\":2.0,\"y\":4.0},\"itemLocation\":[{\"itemId\":1,\"x\":23.0,\"y\":10.0},{\"itemId\":1,\"x\":23.0,\"y\":65.0},{\"itemId\":3,\"x\":12.0,\"y\":10.0}]}");
	}

	@Test
	public void createJobFromJasonString() throws ParseException {
		int jobID = 1;
		double destX = 2;
		double destY = 4;
		String jsonString = "{\"jobID\":" + jobID + ",\"messageType\":\"Job\",\"destination\":{\"x\":" + destX
				+ ",\"y\":" + destY
				+ "},\"itemLocation\":[{\"itemId\":1,\"x\":23.0,\"y\":10.0},{\"itemId\":1,\"x\":23.0,\"y\":65.0},{\"itemId\":3,\"x\":12.0,\"y\":10.0}]}";
		Job job = new Job(jsonString);
		assertThat(job.getJobID()).isEqualTo(jobID);
		assertThat(job.getDestination()).isEqualTo(new DroneVector(destX, destY));

		List<Tuple<Integer, DroneVector>> itemLocation = Lists.newArrayList();
		itemLocation.add(new Tuple<Integer, DroneVector>(1, new DroneVector(23, 10)));
		itemLocation.add(new Tuple<Integer, DroneVector>(1, new DroneVector(23, 65)));
		itemLocation.add(new Tuple<Integer, DroneVector>(3, new DroneVector(12, 10)));

		assertThat(job.getItemLocation()).isEqualTo(itemLocation);
	}
}
