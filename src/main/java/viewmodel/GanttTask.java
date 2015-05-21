package viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GanttTask {

	public Long id;
	public String name;
	public String code;
	public int level;
	public String status;
	public boolean canWrite = false;
	public Long start;
	public Long duration;
	public Long end;
	public boolean startIsMilestone;
	public boolean endIsMilestone;
	public boolean collapsed;
	public List<String> assigs = new ArrayList<>();
	public String description;
	public String depends;
	public int progress;
	public boolean hasChild;
}
