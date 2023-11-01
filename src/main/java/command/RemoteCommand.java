package command;

import java.util.HashMap;

public interface RemoteCommand {

   static HashMap<String, RemoteCommand> commandList = new HashMap<>();

   public static void addCommand(RemoteCommand command) {
      commandList.put(command.getCommandName(), command);
   }

   public static RemoteCommand forCommandName(String name) {
      return commandList.get(name);
   }

   public String getCommandName();
   public void execute();

}
