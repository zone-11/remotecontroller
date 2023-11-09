package command.separator;

import java.util.List;

public interface CommandSeparator {

	List<List<String>> separate(String inputLine);
	
}
