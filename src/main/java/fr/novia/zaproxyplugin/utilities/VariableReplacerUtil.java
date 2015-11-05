package fr.novia.zaproxyplugin.utilities;

import java.util.Map;
import java.util.Set;

public class VariableReplacerUtil {
	public static String replace(String originalCommand, Map<String, String> vars) {
		if(originalCommand == null){
			return null;
		}
		vars.remove("_"); //why _ as key for build tool?
		StringBuilder sb = new StringBuilder();
		for (String variable : vars.keySet()) {
			if (originalCommand.contains(variable) ) {
				sb.append(variable).append("=\"").append(vars.get(variable)).append("\"\n");
			}
		}
		sb.append("\n");
		sb.append(originalCommand);
		return sb.toString();
	}

	public static String scrub(String command, Map<String, String> vars, Set<String> eyesOnlyVars) {
		if(command == null || vars == null || eyesOnlyVars == null){
			return command;
		}
		vars.remove("_");
		for (String sensitive : eyesOnlyVars) {
			for (String variable : vars.keySet()) {
				if (variable.equals(sensitive)) {
					String value = vars.get(variable);
					if (command.contains(value)) {
						if (command.contains("\"" + value + "\"")) {
							command = command.replace(("\"" + value + "\"") , "**********");
						}
						command = command.replace(value , "**********");
					}
					break;
				}
			}
		}
		return command;
	}
}
